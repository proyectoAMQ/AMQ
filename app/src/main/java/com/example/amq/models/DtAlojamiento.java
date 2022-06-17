package com.example.amq.models;

import java.util.List;

public class DtAlojamiento {
	
	private Integer id;
	private Boolean activo;
	private String descripcion;
	private DtDireccion direcion;
	private String nombre;
	private List<DtHabitacion> habitaciones;

	public DtAlojamiento(Integer id, Boolean activo, String descripcion, DtDireccion direcion,
						 String nombre, List<DtHabitacion> habitaciones ) {
		super();
		this.id = id;
		this.activo = activo;
		this.descripcion = descripcion;
		this.direcion = direcion;
		this.nombre = nombre;
		this.habitaciones = habitaciones;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getActivo() {
		return activo;
	}

	public Boolean isActivo() {
		return activo;
	}
	
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public DtDireccion getDirecion() {
		return direcion;
	}

	public void setDirecion(DtDireccion direcion) {
		this.direcion = direcion;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DtHabitacion> getHabs() {
		return habitaciones;
	}

	public void setHabs(List<DtHabitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}
}
