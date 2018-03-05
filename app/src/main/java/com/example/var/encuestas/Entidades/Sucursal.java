package com.example.var.encuestas.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by rexv666480 on 29/09/2016.
 */
@DatabaseTable(tableName = "TBL_SUCURSALES")
public class Sucursal {

    @DatabaseField(columnName = "id_Sucursal")
    private String idSucursal;

    @DatabaseField
    private String descripcion;

    @DatabaseField(columnName = "id_Region")
    private String idRegion;

    public Sucursal(String idRegion, String descripcion, String idSucursal) {
        this.idRegion = idRegion;
        this.descripcion = descripcion;
        this.idSucursal = idSucursal;
    }

    public Sucursal() {
    }

    public String getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(String idRegion) {
        this.idRegion = idRegion;
    }

    public Sucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
