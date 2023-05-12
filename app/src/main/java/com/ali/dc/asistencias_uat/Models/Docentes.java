package com.ali.dc.asistencias_uat.Models;

public class Docentes {

    private int id_docente;
    private String num_empleado;
    private String nombre;

    public Docentes(){}

    public Docentes(int id_docente, String num_empleado, String nombre) {
        this.id_docente = id_docente;
        this.num_empleado = num_empleado;
        this.nombre = nombre;
    }

    public int getId_docente() {
        return id_docente;
    }

    public void setId_docente(int id_docente) {
        this.id_docente = id_docente;
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

    @Override
    public String toString() {
        return "Docentes{" +
                "id_docente=" + id_docente +
                ", num_empleado='" + num_empleado + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

}
