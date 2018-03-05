package com.example.var.encuestas.Params;

import com.example.var.encuestas.Entidades.Socio;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rexv666480 on 12/10/2016.
 */
public class EnviarRepuestasParams {

    private String  idSucursal;

    @SerializedName("lstSocio")
    private List<Socio> listSocio ;

    public String getIdSucursal() {
        return idSucursal;
    }

    public EnviarRepuestasParams(String idSucursal, List<Socio> listSocio) {
        this.idSucursal = idSucursal;
        this.listSocio = listSocio;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public List<Socio> getListSocio() {
        return listSocio;
    }

    public void setListSocio(List<Socio> listSocio) {
        this.listSocio = listSocio;
    }

    public EnviarRepuestasParams() {
    }
}
