package com.example.appfaltasapiservice.controlador;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.appfaltasapiservice.AppFaltasApiServiceApplication;
import com.example.appfaltasapiservice.modelo.Horario;
import com.example.appfaltasapiservice.modelo.Profesor;
import com.example.appfaltasapiservice.repositorio.HorarioRepositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

@RestController
public class HorarioController {
	private final HorarioRepositorio horarioRepositorio;
	private EntityManager em;

	public HorarioController(HorarioRepositorio horarioRepositorio) {
		this.horarioRepositorio = horarioRepositorio;
		em = Persistence.createEntityManagerFactory("API").createEntityManager();
	}
	
	@GetMapping("/horarios")
	public ResponseEntity<?> obtenerTodos() {
		return ResponseEntity.ok(horarioRepositorio.findAll());
	}
	
	//Extrae los horarios de un profesor dado un id
	@GetMapping("horario-profesor/{id}")
	public ResponseEntity<?> horariosDeProfesor(@PathVariable String id, @RequestHeader("key") String key) {
		if(!AppFaltasApiServiceApplication.validarKey(key)) return null;
		
		TypedQuery<Horario> q = em.createQuery("SELECT h FROM Horario h WHERE h.profesor = :p", Horario.class);
		q.setParameter("p", id);
		
		List<Horario> horarios = q.getResultList();
		if(!horarios.isEmpty()) {
			return ResponseEntity.ok(horarios);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se encontraron resultados");
	}
	
	//Metodo para autocompletar los formularios de la aplicación de escritorio
	@PostMapping("/completar-guardias")
	public ResponseEntity<?> autoCompletarGuardias(@RequestHeader("nombre") String nombre, @RequestHeader("apellidos") String apellidos, @RequestHeader("fecha") String fecha, @RequestHeader("hora") String hora) {
		try {
			String[] apellidoSeparado = apellidos.split(" ");
			String ape1 = apellidoSeparado[0];
			String ape2 = apellidoSeparado[1];
			TypedQuery<Horario> q = em.createQuery("SELECT h FROM Horario h JOIN Profesor p ON h.profesor = p.id WHERE p.nombre = :nombre AND p.ape1 = :ape1 AND p.ape2 = :ape2 AND h.dia_semana = :dia AND h.hora = :hora", Horario.class);
			q.setParameter("nombre", nombre);
			q.setParameter("ape1", ape1);
			q.setParameter("ape2", ape2);
			LocalDate fech = LocalDate.parse(fecha);
			int dia = fech.getDayOfWeek().getValue();
			q.setParameter("dia", dia);
			q.setParameter("hora", hora);			
			List<Horario> horario = q.getResultList();
			if (!horario.isEmpty()) {
				return ResponseEntity.ok(horario);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso denegado");
			}
		} catch (NoResultException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del sistema");
		}
	}
}
