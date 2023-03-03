package com.example.appfaltasapiservice.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "horarioguardias")
public class HorarioGuardia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer profesor;
	
	private Integer dia_semana;
	
	private Integer hora_guardia;
	
	private Integer realizadas;

	public HorarioGuardia() {
		super();
	}

	public HorarioGuardia(Integer id, Integer profesor, Integer dia_semana, Integer hora_guardia, Integer realizadas) {
		super();
		this.id = id;
		this.profesor = profesor;
		this.dia_semana = dia_semana;
		this.hora_guardia = hora_guardia;
		this.realizadas = realizadas;
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

	public Integer getDia_semana() {
		return dia_semana;
	}

	public void setDia_semana(Integer dia_semana) {
		this.dia_semana = dia_semana;
	}

	public Integer getHora_guardia() {
		return hora_guardia;
	}

	public void setHora_guardia(Integer hora_guardia) {
		this.hora_guardia = hora_guardia;
	}

	public Integer getRealizadas() {
		return realizadas;
	}

	public void setRealizadas(Integer realizadas) {
		this.realizadas = realizadas;
	}
	
	
}
