package com.ulpsoft.inmobiliarialaboratorio3.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Contrato implements Serializable {

     private int idContrato;

    @SerializedName("precioContrato")
    private double precio;

        private String fechaInicio;

        private String fechaFin;

        private String fechaTerminacion;

        private Inquilino inquilino;

        private Inmueble inmueble;

    @SerializedName("pagos")
    private List<Pago> pagos;

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaTerminacion() {
        return fechaTerminacion;
    }

    public void setFechaTerminacion(String fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "idContrato=" + idContrato +
                ", precioContrato=" + precio +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", fechaTerminacion='" + fechaTerminacion + '\'' +
                ", inquilino=" + inquilino +
                '}';
    }


    }




