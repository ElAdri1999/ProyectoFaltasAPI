package com.example.appfaltasapiservice.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "perfiles")
public class Perfil {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idperfil;
	
	@Column(length = 20)
	private String nom_perfil;

	public Perfil() {
		super();
	}

	public Perfil(Integer idperfil, String nom_perfil) {
		super();
		this.idperfil = idperfil;
		this.nom_perfil = nom_perfil;
	}

	public Integer getIdperfil() {
		return idperfil;
	}

	public String getNom_perfil() {
		return nom_perfil;
	}

	public void setNom_perfil(String nom_perfil) {
		this.nom_perfil = nom_perfil;
	}
	
	
}
