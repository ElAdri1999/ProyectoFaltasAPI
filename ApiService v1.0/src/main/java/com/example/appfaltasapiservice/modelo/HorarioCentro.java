package com.example.appfaltasapiservice.modelo;

import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name = "horariocentro")
public class HorarioCentro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private LocalTime horainicio;
	
	private LocalTime horafin;
	
	@Column(length = 1)
	private String turno;

	public HorarioCentro() {
		super();
	}

	public HorarioCentro(Integer id, LocalTime horainicio, LocalTime horafin, String turno) {
		super();
		this.id = id;
		this.horainicio = horainicio;
		this.horafin = horafin;
		this.turno = turno;
	}

	public Integer getId() {
		return id;
	}

	public LocalTime getHorainicio() {
		return horainicio;
	}

	public void setHorainicio(LocalTime horainicio) {
		this.horainicio = horainicio;
	}

	public LocalTime getHorafin() {
		return horafin;
	}

	public void setHorafin(LocalTime horafin) {
		this.horafin = horafin;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}
	
	
}
