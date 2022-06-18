package com.example.amq.models;

public class DtFiltrosAloj {
    private Boolean aloj_activo;
    private Integer hab_precio;
    private Integer hab_precio_hasta;
    private Integer aloj_idPais;


    public DtFiltrosAloj(Boolean aloj_activo, Integer hab_precio, Integer hab_precio_hasta, Integer aloj_idPais) {
        this.aloj_activo = aloj_activo;
        this.hab_precio = hab_precio;
        this.hab_precio_hasta = hab_precio_hasta;
        this.aloj_idPais = aloj_idPais;
    }

    public Boolean getAloj_activo() {
        return aloj_activo;
    }

    public void setAloj_activo(Boolean aloj_activo) {
        this.aloj_activo = aloj_activo;
    }

    public Integer getHab_precio() {
        return hab_precio;
    }

    public void setHab_precio(Integer hab_precio) {
        this.hab_precio = hab_precio;
    }

    public Integer getHab_precio_hasta() {
        return hab_precio_hasta;
    }

    public void setHab_precio_hasta(Integer hab_precio_hasta) {
        this.hab_precio_hasta = hab_precio_hasta;
    }

    public Integer getAloj_idPais() {
        return aloj_idPais;
    }

    public void setAloj_idPais(Integer aloj_idPais) {
        this.aloj_idPais = aloj_idPais;
    }
}
