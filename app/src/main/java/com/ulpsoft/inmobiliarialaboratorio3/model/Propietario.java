package com.ulpsoft.inmobiliarialaboratorio3.model;

public class Propietario {
    private int idpropietario;
    private String dni;
    private String apellido;
    private String nombre;
    private String telefono;
    private boolean estado;
    private String email;
    private String clave;
    private String avatar;

    public Propietario(){

    }

    public Propietario(int idpropietario, String dni, String apellido, String nombre, String telefono, boolean estado, String email, String clave, String avatar) {
        this.idpropietario = idpropietario;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.telefono = telefono;
        this.estado = estado;
        this.email = email;
        this.clave = clave;
        this.avatar = avatar;
    }

    public int getIdpropietario() {
        return idpropietario;
    }

    public void setIdpropietario(int idpropietario) {
        this.idpropietario = idpropietario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Propietario{" +
                "id=" + idpropietario +
                ", dni='" + dni + '\'' +
                ", apellido='" + apellido + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", estado=" + estado +
                ", email='" + email + '\'' +
                ", clave='" + clave + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}

/*  Modelo en .NET :

public class Propietario
{
    public int Id { get; set; }
    public string Dni { get; set; } = "";
    public string Apellido { get; set; } = "";
    public string Nombre { get; set; } = "";
    public string? Telefono { get; set; }
    public bool Estado { get; set; }
    public string? Email { get; set; } = "";
    public string? Clave { get; set; } = "";
    public string? Avatar { get; set; }

    [NotMapped]
    public IFormFile? AvatarFile { get; set; }  // El archivo es opcional
}
*/
