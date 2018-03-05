package com.example.var.encuestas.Model;

import com.example.var.encuestas.Entidades.Sucursal;

import java.util.List;

/**
 * Created by rexv666480 on 29/09/2016.
 */
public class SucursalResponse {

    private String estatus;
    private List<Sucursal> listSucursales;

    public SucursalResponse(String estatus, List<Sucursal> listSucursales) {
        this.estatus = estatus;
        this.listSucursales = listSucursales;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public List<Sucursal> getListSucursales() {
        return listSucursales;
    }

    public void setListSucursales(List<Sucursal> listSucursales) {
        this.listSucursales = listSucursales;
    }
}
