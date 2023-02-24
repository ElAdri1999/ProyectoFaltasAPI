package com.example.appfaltasapiservice.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "avisos_guardia")
public class AvisoGuardia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_aviso;
	
	private Integer profesor;
	
	private Integer horario;
	
	@Column(length = 45)
	private String motivo;
	
	private String observaciones;
	
	private Boolean confirmado;
	
	private Boolean anulado;
	
	private LocalDate fecha_guardia;
	
	private LocalDateTime fecha_hora_aviso;

	public AvisoGuardia() {
		super();
	}

	public AvisoGuardia(Integer id_aviso, Integer profesor, Integer horario, String motivo, String observaciones,
			Boolean confirmado, Boolean anulado, LocalDate fecha_guardia, LocalDateTime fecha_hora_aviso) {
		super();
		this.id_aviso = id_aviso;
		this.profesor = profesor;
		this.horario = horario;
		this.motivo = motivo;
		this.observaciones = observaciones;
		this.confirmado = confirmado;
		this.anulado = anulado;
		this.fecha_guardia = fecha_guardia;
		this.fecha_hora_aviso = fecha_hora_aviso;
	}

	public Integer getId_aviso() {
		return id_aviso;
	}

	public Integer getProfesor() {
		return profesor;
	}

	public void setProfesor(Integer profesor) {
		this.profesor = profesor;
	}

	public Integer getHorario() {
		return horario;
	}

	public void setHorario(Integer horario) {
		this.horario = horario;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Boolean getConfirmado() {
		return confirmado;
	}

	public void setConfirmado(Boolean confirmado) {
		this.confirmado = confirmado;
	}

	public Boolean getAnulado() {
		return anulado;
	}

	public void setAnulado(Boolean anulado) {
		this.anulado = anulado;
	}

	public LocalDate getFecha_guardia() {
		return fecha_guardia;
	}

	public void setFecha_guardia(LocalDate fecha_guardia) {
		this.fecha_guardia = fecha_guardia;
	}

	public LocalDateTime getFecha_hora_aviso() {
		return fecha_hora_aviso;
	}

	public void setFecha_hora_aviso(LocalDateTime fecha_hora_aviso) {
		this.fecha_hora_aviso = fecha_hora_aviso;
	}
	
	
}
