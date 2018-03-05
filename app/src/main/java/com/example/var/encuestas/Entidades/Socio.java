package com.example.var.encuestas.Entidades;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rexv666480 on 05/10/2016.
 */
public class Socio {

    private String nombre;

    private String edad;

    private String genero;

    private String contNumeroSocio;

    @SerializedName("lstRespuesta")
    private List<Respuesta> listR;

    private String idEncuesta;

    public Socio(String nombre, String fecha_nacimiento, String genero) {
        this.nombre = nombre;
        this.edad = fecha_nacimiento;
        this.genero = genero;
    }

    public String getContNumeroSocio() {
        return contNumeroSocio;
    }

    public void setContNumeroSocio(String contNumeroSocio) {
        this.contNumeroSocio = contNumeroSocio;
    }

    public Socio() {
    }


    public String getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(String idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public List<Respuesta> getLisrR() {
        return listR;
    }

    public void setLisrR(List<Respuesta> lisrR) {
        this.listR = lisrR;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFecha_nacimiento() {
        return edad;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.edad = fecha_nacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
