package com.example.appfaltasapiservice.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.appfaltasapiservice.modelo.Perfil;
import com.example.appfaltasapiservice.repositorio.PerfilRepositorio;

@RestController
public class PerfilController {
	private final PerfilRepositorio perfilRepositorio;

	public PerfilController(PerfilRepositorio perfilRepositorio) {
		super();
		this.perfilRepositorio = perfilRepositorio;
	}
	
	@GetMapping("perfiles")
	public List<Perfil> obtenerTodos() {
		return perfilRepositorio.findAll();
	}
}
