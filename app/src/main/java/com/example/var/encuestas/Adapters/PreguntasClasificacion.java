package com.example.var.encuestas.Adapters;


import android.support.v4.app.Fragment;

/**
 * Created by rexv666480 on 07/10/2016.
 */
public class PreguntasClasificacion {


    private String idTipoPregunta;
    private Fragment fragment;

    public PreguntasClasificacion(String idTipoPregunta, Fragment fragment) {
        this.idTipoPregunta = idTipoPregunta;
        this.fragment = fragment;
    }

    public String getIdTipoPregunta() {
        return idTipoPregunta;
    }

    public void setIdTipoPregunta(String idTipoPregunta) {
        this.idTipoPregunta = idTipoPregunta;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
