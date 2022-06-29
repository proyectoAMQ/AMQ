package com.example.amq.models;

import java.util.Date;
import java.util.List;

public class DtReservaAlojHab {
	private int res_id;
	private ReservaEstado res_estado;
	private Date res_fechaInicio;
	private Date res_fechaFin;
	private int res_cantDias;
	private DtCalificacion res_calificacion;
	
	private int aloj_id;
	private Boolean aloj_activo;
	private String aloj_descripcion;
	private int aloj_idAnfitrion;
	private DtDireccion aloj_direccion;
	private String aloj_nombre;
	
	private int hab_id;
	private String hab_descripcion;
	private Double hab_precioNoche;
	private int hab_camas;
	private DtServicios hab_servicios;
	
	private List<DtFactura> facturas;
	
	public DtReservaAlojHab() {	}
	
	public DtReservaAlojHab(int res_id, ReservaEstado res_estado, Date res_fechaInicio, Date res_fechaFin,
			int res_cantDias, DtCalificacion res_calificacion, int aloj_id, Boolean aloj_activo, String aloj_descripcion,
			int aloj_idAnfitrion, DtDireccion aloj_direccion, String aloj_nombre, int hab_id, String hab_descripcion,
			Double hab_precioNoche, int hab_camas, DtServicios hab_servicios, List<DtFactura> facturas) {
		super();
		this.res_id = res_id;
		this.res_estado = res_estado;
		this.res_fechaInicio = res_fechaInicio;
		this.res_fechaFin = res_fechaFin;
		this.res_cantDias = res_cantDias;
		this.res_calificacion = res_calificacion;
		this.aloj_id = aloj_id;
		this.aloj_activo = aloj_activo;
		this.aloj_descripcion = aloj_descripcion;
		this.aloj_idAnfitrion = aloj_idAnfitrion;
		this.aloj_direccion = aloj_direccion;
		this.aloj_nombre = aloj_nombre;
		this.hab_id = hab_id;
		this.hab_descripcion = hab_descripcion;
		this.hab_precioNoche = hab_precioNoche;
		this.hab_camas = hab_camas;
		this.hab_servicios = hab_servicios;
		this.facturas = facturas;
	}


	public int getRes_id() {
		return res_id;
	}

	public void setRes_id(int res_id) {
		this.res_id = res_id;
	}

	public ReservaEstado getRes_estado() {
		return res_estado;
	}

	public void setRes_estado(ReservaEstado res_estado) {
		this.res_estado = res_estado;
	}

	public Date getRes_fechaInicio() {
		return res_fechaInicio;
	}

	public void setRes_fechaInicio(Date res_fechaInicio) {
		this.res_fechaInicio = res_fechaInicio;
	}

	public Date getRes_fechaFin() {
		return res_fechaFin;
	}

	public void setRes_fechaFin(Date res_fechaFin) {
		this.res_fechaFin = res_fechaFin;
	}

	public int getRes_cantDias() {
		return res_cantDias;
	}

	public void setRes_cantDias(int res_cantDias) {
		this.res_cantDias = res_cantDias;
	}

	public DtCalificacion getRes_calificacion() {
		return res_calificacion;
	}

	public void setRes_calificacion(DtCalificacion res_calificacion) {
		this.res_calificacion = res_calificacion;
	}

	public int getAloj_id() {
		return aloj_id;
	}

	public void setAloj_id(int aloj_id) {
		this.aloj_id = aloj_id;
	}

	public Boolean getAloj_activo() {
		return aloj_activo;
	}

	public void setAloj_activo(Boolean aloj_activo) {
		this.aloj_activo = aloj_activo;
	}

	public String getAloj_descripcion() {
		return aloj_descripcion;
	}

	public void setAloj_descripcion(String aloj_descripcion) {
		this.aloj_descripcion = aloj_descripcion;
	}

	public int getAloj_idAnfitrion() {
		return aloj_idAnfitrion;
	}

	public void setAloj_idAnfitrion(int aloj_idAnfitrion) {
		this.aloj_idAnfitrion = aloj_idAnfitrion;
	}

	public DtDireccion getAloj_direccion() {
		return aloj_direccion;
	}

	public void setAloj_direccion(DtDireccion aloj_direccion) {
		this.aloj_direccion = aloj_direccion;
	}

	public String getAloj_nombre() {
		return aloj_nombre;
	}

	public void setAloj_nombre(String aloj_nombre) {
		this.aloj_nombre = aloj_nombre;
	}

	public int getHab_id() {
		return hab_id;
	}

	public void setHab_id(int hab_id) {
		this.hab_id = hab_id;
	}

	public String getHab_descripcion() {
		return hab_descripcion;
	}

	public void setHab_descripcion(String hab_descripcion) {
		this.hab_descripcion = hab_descripcion;
	}

	public Double getHab_precioNoche() {
		return hab_precioNoche;
	}

	public void setHab_precioNoche(Double hab_precioNoche) {
		this.hab_precioNoche = hab_precioNoche;
	}

	public int getHab_camas() {
		return hab_camas;
	}

	public void setHab_camas(int hab_camas) {
		this.hab_camas = hab_camas;
	}

	public DtServicios getHab_servicios() {
		return hab_servicios;
	}

	public void setHab_servicios(DtServicios hab_servicios) {
		this.hab_servicios = hab_servicios;
	}

	public List<DtFactura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<DtFactura> facturas) {
		this.facturas = facturas;
	}

			
}
