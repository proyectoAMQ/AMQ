package com.example.amq.models;

import java.sql.Date;

public class DtFactura {
	
	private int idFactura;
	private PagoEstado pagoEstado;
	private Double monto;
	private DtFecha fecha;
	private boolean descuento;
	private Double montoDescuento;
	private String idPaypal;

	

	public DtFactura(int idFactura, PagoEstado pagoEstado, Double monto, DtFecha fecha, boolean descuento,
			Double montoDescuento, String idPaypal) {
		super();
		this.idFactura = idFactura;
		this.pagoEstado = pagoEstado;
		this.monto = monto;
		this.fecha = fecha;
		this.descuento = descuento;
		this.montoDescuento = montoDescuento;
		this.idPaypal = idPaypal;
	}

	public PagoEstado getPagoEstado() {
		return pagoEstado;
	}

	public void setPagoEstado(PagoEstado pagoEstado) {
		this.pagoEstado = pagoEstado;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public DtFecha getFecha() {
		return fecha;
	}

	public void setFecha(DtFecha fecha) {
		this.fecha = fecha;
	}

	public boolean isDescuento() {
		return descuento;
	}

	public void setDescuento(boolean descuento) {
		this.descuento = descuento;
	}

	public Double getMontoDescuento() {
		return montoDescuento;
	}

	public void setMontoDescuento(Double montoDescuento) {
		this.montoDescuento = montoDescuento;
	}

	public int getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public String getIdPaypal() {
		return idPaypal;
	}

	public void setIdPaypal(String idPaypal) {
		this.idPaypal = idPaypal;
	}
	
}
