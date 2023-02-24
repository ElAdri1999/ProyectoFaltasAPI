package com.example.appfaltasapiservice.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.appfaltasapiservice.modelo.BajaPermiso;
import com.example.appfaltasapiservice.repositorio.BajaPermisoRepositorio;

@RestController
public class BajaPermisoController {
	private final BajaPermisoRepositorio bajaPermisoRepositorio;

	public BajaPermisoController(BajaPermisoRepositorio bajaPermisoRepositorio) {
		super();
		this.bajaPermisoRepositorio = bajaPermisoRepositorio;
	}
	
	@GetMapping("/bajas-permisos")
	public List<BajaPermiso> obtenerTodos() {
		return bajaPermisoRepositorio.findAll();
	}
}
