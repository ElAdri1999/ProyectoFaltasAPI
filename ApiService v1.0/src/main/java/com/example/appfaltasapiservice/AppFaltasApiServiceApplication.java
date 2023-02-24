package com.example.appfaltasapiservice;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.appfaltasapiservice.modelo.Profesor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

@SpringBootApplication
public class AppFaltasApiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppFaltasApiServiceApplication.class, args);
	}

	public static boolean validarKey(String key) {
		String[] args = key.split(" ");
		String user = args[0];
		String pwd = args[1];
		
		EntityManager em = Persistence.createEntityManagerFactory("API").createEntityManager();
		TypedQuery<Profesor> q = em.createQuery("SELECT p FROM Profesor p WHERE p.user = :u and p.password = :c", Profesor.class);
		q.setParameter("u", user);
		q.setParameter("c", pwd);

		List<Profesor> profesor = q.getResultList();
		if(profesor.isEmpty()) {
			return false;
		}
		
		return true;
	}
}
