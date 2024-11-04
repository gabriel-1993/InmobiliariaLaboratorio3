package com.ulpsoft.inmobiliarialaboratorio3.model;

import java.io.Serializable;

public class Inquilino implements Serializable {


        private int idInquilino;


        private int dni;


        private String apellido;


        private String nombre;


        private String telefono;


        private String nombreGarante;


        private String apellidoGarante;

        private String telefonoGarante;

    public Inquilino(int idInquilino, int dni, String apellido, String nombre, String telefono, String nombreGarante, String apellidoGarante, String telefonoGarante) {
        this.idInquilino = idInquilino;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.telefono = telefono;
        this.nombreGarante = nombreGarante;
        this.apellidoGarante = apellidoGarante;
        this.telefonoGarante = telefonoGarante;
    }


    // Getters y setters


    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreGarante() {
        return nombreGarante;
    }

    public void setNombreGarante(String nombreGarante) {
        this.nombreGarante = nombreGarante;
    }

    public String getApellidoGarante() {
        return apellidoGarante;
    }

    public void setApellidoGarante(String apellidoGarante) {
        this.apellidoGarante = apellidoGarante;
    }

    public String getTelefonoGarante() {
        return telefonoGarante;
    }

    public void setTelefonoGarante(String telefonoGarante) {
        this.telefonoGarante = telefonoGarante;
    }
}



