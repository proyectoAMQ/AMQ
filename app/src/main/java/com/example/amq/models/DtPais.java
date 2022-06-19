package com.example.amq.models;



public class DtPais {
	private int id;
	private String valor;
	
	public DtPais() {}
	
	public DtPais(int id, String valor) {
		super();
		this.id = id;
		this.valor = valor;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getNombre() {
		return valor;
	}
	
}
