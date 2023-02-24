package com.example.appfaltasapiservice.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.appfaltasapiservice.modelo.HorarioGuardia;
import com.example.appfaltasapiservice.repositorio.HorarioGuardiaRepositorio;

@RestController
public class HorarioGuardiaController {
	private final HorarioGuardiaRepositorio horarioGuardiaRepositorio;

	public HorarioGuardiaController(HorarioGuardiaRepositorio horarioGuardiaRepositorio) {
		super();
		this.horarioGuardiaRepositorio = horarioGuardiaRepositorio;
	}
	
	@GetMapping("/horario-guardias")
	public List<HorarioGuardia> obtenerTodos() {
		return horarioGuardiaRepositorio.findAll();
	}
}
