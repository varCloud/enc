package com.example.var.encuestas.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rexv666480 on 28/09/2016.
 */
@DatabaseTable(tableName = "TBL_ENCUESTAS")
public class Encuesta {

    @DatabaseField(generatedId = true)
    String id;

    @DatabaseField(columnName = "id_encuesta")
    String id_encuesta;

    @DatabaseField
    String descripcion;

    @DatabaseField(columnName = "fecha_alta")
    String fechaAlta;

    /*
    ForeignCollectionField(columnName = "preguntas", eager = true)
    private ForeignCollection<Preguntas> preguntas;
    // NO SE PUEDE UTILIZAR  EL ORM Y RETROFIT YA QUE PARA LIGAR DE UNO A MUCHOS
     PIDE QUE CAMBIE EL TIPO  DE DATO  Y POR LO TANTO YA NO LO RECONOCE
     EL PARSING DE RETROFIR NECESITAMOS BUSCAR OTRO
    */

    private List<Preguntas> preguntas;

    @DatabaseField (columnName = "id_de_sucursal")
    String idSucursal;

    @DatabaseField (columnName = "id_de_region")
    String idRegion;

    @DatabaseField
    String activa;

    String area;

    public Encuesta() {
    }

    public Encuesta(String id, String descripcion, String fechaAlta, ArrayList<Preguntas> preguntas) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        //this.preguntas = preguntas;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
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

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public List<Preguntas> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Preguntas>  preguntas) {
        this.preguntas = preguntas;
    }

    public String getIdEncuesta() {
        return id_encuesta;
    }

    public void setIdEncuesta(String idEncuesta) {
        this.id_encuesta = idEncuesta;
    }

    public String getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(String idRegion) {
        this.idRegion = idRegion;
    }

    public String getActiva() {
        return activa;
    }

    public void setActiva(String activa) {
        this.activa = activa;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
