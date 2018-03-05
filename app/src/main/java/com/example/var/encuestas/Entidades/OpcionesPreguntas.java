package com.example.var.encuestas.Entidades;

/**
 * Created by rexv666480 on 28/09/2016.
 */
public class OpcionesPreguntas {
    String id;
    String descripcion;
    String seleccionada;
    String idOpcion;


    public String getSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(String seleccionada) {
        this.seleccionada = seleccionada;
    }

    public OpcionesPreguntas(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public OpcionesPreguntas() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(String idOpcion) {
        this.idOpcion = idOpcion;
    }
}
