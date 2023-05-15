package com.ali.dc.asistencias_uat.Models;

import com.google.type.DateTime;

import java.time.LocalDateTime;

public class Registros {

    private int id_registro;
    private String nombre_registrado;
    private String miembro;
    private String salon;
    private String fecha_hora;
    private int id_admin;

    public Registros() {}

    public Registros(int id_registro, String nombre_registrado, String miembro, String salon, String fecha_hora, int id_admin) {
        this.id_registro = id_registro;
        this.nombre_registrado = nombre_registrado;
        this.miembro = miembro;
        this.salon = salon;
        this.fecha_hora = fecha_hora;
        this.id_admin = id_admin;
    }

    public int getId_registro() {
        return id_registro;
    }

    public void setId_registro(int id_registro) {
        this.id_registro = id_registro;
    }

    public String getNombre_registrado() {
        return nombre_registrado;
    }

    public void setNombre_registrado(String nombre_registrado) {
        this.nombre_registrado = nombre_registrado;
    }

    public String getMiembro() {
        return miembro;
    }

    public void setMiembro(String miembro) {
        this.miembro = miembro;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    @Override
    public String toString() {
        return "Registros{" +
                "id_registro=" + id_registro +
                ", nombre_registrado='" + nombre_registrado + '\'' +
                ", miembro='" + miembro + '\'' +
                ", salon='" + salon + '\'' +
                ", fecha_hora='" + fecha_hora + '\'' +
                ", id_admin=" + id_admin +
                '}';
    }
}
