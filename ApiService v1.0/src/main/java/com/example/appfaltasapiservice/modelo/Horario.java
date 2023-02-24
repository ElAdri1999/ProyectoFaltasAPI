package com.example.appfaltasapiservice.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "horario")
public class Horario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer profesor;
	
	private Integer dia_semana;
	
	private Integer hora;
	
	@Column(length = 20)
	private String aula;
	
	@Column(length = 10)
	private String grupo;
	
	@Column(length = 10)
	private String materia;
	
	private Boolean genera_guardia;

	public Horario() {
		super();
	}

	public Horario(Integer id, Integer profesor, Integer dia_semana, Integer hora, String aula, String grupo,
			String materia, Boolean genera_guardia) {
		super();
		this.id = id;
		this.profesor = profesor;
		this.dia_semana = dia_semana;
		this.hora = hora;
		this.aula = aula;
		this.grupo = grupo;
		this.materia = materia;
		this.genera_guardia = genera_guardia;
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

	public Integer getHora() {
		return hora;
	}

	public void setHora(Integer hora) {
		this.hora = hora;
	}

	public String getAula() {
		return aula;
	}

	public void setAula(String aula) {
		this.aula = aula;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public Boolean getGenera_guardia() {
		return genera_guardia;
	}

	public void setGenera_guardia(Boolean genera_guardia) {
		this.genera_guardia = genera_guardia;
	}
	
	
}
