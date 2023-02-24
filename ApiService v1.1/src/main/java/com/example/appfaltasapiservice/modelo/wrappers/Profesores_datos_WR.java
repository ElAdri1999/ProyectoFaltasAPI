package com.example.appfaltasapiservice.modelo.wrappers;

import java.util.ArrayList;
import java.util.List;

import com.example.appfaltasapiservice.modelo.Profesor;

public class Profesores_datos_WR {
	
	private Integer id;

	private String dni;
	
	private String nombre;

	private String ape1;
	
	private String ape2;
	
	public Profesores_datos_WR(Integer id, String dni, String nombre, String ape1, String ape2) {
		super();
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.ape1 = ape1;
		this.ape2 = ape2;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApe1() {
		return ape1;
	}

	public void setApe1(String ape1) {
		this.ape1 = ape1;
	}

	public String getApe2() {
		return ape2;
	}

	public void setApe2(String ape2) {
		this.ape2 = ape2;
	}

	@Override
	public String toString() {
		return "Profesores_datos_WR [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", ape1=" + ape1 + ", ape2="
				+ ape2 + "]";
	}
	
	public static List<Profesores_datos_WR> listaProfesor(List<Profesor> profesores){
		List<Profesores_datos_WR> profes = new ArrayList<>();
		for(int i = 0; i < profesores.size(); i++) {
			Profesor p = profesores.get(i);
			profes.add(new Profesores_datos_WR(p.getId(),p.getDni(),p.getNombre(),p.getApe1(),p.getApe2()));
		}
		return profes;
	}
}
