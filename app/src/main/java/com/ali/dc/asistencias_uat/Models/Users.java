package com.ali.dc.asistencias_uat.Models;

public class Users {

    private long matricula;
    private String nombre;
    private String mail;
    private String password;
    private String tipoUsuario;

    public Users() {
    }

    public Users(long matricula, String nombre, String mail, String password, String tipoUsuario) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.mail = mail;
        this.password = password;
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

    public String getMail() {
        return mail;
    }

    public void setMailail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "Users{" +
                "matricula = " + matricula +
                ", nombre = '" + nombre + '\'' +
                ", mail = '" + mail + '\'' +
                ", password = '" + password + '\'' +
                ", tipoUsuario = '" + tipoUsuario + '\'' +
                '}';
    }
}
