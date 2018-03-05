package com.example.var.encuestas.Sql;

import android.provider.BaseColumns;

/**
 * Created by rexv666480 on 27/09/2016.
 */
public class Tables {

    public static abstract class TBL_ENCUESTAS implements BaseColumns {
        public static final String TABLE_NAME ="TBL_ENCUESTAS";
        public static final String id = "id";
        public static final String id_encuesta = "id_encuesta";
        public static final String descripcion = "descripcion";
        public static final String activa = "activa";
        public static final String fecha_alta = "fecha_alta";
        public static final String id_de_region = "id_de_region";
        public static final String id_de_sucursal = "id_de_sucursal";
        public static final String id_de_area = "id_de_area";// 1 escritorio .... 2 cajas
    }

    public static abstract class TBL_ENCUESTAS_SUCURSALES implements BaseColumns {
        public static final String TABLE_NAME ="TBL_ENCUESTAS_SUCURSALES";
        public static final String id_encuesta_sucursal = "id_encuesta_sucursal";
        public static final String id_encuesta = "id_encuesta";
        public static final String fecha_alta = "fecha_alta";
        public static final String numero = "numero";
        public static final String edad = "edad";
        public static final String genero = "genero";
        public static final String cont_numero_socio = "cont_numero_socio";
    }

    public static abstract class TBL_ENCUESTAS_PREGUNTAS implements BaseColumns {
        public static final String TABLE_NAME ="TBL_ENCUESTAS_PREGUNTAS";
        public static final String id = "id";
        public static final String id_pregunta = "id_pregunta";
        public static final String id_encuesta = "id_encuesta";
        public static final String descripcion = "descripcion";
        public static final String idTipoPregunta = "idTipoPregunta";

    }

    public static abstract class TBL_ENCUESTAS_RESPUESTAS implements BaseColumns {
        public static final String TABLE_NAME ="TBL_ENCUESTAS_RESPUESTAS";
        public static final String id_respuesta = "id_respuesta";
        public static final String id_pregunta = "id_pregunta";
        public static final String respuesta = "respuesta";
        public static final String id_encuesta_sucursal = "id_encuesta_sucursal";
    }


    public static abstract class TBL_ENCUESTAS_OPCIONES_PREGUNTAS implements BaseColumns {
        public static final String TABLE_NAME ="TBL_ENCUESTAS_OPCIONES_PREGUNTAS";
        public static final String id = "id";
        public static final String id_pregunta = "id_pregunta";
        public static final String id_opcion = "id_opcion";
        public static final String descripcion = "descripcion";
    }

    public static abstract class TBL_SUCURSALES implements BaseColumns {
        public static final String TABLE_NAME ="TBL_SUCURSALES";
        public static final String id_Sucursal = "id_Sucursal";
        public static final String descripcion = "descripcion";
        public static final String id_Region = "id_Region";
    }

    public static abstract class TBL_REGIONES implements BaseColumns {
        public static final String TABLE_NAME ="TBL_REGIONES";
        public static final String id_Region = "id_Region";
        public static final String descripcion = "descripcion";
    }

    public static abstract class TBL_LOG_ARCHIVOS_RESPUESTAS implements BaseColumns {
        public static final String TABLE_NAME ="TBL_LOG_ARCHIVOS_RESPUESTAS";
        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String FECHAALTA = "fechaalta";
        public static final String RESPUESTAS = "respuestas";
    }
}
