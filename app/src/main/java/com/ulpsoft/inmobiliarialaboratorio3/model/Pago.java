package com.ulpsoft.inmobiliarialaboratorio3.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Pago implements Serializable {

    @SerializedName("idPago")
    private int IdPago;

    @SerializedName("nroPago")
    private int NroPago;

    @SerializedName("fecha")
    private String Fecha;

    @SerializedName("importe")
    private Double Importe;

    @SerializedName("idContrato")
    private int IdContrato;

    private Contrato contrato;

    // Getters y setters

    public int getIdPago() {
        return IdPago;
    }

    public void setIdPago(int idPago) {
        IdPago = idPago;
    }

    public int getNroPago() {
        return NroPago;
    }

    public void setNroPago(int nroPago) {
        NroPago = nroPago;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public Double getImporte() {
        return Importe;
    }

    public void setImporte(Double importe) {
        Importe = importe;
    }

    public int getIdContrato() {
        return IdContrato;
    }

    public void setIdContrato(int idContrato) {
        IdContrato = idContrato;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
