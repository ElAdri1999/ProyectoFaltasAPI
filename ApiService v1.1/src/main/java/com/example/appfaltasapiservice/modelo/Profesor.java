package com.example.appfaltasapiservice.modelo;


import jakarta.persistence.*;

@Entity
@Table(name = "profesores")
public class Profesor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 50)
	private String apikey;
	
	@Column(length = 9)
	private String dni;
	
	@Column(length = 15)
	private String user;
	
	@Column(length = 32)
	private String password;
	
	@Column(length = 45)
	private String nombre;
	
	@Column(length = 45)
	private String ape1;
	
	@Column(length = 45)
	private String ape2;
	
	@Column(length = 9)
	private String tfno;
	
	private Boolean baja;
	
	private Boolean activo;
	
	@Column(length = 5)
	private String dept_cod;
	
	private Integer id_sustituye;
	
	public Profesor() {
		
	}

	public Profesor(Integer id, String apikey, String user, String password, String dni, String nombre, String ape1, String ape2, String tfno, Boolean baja,
			Boolean activo, String dept_cod, Integer id_sustituye) {
		super();
		this.id = id;
		this.apikey = apikey;
		this.user = user;
		this.password = password;
		this.dni = dni;
		this.nombre = nombre;
		this.ape1 = ape1;
		this.ape2 = ape2;
		this.tfno = tfno;
		this.baja = baja;
		this.activo = activo;
		this.dept_cod = dept_cod;
		this.id_sustituye = id_sustituye;
	}

	public Integer getId() {
		return id;
	}
	
	public String getApikey() {
		return apikey;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getTfno() {
		return tfno;
	}

	public void setTfno(String tfno) {
		this.tfno = tfno;
	}

	public Boolean getBaja() {
		return baja;
	}

	public void setBaja(Boolean baja) {
		this.baja = baja;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getDept_cod() {
		return dept_cod;
	}

	public void setDept_cod(String dept_cod) {
		this.dept_cod = dept_cod;
	}

	public Integer getId_sustituye() {
		return id_sustituye;
	}

	public void setId_sustituye(Integer id_sustituye) {
		this.id_sustituye = id_sustituye;
	}
	
}
