package com.example.var.encuestas.Model;


import com.example.var.encuestas.Entidades.Encuesta;

/**
 * Created by rexv666480 on 28/09/2016.
 */
public class EncuestaResponse {

    private String estatus;
    private String usuario;
    private String pass;
    private Encuesta encuesta ;



    private String msj;

    public EncuestaResponse(String estatus, Encuesta encuesta) {
        this.estatus = estatus;
        this.encuesta = encuesta;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }
}
