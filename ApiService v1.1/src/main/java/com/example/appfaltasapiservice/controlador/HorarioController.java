package com.example.appfaltasapiservice.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.appfaltasapiservice.AppFaltasApiServiceApplication;
import com.example.appfaltasapiservice.modelo.Horario;
import com.example.appfaltasapiservice.repositorio.HorarioRepositorio;

import jakarta.persistence.EntityManager;
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
}
