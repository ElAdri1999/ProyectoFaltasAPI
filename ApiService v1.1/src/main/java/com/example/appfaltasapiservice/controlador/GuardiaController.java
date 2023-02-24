package com.example.appfaltasapiservice.controlador;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.appfaltasapiservice.AppFaltasApiServiceApplication;
import com.example.appfaltasapiservice.modelo.AvisoGuardia;
import com.example.appfaltasapiservice.modelo.Guardia;
import com.example.appfaltasapiservice.modelo.HorarioGuardia;
import com.example.appfaltasapiservice.repositorio.AvisoGuardiaRepositorio;
import com.example.appfaltasapiservice.repositorio.GuardiaRepositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

@RestController
public class GuardiaController {
	private final GuardiaRepositorio guardiaRepositorio;
	private final AvisoGuardiaRepositorio avisoGuardiaRepositorio;
	private EntityManager em;

	public GuardiaController(GuardiaRepositorio guardiaRepositorio, AvisoGuardiaRepositorio avisoGuardiaRepositorio) {
		this.guardiaRepositorio = guardiaRepositorio;
		this.avisoGuardiaRepositorio = avisoGuardiaRepositorio;
		em = Persistence.createEntityManagerFactory("API").createEntityManager();
	}
	
	@GetMapping("/guardias")
	public List<Guardia> obtenerTodas() {
		return guardiaRepositorio.findAll();
	}
	
	@GetMapping("guardias/{id}")
	public ResponseEntity<?> guardiasDeProfesor(@PathVariable String id, @RequestHeader("key") String key) {
		if(!AppFaltasApiServiceApplication.validarKey(key)) return null;
		
		TypedQuery<Guardia> q = em.createQuery("SELECT g FROM Guardia g WHERE g.prof_falta = :id AND g.estado = 'C'", Guardia.class);
		q.setParameter("id", id);
		List<Guardia> guardias = new ArrayList<>();
		if(q.isBound(q.getParameter("id"))) {
			try {
				 guardias = q.getResultList();
				}catch(Exception e) {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la petición");
				}
		}
		if(guardias.isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sin resultados");
		}
		return ResponseEntity.ok(guardias);
	}
	
	@GetMapping("/guardias-por-horario/{id}")
	public ResponseEntity<?> guardiasPorHorario(@PathVariable String id, @RequestHeader("key") String key) {
		if(!AppFaltasApiServiceApplication.validarKey(key)) return null;
		
		TypedQuery<Object[]> q = em.createQuery("SELECT h.dia_semana, h.hora_guardia FROM HorarioGuardia h WHERE h.profesor = :id", Object[].class);
		q.setParameter("id", id);
		List<Object[]> horarios = new ArrayList<>();
		if(q.isBound(q.getParameter("id"))) {
			try {
			 horarios = q.getResultList();
			}catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la petición");
			}
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la petición");
		}
		List<Guardia> guardias = new ArrayList<>();
		TypedQuery<Guardia> q2 = em.createQuery("SELECT g FROM Guardia g WHERE g.fecha = current_date and g.hora = :h and g.dia_semana = :d and g.estado = 'C'", Guardia.class);
		if(!horarios.isEmpty()) {
			horarios.forEach(h -> {
				q2.setParameter("h", h[1]);
				q2.setParameter("d", h[0]);
				
				guardias.addAll(q2.getResultList());
			});
		}
		
		if(!guardias.isEmpty()) return ResponseEntity.ok(guardias);
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sin resultados");
	}
	
	@PutMapping("/modificar-guardia/{id}")
	public ResponseEntity<?> modificarGuardia(@RequestBody Guardia guardia, @PathVariable int id, @RequestHeader("key") String key) {
		if(!AppFaltasApiServiceApplication.validarKey(key)) return null;
		
		Guardia g = guardiaRepositorio.findById(id).orElse(null);
		if(g == null) {
			return ResponseEntity.notFound().build();
		}
		
		g.setEstado("R");
		g.setProf_hace_guardia(guardia.getProf_hace_guardia());
		
		return ResponseEntity.ok(guardiaRepositorio.save(g));
	}
	
	@PutMapping("anular-guardia/{id}")
	public ResponseEntity<?> anularGuardia(@RequestBody Guardia guardia, @PathVariable int id, @RequestHeader("key") String key) {
		if(!AppFaltasApiServiceApplication.validarKey(key)) return null;
		
		Guardia g = guardiaRepositorio.findById(id).orElse(null);
		if(g == null) {
			return ResponseEntity.notFound().build();
		}
		
		g.setEstado("A");
		if(g.getAviso() != null) {
			AvisoGuardia a = avisoGuardiaRepositorio.findById(g.getAviso()).orElse(null);
			if(a != null) {
				a.setFecha_guardia(null);
				a.setFecha_hora_aviso(LocalDateTime.now());
				a.setConfirmado(false);
				a.setAnulado(true);
				
				avisoGuardiaRepositorio.save(a);
			}
		}
		
		return ResponseEntity.ok(guardiaRepositorio.save(g));
	}
}
