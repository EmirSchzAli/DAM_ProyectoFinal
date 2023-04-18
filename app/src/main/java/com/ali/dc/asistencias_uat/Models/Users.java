package com.ali.dc.asistencias_uat.Models;

public class Users {

    private long matricula;
    private String nombre;
    private String correoElectronico;
    private String tipoUsuario;

    public Users() {
    }

    public Users(long matricula, String nombre, String correoElectronico, String tipoUsuario) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.tipoUsuario = tipoUsuario;
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
