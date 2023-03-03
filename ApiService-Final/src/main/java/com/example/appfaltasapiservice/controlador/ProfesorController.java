package com.example.appfaltasapiservice.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.appfaltasapiservice.modelo.Profesor;
import com.example.appfaltasapiservice.modelo.ProfesorTienePerfil;
import com.example.appfaltasapiservice.modelo.wrappers.Profesores_datos_WR;
import com.example.appfaltasapiservice.repositorio.ProfesorRepositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@RestController
public class ProfesorController {
	private final ProfesorRepositorio profesorRepositorio;

	private EntityManager em;

	public ProfesorController(ProfesorRepositorio profesorRepositorio) {
		this.profesorRepositorio = profesorRepositorio;
		this.em = Persistence.createEntityManagerFactory("API").createEntityManager();
	}

	// Devuelve todos los profesores con todos sus datos
	@GetMapping("/profesores")
	public ResponseEntity<?> obtenerTodos() {
		List<Profesor> profesores = profesorRepositorio.findAll();
		if (profesores.isEmpty())
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(profesores);
	}

	// Devuelve todos los profesores con un filtro de datos (FALLA)
	@GetMapping("/profesores-datos")
	public ResponseEntity<?> obtenerDatos() {
		List<Profesor> profesor = profesorRepositorio.findAll();
		if (profesor == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(Profesores_datos_WR.listaProfesor(profesor));
	}

	// Devuelve un unico profesor buscado por el id con todos sus datos
	@GetMapping("/profesores/{id}")
	public ResponseEntity<?> obtenerUno(@PathVariable Integer id) {
		Profesor profesor = profesorRepositorio.findById(id).orElse(null);
		if (profesor == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(profesor);
	}

	// Devuelve un unico profesor buscado por el id con un filtro de datos
	@GetMapping("/profesores-datos/{id}")
	public ResponseEntity<?> obtenerDatosUno(@PathVariable Integer id) {
		Profesor profesor = profesorRepositorio.findById(id).orElse(null);
		if (profesor == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(profesor.getId() + "," + profesor.getNombre() + "," + profesor.getApe1() + ","
				+ profesor.getApe2() + "," + profesor.getDni());
	}
	
	//Obsoleto
	@GetMapping("/profesor/{user},{passwd}")
	public ResponseEntity<?> obtenerKey(@PathVariable String user, @PathVariable String passwd) {
		List<Profesor> profesores = profesorRepositorio.findAll();
		if (profesores == null)
			return ResponseEntity.notFound().build();
		Profesor APIKey = null;
		for (int i = 0; i > profesores.size(); i++) {
			Profesor p = profesores.get(i);
			if (p.getUser().equalsIgnoreCase(user) && p.getPassword().equalsIgnoreCase(passwd)) {
				APIKey.setUser(user);
				APIKey.setPassword(passwd);
			}
		}
		return ResponseEntity.ok(APIKey.getUser() + APIKey.getPassword());
	}

	//Metodo login de usuarios normales.
	@PostMapping("/login")
	public ResponseEntity<?> validateUser(@RequestHeader("user") String usuario, @RequestHeader("pswd") String contra) {
		try {
			TypedQuery<Profesor> q = em.createQuery("SELECT p FROM Profesor p WHERE p.user = :user AND p.password = :pswd", Profesor.class);
			q.setParameter("user", usuario);
			q.setParameter("pswd", contra);
			
			List<Profesor> profesor = q.getResultList();
			if (!profesor.isEmpty()) {
				return ResponseEntity.ok(profesor);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso denegado");
			}
		} catch (NoResultException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del sistema");
		}
	}
	
	//Metodo login de usuarios administradores.
	@PostMapping("/loginAdmin")
	public ResponseEntity<?> validateUserAdmin(@RequestHeader("user") String usuario, @RequestHeader("pswd") String contra) {
		try {		
			
			TypedQuery<Profesor> q = em.createQuery("SELECT p FROM Profesor p JOIN ProfesorTienePerfil pp ON p.id = pp.profesor WHERE p.user = :user AND p.password = :pswd AND (pp.perfil = 2 OR pp.perfil = 4)", Profesor.class);
			q.setParameter("user", usuario);
			q.setParameter("pswd", contra);
			
			List<Profesor> profesor = q.getResultList();
			
			if(!profesor.isEmpty()) {
				return ResponseEntity.ok(profesor);
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso denegado");
			}
		} catch (PersistenceException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del sistema");
		}
	}
}
