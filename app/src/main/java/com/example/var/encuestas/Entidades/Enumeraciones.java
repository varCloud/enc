package com.example.var.encuestas.Entidades;

import android.provider.BaseColumns;

/**
 * Created by rexv666480 on 03/05/2017.
 */
public class Enumeraciones {

    public static abstract class TIPOS_PREGUNTAS implements BaseColumns {

        public static final String ABIERTA ="1";
        public static final String OPCION ="2";
        public static final String VALORACION ="3";

    }
}
