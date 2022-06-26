package com.example.amq.models;

import java.util.List;

public class DtRegistroHuesped {

	private String email;
	private String nombre;
	private String apellido;
	private String pass;

	public DtRegistroHuesped(String email, String nombre, String apellido, String pass) {
		this.email = email;
		this.nombre = nombre;
		this.apellido = apellido;
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}
