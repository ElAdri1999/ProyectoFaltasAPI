package com.example.appfaltasapiservice.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.appfaltasapiservice.AppFaltasApiServiceApplication;
import com.example.appfaltasapiservice.modelo.AvisoGuardia;
import com.example.appfaltasapiservice.modelo.Guardia;
import com.example.appfaltasapiservice.modelo.Horario;
import com.example.appfaltasapiservice.repositorio.AvisoGuardiaRepositorio;
import com.example.appfaltasapiservice.repositorio.GuardiaRepositorio;
import com.example.appfaltasapiservice.repositorio.HorarioRepositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

@RestController
public class AvisoGuardiaController {
	private final AvisoGuardiaRepositorio avisoGuardiaRepositorio;
	private final HorarioRepositorio horarioRepositorio;
	private final GuardiaRepositorio guardiaRepositorio;
	
	public AvisoGuardiaController(AvisoGuardiaRepositorio avisoGuardiaRepositorio, HorarioRepositorio horarioRepositorio,
			GuardiaRepositorio guardiaRepositorio) {
		this.avisoGuardiaRepositorio = avisoGuardiaRepositorio;
		this.horarioRepositorio = horarioRepositorio;
		this.guardiaRepositorio = guardiaRepositorio;
	}
	
	@GetMapping("/avisos-guardias")
	public List<AvisoGuardia> obtenerTodos() {
		return avisoGuardiaRepositorio.findAll();
	}
	
	//Metodo usado para crear avisos de guardia
	@PostMapping("/crear-aviso")
	public ResponseEntity<?> crearAviso(@RequestBody AvisoGuardia aviso, @RequestHeader("key") String key) {
		if(!AppFaltasApiServiceApplication.validarKey(key)) return null;
		
		AvisoGuardia nuevo = avisoGuardiaRepositorio.save(aviso);
		Guardia nuevaGuardia = new Guardia();
		nuevaGuardia.setProf_falta(aviso.getProfesor());
		nuevaGuardia.setEstado("C");
		nuevaGuardia.setFecha(aviso.getFecha_guardia());
		nuevaGuardia.setHorario(aviso.getHorario());
		
		Horario h = horarioRepositorio.findById(aviso.getHorario()).orElse(null);
		nuevaGuardia.setDia_semana(h.getDia_semana());
		nuevaGuardia.setHora(h.getHora());
		nuevaGuardia.setGrupo(h.getGrupo());
		nuevaGuardia.setAula(h.getAula());
		
		nuevaGuardia.setAviso(nuevo.getId_aviso());
		nuevaGuardia.setObservaciones(aviso.getObservaciones());
		
		guardiaRepositorio.save(nuevaGuardia);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
	}
}