package com.example.appfaltasapiservice.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "profesor_tiene_perfil")
public class ProfesorTienePerfil {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer profesor;
	
	private Integer perfil;

	public ProfesorTienePerfil() {
		super();
	}

	public ProfesorTienePerfil(Integer id, Integer profesor, Integer perfil) {
		super();
		this.id = id;
		this.profesor = profesor;
		this.perfil = perfil;
	}

	public Integer getId() {
		return id;
	}

	public Integer getProfesor() {
		return profesor;
	}

	public void setProfesor(Integer profesor) {
		this.profesor = profesor;
	}

	public Integer getPerfil() {
		return perfil;
	}

	public void setPerfil(Integer perfil) {
		this.perfil = perfil;
	}
	
	
}
