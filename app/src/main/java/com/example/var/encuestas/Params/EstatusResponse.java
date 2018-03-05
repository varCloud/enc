package com.example.var.encuestas.Params;

/**
 * Created by rexv666480 on 10/01/2017.
 */
public class EstatusResponse {

    public EstatusResponse(int estatus, String mensaje) {
        this.estatus = estatus;
        this.mensaje = mensaje;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    private int estatus ;
    private String mensaje ;
}
