package com.example.amq.models;



public class DtIdValor {
	private int id ;
	private String valor = null;

	public DtIdValor(int id, String valor) {
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
}
