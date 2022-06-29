package com.example.amq.models;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DtCalificacion{

	private int id;
	private int calificacionAnfitrion;
	private int calificacionHuesped;
	private String resena;
	private DtFecha fechaResena;

	public DtCalificacion(int id, int calificacionAnfitrion, int calificacionHuesped, String resena, DtFecha fechaResena) {
		this.id = id;
		this.calificacionAnfitrion = calificacionAnfitrion;
		this.calificacionHuesped = calificacionHuesped;
		this.resena = resena;
		this.fechaResena = fechaResena;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCalificacionAnfitrion() {
		return calificacionAnfitrion;
	}

	public void setCalificacionAnfitrion(int calificacionAnfitrion) {
		this.calificacionAnfitrion = calificacionAnfitrion;
	}

	public int getCalificacionHuesped() {
		return calificacionHuesped;
	}

	public void setCalificacionHuesped(int calificacionHuesped) {
		this.calificacionHuesped = calificacionHuesped;
	}

	public String getResena() {
		return resena;
	}

	public void setResena(String resena) {
		this.resena = resena;
	}

	public DtFecha getFechaResena() {
		return fechaResena;
	}

	public void setFechaResena(DtFecha fechaResena) {
		this.fechaResena = fechaResena;
	}
}
