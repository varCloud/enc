package com.example.var.encuestas.Sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rexv666480 on 27/09/2016.
 */
public class EncuestasDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "encuestas.db";
    public SQLiteDatabase db ;

    public EncuestasDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            CrearBD(sqLiteDatabase);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionAnterior, int versionNueva) {
        if (versionAnterior < versionNueva) {
            try {
                DeleteBD(sqLiteDatabase);
                 CrearBD(sqLiteDatabase);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

     public void CrearBD(SQLiteDatabase sqLiteDatabase) throws Exception {
         try{
                      /* */
             sqLiteDatabase.execSQL("CREATE TABLE " + Tables.TBL_ENCUESTAS.TABLE_NAME + " ("
                     + Tables.TBL_ENCUESTAS.id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                     + Tables.TBL_ENCUESTAS.id_encuesta + " INTEGER NOT NULL,"
                     + Tables.TBL_ENCUESTAS.descripcion+ " TEXT NOT NULL,"
                     + Tables.TBL_ENCUESTAS.id_de_sucursal + " INTEGER ,"
                     + Tables.TBL_ENCUESTAS.id_de_region + " INTEGER ,"
                     + Tables.TBL_ENCUESTAS.activa + " INTEGER NOT NULL,"
                     + Tables.TBL_ENCUESTAS.fecha_alta + " TEXT NOT NULL )");

         }catch (Exception ex)
         {
             throw new Exception("al crear tabla TBL_ENCUESTAS",ex);
         }

         try{
             sqLiteDatabase.execSQL("CREATE TABLE " + Tables.TBL_ENCUESTAS_SUCURSALES.TABLE_NAME + " ("
                     + Tables.TBL_ENCUESTAS_SUCURSALES.id_encuesta_sucursal + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                     + Tables.TBL_ENCUESTAS_SUCURSALES.id_encuesta + " INTEGER NOT NULL,"
                     + Tables.TBL_ENCUESTAS_SUCURSALES.numero + " TEXT ,"
                     + Tables.TBL_ENCUESTAS_SUCURSALES.edad + " TEXT ,"
                     + Tables.TBL_ENCUESTAS_SUCURSALES.genero + " TEXT,"
                     + Tables.TBL_ENCUESTAS_SUCURSALES.fecha_alta + " TEXT NOT NULL,"
                     + Tables.TBL_ENCUESTAS_SUCURSALES.cont_numero_socio +" TEXT)");

         }catch (Exception ex)
         {
             throw new Exception("al crear tabla TBL_ENCUESTAS_SUCURSALES",ex);
         }


         try{
                      /* */
             sqLiteDatabase.execSQL("CREATE TABLE " + Tables.TBL_ENCUESTAS_PREGUNTAS.TABLE_NAME + " ("
                     + Tables.TBL_ENCUESTAS_PREGUNTAS.id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                     + Tables.TBL_ENCUESTAS_PREGUNTAS.id_pregunta + " INTEGER NOT NULL,"
                     + Tables.TBL_ENCUESTAS_PREGUNTAS.id_encuesta+ " INTEGER NOT NULL,"
                     + Tables.TBL_ENCUESTAS_PREGUNTAS.idTipoPregunta+ " INTEGER NOT NULL,"
                     + Tables.TBL_ENCUESTAS_PREGUNTAS.descripcion + " TEXT NOT NULL )");

         }catch (Exception ex)
         {
             throw new Exception("al crear tabla TBL_ENCUESTAS_PREGUNTAS",ex);
         }

         try{
                      /* */
             sqLiteDatabase.execSQL("CREATE TABLE " + Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.TABLE_NAME + " ("
                     + Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                     + Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.id_pregunta + " INTEGER NOT NULL,"
                     + Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.id_opcion + " INTEGER  NULL,"
                     + Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.descripcion+ " TEXT NOT NULL)");

         }catch (Exception ex)
         {
             throw new Exception("al crear tabla TBL_ENCUESTAS_OPCIONES_PREGUNTAS",ex);
         }

         try{
                      /* */
             sqLiteDatabase.execSQL("CREATE TABLE " + Tables.TBL_ENCUESTAS_RESPUESTAS.TABLE_NAME + " ("
                     + Tables.TBL_ENCUESTAS_RESPUESTAS.id_respuesta + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                     + Tables.TBL_ENCUESTAS_RESPUESTAS.id_pregunta+ " INTEGER NOT NULL,"
                     + Tables.TBL_ENCUESTAS_RESPUESTAS.id_encuesta_sucursal+ " INTEGER NOT NULL,"
                     + Tables.TBL_ENCUESTAS_RESPUESTAS.respuesta + " TEXT NOT NULL )");

         }catch (Exception ex)
         {
             throw new Exception("al crear tabla TBL_ENCUESTAS_RESPUESTAS",ex);
         }

         try{
                      /* */
             sqLiteDatabase.execSQL("CREATE TABLE " + Tables.TBL_SUCURSALES.TABLE_NAME + " ("
                     + Tables.TBL_SUCURSALES.id_Sucursal + " INTEGER ,"
                     + Tables.TBL_SUCURSALES.id_Region + " INTEGER ,"
                     + Tables.TBL_SUCURSALES.descripcion+ " TEXT NOT NULL)");

         }catch (Exception ex)
         {
             throw new Exception("al crear tabla TBL_SUCURSALES",ex);
         }


         try{
                      /* */
             sqLiteDatabase.execSQL("CREATE TABLE " + Tables.TBL_REGIONES.TABLE_NAME + " ("
                     + Tables.TBL_REGIONES.id_Region + " INTEGER ,"
                     + Tables.TBL_SUCURSALES.descripcion+ " INTEGER NOT NULL)");

         }catch (Exception ex)
         {
             throw new Exception("al crear tabla TBL_SUCURSALES",ex);
         }
         try{
             sqLiteDatabase.execSQL("CREATE TABLE " + Tables.TBL_LOG_ARCHIVOS_RESPUESTAS.TABLE_NAME+ " ("
                     + Tables.TBL_LOG_ARCHIVOS_RESPUESTAS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                     + Tables.TBL_LOG_ARCHIVOS_RESPUESTAS.FECHAALTA + " TEXT NOT NULL,"
                     + Tables.TBL_LOG_ARCHIVOS_RESPUESTAS.RESPUESTAS+ " TEXT NOT NULL,"
                     + Tables.TBL_LOG_ARCHIVOS_RESPUESTAS.NOMBRE + "  TEXT NOT NULL )");

         }catch (Exception ex)
        {
            throw new Exception("al crear tabla TBL_ENCUESTAS",ex);
        }
     }


    public void DeleteBD(SQLiteDatabase sqLiteDatabase) throws Exception {
        try
        {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  " + Tables.TBL_ENCUESTAS.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Tables.TBL_ENCUESTAS_PREGUNTAS.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Tables.TBL_ENCUESTAS_RESPUESTAS.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  " + Tables.TBL_ENCUESTAS_SUCURSALES.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  " + Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  " + Tables.TBL_SUCURSALES.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  " + Tables.TBL_REGIONES.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  " + Tables.TBL_LOG_ARCHIVOS_RESPUESTAS.TABLE_NAME);
        }
        catch (Exception ex)
        {
            throw new Exception("al ELiminar tablas",ex);
        }

    }
}
