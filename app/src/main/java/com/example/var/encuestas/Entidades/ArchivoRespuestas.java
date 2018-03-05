package com.example.var.encuestas.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by rexv666480 on 11/05/2017.
 */
@DatabaseTable(tableName = "TBL_LOG_ARCHIVOS_RESPUESTAS")
public class ArchivoRespuestas {

    @DatabaseField(columnName = "id")
    private int id;

    @DatabaseField(columnName = "nombre")
    private String nombre;
    @DatabaseField(columnName = "respuestas")
    private String respuestas;
    @DatabaseField(columnName = "fechaalta")
    private String fechaCreacion;

    public ArchivoRespuestas() {

    }

    public ArchivoRespuestas(String nombre, String respuestas) {
        this.nombre = nombre;
        this.respuestas = respuestas;
    }

    public ArchivoRespuestas(String nombre, String respuestas, String fechaCreacion) {
        this.nombre = nombre;
        this.respuestas = respuestas;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(String respuestas) {
        this.respuestas = respuestas;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
