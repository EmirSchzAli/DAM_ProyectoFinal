package com.ali.dc.asistencias_uat.Models;

public class Salones {
    private int id_salon;
    private String nombre;

    public Salones(){}

    public Salones(int id_salon, String nombre) {
        this.id_salon = id_salon;
        this.nombre = nombre;
    }

    public int getId_salon() {
        return id_salon;
    }

    public void setId_salon(int id_salon) {
        this.id_salon = id_salon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Salones{" +
                "id_salon=" + id_salon +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
