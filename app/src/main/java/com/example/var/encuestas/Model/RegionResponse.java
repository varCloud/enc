package com.example.var.encuestas.Model;

import com.example.var.encuestas.Entidades.Region;

import java.util.List;

/**
 * Created by rexv666480 on 29/09/2016.
 */
public class RegionResponse {

    private String estatus;
    private List<Region> listRegiones;

    public RegionResponse(String estatus, List<Region> listRegiones) {
        this.estatus = estatus;
        this.listRegiones = listRegiones;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public List<Region> getListRegiones() {
        return listRegiones;
    }

    public void setListRegiones(List<Region> listRegiones) {
        this.listRegiones = listRegiones;
    }
}
