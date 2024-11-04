package com.ulpsoft.inmobiliarialaboratorio3.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Inmueble implements Serializable {

    private int idInmueble; // Auto increment

    private String direccion; // Required

    private int ambientes; // Required

    private String tipo; // Required

    private String uso; // Required

    private double precio; // Required

    private boolean disponible = false; // Default value false

    private String avatar; // Cambié a String para coincidir con el modelo de .NET

    private int idPropietario; // Foreign Key to Propietario

    private Propietario propietario; // Navigation Property

    @SerializedName("contratos")
    private List<Contrato> contratos; // Lista de contratos no agregar en el constructor es para algunos endpoints

    @Override
    public String toString() {
        return "Inmueble{" +
                "idInmueble=" + idInmueble +
                ", direccion='" + direccion + '\'' +
                ", ambientes=" + ambientes +
                ", tipo='" + tipo + '\'' +
                ", uso='" + uso + '\'' +
                ", precio=" + precio +
                ", disponible=" + disponible +
                ", avatar='" + avatar + '\'' +
                ", idPropietario=" + idPropietario +
                ", propietario=" + propietario +
                ", contratos=" + contratos +
                '}';
    }

    public Inmueble(){

}

    public Inmueble(int idInmueble, String direccion, int ambientes, String tipo, String uso, double precio, boolean disponible, String avatar, int idPropietario, Propietario propietario) {
        this.idInmueble = idInmueble;
        this.direccion = direccion;
        this.ambientes = ambientes;
        this.tipo = tipo;
        this.uso = uso;
        this.precio = precio;
        this.disponible = disponible;
        this.avatar = avatar;
        this.idPropietario = idPropietario;
        this.propietario = propietario;
    }



    // Getters and Setters
    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() { // Cambié a isDisponible para seguir la convención
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getAvatar() { // Cambié el tipo de avatar a String
        return avatar;
    }

    public void setAvatar(String avatar) { // Cambié el tipo de avatar a String
        this.avatar = avatar;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }
}
