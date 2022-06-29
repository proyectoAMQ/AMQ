package com.example.amq.models;

import java.util.List;

public class DtResHuespEstado {
	private int idHu;
	private List<ReservaEstado> resEstado;
	
	
	
	public DtResHuespEstado() {	super(); }

	public DtResHuespEstado(int idHu, List<ReservaEstado> resEstado) {
		super();
		this.idHu = idHu;
		this.resEstado = resEstado;
	}
	
	public int getIdHu() {
		return idHu;
	}
	public void setIdHu(int idHu) {
		this.idHu = idHu;
	}
	public List<ReservaEstado> getResEstado() {
		return resEstado;
	}
	public void setResEstado(List<ReservaEstado> resEstado) {
		this.resEstado = resEstado;
	}
	
	
}
