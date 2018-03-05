package com.example.var.encuestas.Entidades;

import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rexv666480 on 28/09/2016.
 */
public class Preguntas {


    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    String id ;

    @DatabaseField(columnName = "id_pregunta")
    String idPregunta ;

    String descTipoPregunta;

    @DatabaseField
    String descPregunta ;

    @DatabaseField
    String idTipoPregunta;

    List<OpcionesPreguntas> opciones ;


    public Preguntas() {
    }

    public Preguntas(String id, String tipoPregunta, String descripcion, String idTipoPregunta, ArrayList<OpcionesPreguntas> opciones) {
        this.id = id;
        this.descTipoPregunta = tipoPregunta;
        this.descPregunta = descripcion;
        this.idTipoPregunta = idTipoPregunta;
        this.opciones = opciones;
    }

    public String getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(String idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoPreguntaDescripcion() {
        return descTipoPregunta;
    }

    public void setTipoPreguntaDescripcion(String tipoPreguntaDescripcion) {
        this.descTipoPregunta = tipoPreguntaDescripcion;
    }

    public String getDescripcion() {
        return descPregunta;
    }

    public void setDescripcion(String descripcion) {
        this.descPregunta = descripcion;
    }

    public List<OpcionesPreguntas> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<OpcionesPreguntas> opciones) {
        this.opciones = opciones;
    }

    public String getIdTipoPregunta() {
        return idTipoPregunta;
    }

    public void setIdTipoPregunta(String idTipoPregunta) {
        this.idTipoPregunta = idTipoPregunta;
    }
}
