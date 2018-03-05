package com.example.var.encuestas.Entidades;

/**
 * Created by rexv666480 on 27/09/2016.
 */
public class Respuesta {

    private String idRespuesta;
    private String idPregunta;
    private String respuesta;
    private String idOpcion;


    public Respuesta( String idPregunta, String respuesta) {
        this.idPregunta = idPregunta;
        this.respuesta = respuesta;
    }

    public Respuesta() {
    }

    public String getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(String idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(String idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(String idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
