package com.example.amq.models;

public class DtCalificarDatosRequeridosInput {
	private int idUsuario;

	public DtCalificarDatosRequeridosInput(int idUsuario) {
		super();
		this.idUsuario = idUsuario;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
}
