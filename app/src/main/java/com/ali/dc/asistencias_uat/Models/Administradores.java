package com.ali.dc.asistencias_uat.Models;

import androidx.annotation.NonNull;

public class Administradores {

    private int id_admin;
    private String fb_id;
    private String num_empleado;
    private String nombre;
    private String correo;
    private int id_tipoAdmin;
    private String tipoAdmin;


    public Administradores(int id_admin, String fb_id, String num_empleado, String nombre, String correo, int id_tipoAdmin, String tipoEmpleado) {
        this.id_admin = id_admin;
        this.fb_id = fb_id;
        this.num_empleado = num_empleado;
        this.nombre = nombre;
        this.correo = correo;
        this.id_tipoAdmin = id_tipoAdmin;
        this.tipoAdmin = tipoEmpleado;
    }

    public Administradores(){}

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getNum_empleado() {
        return num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        this.num_empleado = num_empleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getId_tipoAdmin() {
        return id_tipoAdmin;
    }

    public void setId_tipoAdmin(int id_tipoAdmin) {
        this.id_tipoAdmin = id_tipoAdmin;
    }

    public String getTipoAdmin() {
        return tipoAdmin;
    }

    public void setTipoAdmin(String tipoAdmin) {
        this.tipoAdmin = tipoAdmin;
    }

    @NonNull
    @Override
    public String toString() {
        return "Administradores{" +
                "fb_id='" + fb_id + '\'' +
                ", num_empleado='" + num_empleado + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", id_tipoAdmin=" + id_tipoAdmin +
                '}';
    }
}
