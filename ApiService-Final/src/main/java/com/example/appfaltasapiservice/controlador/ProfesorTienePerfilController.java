package com.example.appfaltasapiservice.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.appfaltasapiservice.modelo.ProfesorTienePerfil;
import com.example.appfaltasapiservice.repositorio.ProfesorTienePerfilRepositorio;

@RestController
public class ProfesorTienePerfilController {
	private final ProfesorTienePerfilRepositorio profesorTienePerfilRepositorio;

	public ProfesorTienePerfilController(ProfesorTienePerfilRepositorio profesorTienePerfilRepositorio) {
		super();
		this.profesorTienePerfilRepositorio = profesorTienePerfilRepositorio;
	}
	
	@GetMapping("/profesor-perfil")
	public List<ProfesorTienePerfil> obtenerTodos() {
		return profesorTienePerfilRepositorio.findAll();
	}
}
