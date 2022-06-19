package com.example.amq.models;

import java.util.ArrayList;
import java.util.List;

public class DtAlojamiento {
	
	private Integer id;
	private Boolean activo;
	private String descripcion;
	private DtDireccion direcion;
	private String nombre;
	private ArrayList<DtHabitacion> habs;

	public DtAlojamiento(Integer id,Boolean activo, String descripcion, DtDireccion direcion, 
			String nombre, ArrayList<DtHabitacion> habs ) {
		super();
		this.id = id;
		this.activo = activo;
		this.descripcion = descripcion;
		this.direcion = direcion;
		this.nombre = nombre;
		this.habs = habs;
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
		return habs;
	}

	public void setHabs(ArrayList<DtHabitacion> habs) {
		this.habs = habs;
	}
}
