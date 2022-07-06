package com.example.amq.models;

import java.io.Serializable;

public class DtPais implements Serializable {
	private int id;
	private String nombre;
	
	public DtPais() {}
	
	public DtPais(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
