package com.example.appfaltasapiservice.modelo;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "bajas_permisos")
public class BajaPermiso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idbaja;
	
	private Integer profesor;
	
	private LocalDate fechainicio;
	
	private LocalDate fechafin;
	
	@Column(length = 1)
	private Character tipo;
	
	public BajaPermiso() {
		
	}

	public BajaPermiso(Integer idbaja, Integer profesor, LocalDate fechainicio, LocalDate fechafin, Character tipo) {
		super();
		this.idbaja = idbaja;
		this.profesor = profesor;
		this.fechainicio = fechainicio;
		this.fechafin = fechafin;
		this.tipo = tipo;
	}

	public Integer getIdbaja() {
		return idbaja;
	}

	public Integer getProfesor() {
		return profesor;
	}

	public void setProfesor(Integer profesor) {
		this.profesor = profesor;
	}

	public LocalDate getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(LocalDate fechainicio) {
		this.fechainicio = fechainicio;
	}

	public LocalDate getFechafin() {
		return fechafin;
	}

	public void setFechafin(LocalDate fechafin) {
		this.fechafin = fechafin;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}
	
	
}
