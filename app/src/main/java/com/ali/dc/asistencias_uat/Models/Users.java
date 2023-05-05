package com.ali.dc.asistencias_uat.Models;

public class Users {

    private int id;
    private String nombre;
    private String mail;
    private String password;
    private int tipoUsuario;

    public Users() {
    }

    public Users(int id, String nombre, String mail, String password, int tipoUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.mail = mail;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id + "," +
                "nombre:'" + nombre + "'," +
                "mail:'" + mail + "'," +
                "password:'" + password + "'," +
                "tipoUsuario:" + tipoUsuario +
                "}";
    }
}
