package com.example.appfaltasapiservice.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.appfaltasapiservice.modelo.HorarioCentro;
import com.example.appfaltasapiservice.repositorio.HorarioCentroRepositorio;

@RestController
public class HorarioCentroController {
	private final HorarioCentroRepositorio horarioCentroRepositorio;

	public HorarioCentroController(HorarioCentroRepositorio horarioCentroRepositorio) {
		super();
		this.horarioCentroRepositorio = horarioCentroRepositorio;
	}
	
	@GetMapping("/horarios-centro")
	public List<HorarioCentro> obtenerTodos() {
		return horarioCentroRepositorio.findAll();
	}
}
