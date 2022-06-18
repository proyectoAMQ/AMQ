package com.example.amq.models;

public class DtHabitacion {
	
	private int id;
	private String descripcion;
	private Double precioNoche;
	private int camas;
	private DtServicios dtservicios;
	
	public DtHabitacion(int id, String descripcion, Double precioNoche, int camas, 
			DtServicios dtservicios) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.precioNoche = precioNoche;
		this.camas = camas;
		this.dtservicios = dtservicios;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecioNoche() {
		return precioNoche;
	}

	public void setPrecioNoche(Double precioNoche) {
		this.precioNoche = precioNoche;
	}

	public int getCamas() {
		return camas;
	}

	public void setCamas(int camas) {
		this.camas = camas;
	}

	public DtServicios getDtservicios() {
		return dtservicios;
	}

	public void setDtservicios(DtServicios dtservicios) {
		this.dtservicios = dtservicios;
	}
	
	

}
