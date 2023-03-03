package com.example.appfaltasapiservice.modelo;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "guardias")
public class Guardia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer prof_falta;
	
	private Integer prof_hace_guardia;
	
	@Column(length = 1)
	private String estado;
	
	private LocalDate fecha;
	
	private Integer horario;
	
	private Integer dia_semana;
	
	private Integer hora;
	
	private Integer aviso;
	
	@Column(length = 10)
	private String grupo;
	
	@Column(length = 10)
	private String aula;
	
	private String observaciones;

	public Guardia() {
		super();
	}

	public Guardia(Integer id, Integer prof_falta, Integer prof_hace_guardia, String estado, LocalDate fecha,
			Integer horario, Integer dia_semana, Integer hora, Integer aviso, String grupo, String aula,
			String observaciones) {
		super();
		this.id = id;
		this.prof_falta = prof_falta;
		this.prof_hace_guardia = prof_hace_guardia;
		this.estado = estado;
		this.fecha = fecha;
		this.horario = horario;
		this.dia_semana = dia_semana;
		this.hora = hora;
		this.aviso = aviso;
		this.grupo = grupo;
		this.aula = aula;
		this.observaciones = observaciones;
	}

	public Integer getId() {
		return id;
	}

	public Integer getProf_falta() {
		return prof_falta;
	}

	public void setProf_falta(Integer prof_falta) {
		this.prof_falta = prof_falta;
	}

	public Integer getProf_hace_guardia() {
		return prof_hace_guardia;
	}

	public void setProf_hace_guardia(Integer prof_hace_guardia) {
		this.prof_hace_guardia = prof_hace_guardia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Integer getHorario() {
		return horario;
	}

	public void setHorario(Integer horario) {
		this.horario = horario;
	}

	public Integer getDia_semana() {
		return dia_semana;
	}

	public void setDia_semana(Integer dia_semana) {
		this.dia_semana = dia_semana;
	}

	public Integer getHora() {
		return hora;
	}

	public void setHora(Integer hora) {
		this.hora = hora;
	}

	public Integer getAviso() {
		return aviso;
	}

	public void setAviso(Integer aviso) {
		this.aviso = aviso;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getAula() {
		return aula;
	}

	public void setAula(String aula) {
		this.aula = aula;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
}
