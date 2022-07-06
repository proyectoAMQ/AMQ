package com.example.amq.models;


public class DtEnviarCalificacion {
	private int idUsuario;
	private int idReserva;
	private Integer calificacion;
	private String resena;
	
	
	public DtEnviarCalificacion(int idUsuario, int idReserva, Integer calificacion, String resena) {
		super();
		this.idUsuario = idUsuario;
		this.idReserva = idReserva;
		this.calificacion = calificacion;
		this.resena = resena;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}
	public Integer getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(Integer calificacion) {
		this.calificacion = calificacion;
	}
	public String getResena() {
		return resena;
	}
	public void setResena(String resena) {
		this.resena = resena;
	}
	
}
