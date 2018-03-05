package com.example.var.encuestas.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by rexv666480 on 29/09/2016.
 */
@DatabaseTable(tableName = "TBL_Regiones")
public class Region {

    @DatabaseField(columnName = "id_Region")
     String id ;
    @DatabaseField
     String descripcion ;

    public Region() {
    }

    public Region(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
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

    @Override
    public String toString() {
        return descripcion;
    }
}
