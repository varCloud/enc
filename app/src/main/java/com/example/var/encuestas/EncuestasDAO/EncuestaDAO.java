package com.example.var.encuestas.EncuestasDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.example.var.encuestas.Entidades.ArchivoRespuestas;
import com.example.var.encuestas.Entidades.Encuesta;
import com.example.var.encuestas.Entidades.OpcionesPreguntas;
import com.example.var.encuestas.Entidades.Preguntas;
import com.example.var.encuestas.Entidades.Region;
import com.example.var.encuestas.Entidades.Respuesta;
import com.example.var.encuestas.Entidades.Socio;
import com.example.var.encuestas.Entidades.Sucursal;
import com.example.var.encuestas.Sql.EncuestasDbHelper;
import com.example.var.encuestas.Sql.OrmHelper;
import com.example.var.encuestas.Sql.Tables;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rexv666480 on 28/09/2016.
 */
public class EncuestaDAO {

    private EncuestasDbHelper dbManger = null;
    OrmHelper helper;
    private Context context;

    public EncuestaDAO(Context context) {
        try {
            this.context = context;
            helper = OpenHelperManager.getHelper(context, OrmHelper.class);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Boolean InsertarEncuesta(Encuesta enc) throws Exception {
        try {
            dbManger = new EncuestasDbHelper(context);
            if (enc != null) {
                dbManger.db.execSQL("UPDATE " + Tables.TBL_ENCUESTAS.TABLE_NAME + " SET " + Tables.TBL_ENCUESTAS.activa + "=0");
                Cursor c = dbManger.db.rawQuery("SELECT * FROM " + Tables.TBL_ENCUESTAS.TABLE_NAME, null);
                // if(c.getCount() <= 0) {

                ContentValues values = new ContentValues();
                // Pares clave-valor
                values.put(Tables.TBL_ENCUESTAS.id_encuesta, enc.getIdEncuesta());
                values.put(Tables.TBL_ENCUESTAS.descripcion, enc.getDescripcion());
                values.put(Tables.TBL_ENCUESTAS.fecha_alta, enc.getFechaAlta());
                values.put(Tables.TBL_ENCUESTAS.id_de_sucursal, 0);
                values.put(Tables.TBL_ENCUESTAS.id_de_region, 0);
                values.put(Tables.TBL_ENCUESTAS.activa, 1);
                // Insertar..
                dbManger.db.insert(Tables.TBL_ENCUESTAS.TABLE_NAME, null, values);
                EncuestasDbHelper dbMangerOpc = new EncuestasDbHelper(context);
                if (enc.getPreguntas().size() > 0) {
                    //final ForeignCollection<Preguntas> lstPreguntas = enc.getPreguntas();
                    for (Preguntas p : enc.getPreguntas()) {

                        ContentValues valuesPreguntas = new ContentValues();
                        // Pares clave-valor
                        valuesPreguntas.put(Tables.TBL_ENCUESTAS_PREGUNTAS.id_pregunta, p.getIdPregunta());
                        valuesPreguntas.put(Tables.TBL_ENCUESTAS_PREGUNTAS.descripcion, p.getDescripcion());
                        valuesPreguntas.put(Tables.TBL_ENCUESTAS_PREGUNTAS.id_encuesta, enc.getIdEncuesta());
                        valuesPreguntas.put(Tables.TBL_ENCUESTAS_PREGUNTAS.idTipoPregunta, p.getIdTipoPregunta());
                        // Insertar..
                        dbManger.db.insert(Tables.TBL_ENCUESTAS_PREGUNTAS.TABLE_NAME, null, valuesPreguntas);
                        if (Integer.parseInt(p.getIdTipoPregunta()) == 2)// es pregunta de opcion insertamos las opciones
                        {
                            for (OpcionesPreguntas opc : p.getOpciones()) {
                                ContentValues valuesOpcPreguntas = new ContentValues();
                                valuesOpcPreguntas.put(Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.id_pregunta, p.getIdPregunta());
                                valuesOpcPreguntas.put(Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.id_opcion, opc.getIdOpcion());
                                valuesOpcPreguntas.put(Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.descripcion, opc.getDescripcion());
                                long newRowId  =dbMangerOpc.db.insert(Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.TABLE_NAME, null, valuesOpcPreguntas);
                                if(newRowId ==-1) {
                                     throw new Exception("fallo al insertar la opciondes de las pregunta");
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            throw ex;
        }
        return true;
    }

    public boolean ExisteEncuestaPorConfigurar() {
        boolean bandera = false;
        dbManger = new EncuestasDbHelper(context);
        String idEncuestaAcutal = ObtenerIdEncuestaActual();
        if (idEncuestaAcutal.equals("")) {
                bandera =  true;
            }
         else
            bandera = false;
        return bandera;
    }

    public boolean ExisteEncuestaConfigurada() {
        boolean bandera = false;
        dbManger = new EncuestasDbHelper(context);

        String query = "SELECT * FROM " + Tables.TBL_ENCUESTAS.TABLE_NAME + " WHERE activa = 1";
        Cursor cursor = dbManger.db.rawQuery(query, null);
        if (cursor.moveToFirst())
            bandera = true;
        else
            bandera = false;

        return bandera;
    }

    public Encuesta ObetenerEncuestaCompleta() {
        Encuesta enc = ObtenerEncuesta();
        enc.setPreguntas(ObtenerPreguntas(enc.getIdEncuesta()));
        return enc;
    }

    public Encuesta ObtenerEncuesta() {
        Encuesta enc = new Encuesta();
        try {
            dbManger = new EncuestasDbHelper(context);
            String maxContador = "SELECT max(" + Tables.TBL_ENCUESTAS.id_encuesta + ") FROM " + Tables.TBL_ENCUESTAS.TABLE_NAME + " WHERE ACTIVA =1";
            Cursor c = dbManger.db.rawQuery(maxContador, null);
            if (c.moveToFirst()) {
                String campos = " " + Tables.TBL_ENCUESTAS.id_encuesta + "," + Tables.TBL_ENCUESTAS.descripcion + "," +
                        Tables.TBL_ENCUESTAS.id_de_sucursal;
                String query = "SELECT " + campos + " FROM " + Tables.TBL_ENCUESTAS.TABLE_NAME + " WHERE " + Tables.TBL_ENCUESTAS.id_encuesta + "=" + c.getString(0);
                Cursor cursor = dbManger.db.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    enc.setDescripcion(cursor.getString(1));
                    enc.setId(cursor.getString(0));
                    enc.setIdEncuesta(cursor.getString(0));
                    enc.setIdSucursal(cursor.getString(2));
                }

            } else {
                enc = null;
            }

        } catch (Exception ex) {
            throw ex;
        }

        return enc;
    }

    public List<Preguntas> ObtenerPreguntas(String idEncuesta) {
        List<Preguntas> lstPreguntas = new ArrayList<Preguntas>();
        try {
            dbManger = new EncuestasDbHelper(context);

            String campos = " " + Tables.TBL_ENCUESTAS_PREGUNTAS.id_pregunta + "," + Tables.TBL_ENCUESTAS_PREGUNTAS.idTipoPregunta + "," +
                    Tables.TBL_ENCUESTAS_PREGUNTAS.descripcion + " ";

            String query = "SELECT " + campos + " FROM " + Tables.TBL_ENCUESTAS_PREGUNTAS.TABLE_NAME + " WHERE " + Tables.TBL_ENCUESTAS_PREGUNTAS.id_encuesta + "=" + idEncuesta;
            Cursor c = dbManger.db.rawQuery(query, null);
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {

                    Preguntas p = new Preguntas();
                    p.setIdPregunta(c.getString(0));
                    p.setIdTipoPregunta(c.getString(1));
                    p.setDescripcion(c.getString(2));
                    if (Integer.parseInt(p.getIdTipoPregunta()) == 2) // si la pregunta es de opcion
                    {
                        p.setOpciones(ObtenerOpcionesPreguntas(p.getIdPregunta()));
                    }
                    lstPreguntas.add(p);

                } while (c.moveToNext());
            }


        } catch (Exception ex) {
            throw ex;
        }
        return lstPreguntas;
    }

    public List<OpcionesPreguntas> ObtenerOpcionesPreguntas(String idPregunta) {
        List<OpcionesPreguntas> lstopc = new ArrayList<OpcionesPreguntas>();
        try {
            dbManger = new EncuestasDbHelper(context);

            String campos = " " + Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.id_pregunta + "," + Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.id + "," +
                    Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.descripcion + " , "+Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.id_opcion+" ";

            String query = "SELECT " + campos + " FROM " + Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.TABLE_NAME + " WHERE " + Tables.TBL_ENCUESTAS_OPCIONES_PREGUNTAS.id_pregunta + "=" + idPregunta;
            Cursor c = dbManger.db.rawQuery(query, null);
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {

                    OpcionesPreguntas op = new OpcionesPreguntas();
                    op.setId(c.getString(1));
                    op.setDescripcion(c.getString(2));
                    op.setIdOpcion(c.getString(3));
                    op.setSeleccionada("F");
                    lstopc.add(op);

                } while (c.moveToNext());
            }
        } catch (Exception ex) {
            throw ex;
        }

        return lstopc;
    }

    public List<Region> ObtenerRegiones() throws SQLException {

        dbManger = new EncuestasDbHelper(context);
        List<Region> regiones = new ArrayList<Region>();
        try {

            Dao dao = helper.getRegionDao();
            regiones = dao.queryForAll();

//            String query = "SELECT * FROM "+Tables.TBL_REGIONES.TABLE_NAME ;
//            Cursor c = dbManger.db.rawQuery(query,null);
//            if (c.moveToFirst()) {
//                //Recorremos el cursor hasta que no haya más registros
//                do {
//                    Region r = new Region();
//                    r.setId(c.getString(0));
//                    r.setDescripcion(c.getString(1));
//                    regiones.add(r);
//
//                } while(c.moveToNext());
//            }

        } catch (Exception ex) {
            try {
                throw ex;
            } catch (android.database.SQLException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return regiones;
    }

    public List<Sucursal> ObtenerScursales(String idRegion) throws SQLException {
        List<Sucursal> lstSucu = null;

        try {
            Dao dao;

            dao = helper.getSucursalDao();
            QueryBuilder queryBuilder = dao.queryBuilder();
            queryBuilder.setWhere(queryBuilder.where().eq("id_Region", idRegion));
            lstSucu = dao.query(queryBuilder.prepare());


        } catch (Exception ex) {
            try {
                throw ex;
            } catch (android.database.SQLException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lstSucu;
    }

    public String ObtenerIdEncuestaActual() {
        String id = "";
        dbManger = new EncuestasDbHelper(context);
        try {
            String maxContador = "SELECT max(" + Tables.TBL_ENCUESTAS.id_encuesta + ") FROM " + Tables.TBL_ENCUESTAS.TABLE_NAME + " WHERE ACTIVA =1";
            Cursor c = dbManger.db.rawQuery(maxContador, null);
            if (c.moveToFirst()) {
                String campos = " " + Tables.TBL_ENCUESTAS.id_encuesta + "," + Tables.TBL_ENCUESTAS.descripcion + "," +
                        Tables.TBL_ENCUESTAS.id_de_sucursal;
                String query = "SELECT " + campos + " FROM " + Tables.TBL_ENCUESTAS.TABLE_NAME + " WHERE " + Tables.TBL_ENCUESTAS.id_encuesta + "=" + c.getString(0);
                Cursor cursor = dbManger.db.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    id = cursor.getString(0);
                }

            }
        } catch (Exception ex) {
            throw ex;
        }
        return id;
    }

    public boolean GuardarConfiguracion(Encuesta enc) {
        try {
            dbManger = new EncuestasDbHelper(context);
            ContentValues valores = new ContentValues();
            valores.put(Tables.TBL_ENCUESTAS.id_de_region, enc.getIdRegion());
            valores.put(Tables.TBL_ENCUESTAS.id_de_sucursal, enc.getIdSucursal());
            valores.put(Tables.TBL_ENCUESTAS.id_de_region, enc.getArea());
            dbManger.db.update(Tables.TBL_ENCUESTAS.TABLE_NAME, valores, Tables.TBL_ENCUESTAS.id_encuesta + "=" + enc.getIdEncuesta(), null);

        } catch (Exception ex) {
            throw ex;
        }
        return true;
    }

    public String ObtenerSucursalActual() {
        try {
            String idSucursal = "";
            dbManger = new EncuestasDbHelper(context);
            try {
                String maxContador = "SELECT max(" + Tables.TBL_ENCUESTAS.id_encuesta + ") FROM " + Tables.TBL_ENCUESTAS.TABLE_NAME + " WHERE ACTIVA =1";
                Cursor c = dbManger.db.rawQuery(maxContador, null);
                if (c.moveToFirst()) {
                    String campos = " " + Tables.TBL_ENCUESTAS.id_encuesta + "," + Tables.TBL_ENCUESTAS.descripcion + "," +
                            Tables.TBL_ENCUESTAS.id_de_sucursal;
                    String query = "SELECT " + campos + " FROM " + Tables.TBL_ENCUESTAS.TABLE_NAME + " WHERE " + Tables.TBL_ENCUESTAS.id_encuesta + "=" + c.getString(0);
                    Cursor cursor = dbManger.db.rawQuery(query, null);
                    if (cursor.moveToFirst()) {
                        idSucursal = cursor.getString(2);
                    }

                }
            } catch (Exception ex) {
                throw ex;
            }
            return idSucursal;

        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean GuardarRespuesta(Socio s) {
        try {

            dbManger = new EncuestasDbHelper(context);
            ContentValues valores = new ContentValues();
            valores.put(Tables.TBL_ENCUESTAS_SUCURSALES.id_encuesta, s.getIdEncuesta());
            valores.put(Tables.TBL_ENCUESTAS_SUCURSALES.numero, s.getNombre());
            valores.put(Tables.TBL_ENCUESTAS_SUCURSALES.edad, s.getFecha_nacimiento());
            valores.put(Tables.TBL_ENCUESTAS_SUCURSALES.genero, s.getGenero());
            valores.put(Tables.TBL_ENCUESTAS_SUCURSALES.fecha_alta, String.valueOf(new Date()));
            valores.put(Tables.TBL_ENCUESTAS_SUCURSALES.cont_numero_socio, s.getContNumeroSocio());
            dbManger.db.execSQL("INSERT INTO " + Tables.TBL_ENCUESTAS_SUCURSALES.TABLE_NAME + " (id_encuesta,numero,edad,genero,fecha_alta,cont_numero_socio)" +
            "VALUES (" + s.getIdEncuesta() + "," + s.getNombre() + ",'" + s.getFecha_nacimiento() + "','" + s.getGenero() + "','" + String.valueOf(new Date()) + "','" + s.getContNumeroSocio() + "')");
            //dbManger.db.insert(Tables.TBL_ENCUESTAS_SUCURSALES.TABLE_NAME, null, valores);

            String maxContador = "SELECT max(" + Tables.TBL_ENCUESTAS_SUCURSALES.id_encuesta_sucursal + ") FROM " + Tables.TBL_ENCUESTAS_SUCURSALES.TABLE_NAME;
            Cursor c = dbManger.db.rawQuery(maxContador, null);
            if (c.moveToFirst()) {
                for (Respuesta opc : s.getLisrR()) {
                    ContentValues valuesResp = new ContentValues();
                    valuesResp.put(Tables.TBL_ENCUESTAS_RESPUESTAS.id_encuesta_sucursal, c.getString(0));
                    valuesResp.put(Tables.TBL_ENCUESTAS_RESPUESTAS.respuesta, opc.getRespuesta());
                    valuesResp.put(Tables.TBL_ENCUESTAS_RESPUESTAS.id_pregunta, opc.getIdPregunta());
                    dbManger.db.execSQL("INSERT INTO " + Tables.TBL_ENCUESTAS_RESPUESTAS.TABLE_NAME + " (id_pregunta,respuesta,id_encuesta_sucursal)" +
                            "VALUES (" + opc.getIdPregunta() + ",'" + opc.getRespuesta() + "'," + c.getString(0) + ")");
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
            return true;
    }

    public List<Socio> ObtenerRespuestas() {
        List<Socio> lstSocios;
        try {
            lstSocios = new ArrayList<>();
            dbManger = new EncuestasDbHelper(context);
            String IdEncuestaActual = ObtenerIdEncuestaActual();
            String camposEncSucursales = Tables.TBL_ENCUESTAS_SUCURSALES.id_encuesta_sucursal + "," +
                    Tables.TBL_ENCUESTAS_SUCURSALES.numero + "," +
                    Tables.TBL_ENCUESTAS_SUCURSALES.genero + "," +
                    Tables.TBL_ENCUESTAS_SUCURSALES.edad + "," +
                    Tables.TBL_ENCUESTAS_SUCURSALES.cont_numero_socio;

            String camposResp = Tables.TBL_ENCUESTAS_RESPUESTAS.id_pregunta + "," +
                    Tables.TBL_ENCUESTAS_RESPUESTAS.respuesta + "," + Tables.TBL_ENCUESTAS_RESPUESTAS.id_respuesta;


            String query = "SELECT " + camposEncSucursales + " FROM " +
                    Tables.TBL_ENCUESTAS_SUCURSALES.TABLE_NAME + " WHERE " + Tables.TBL_ENCUESTAS_SUCURSALES.id_encuesta + "=" + IdEncuestaActual;
            Cursor c = dbManger.db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    Socio s = new Socio();
                    s.setIdEncuesta(IdEncuestaActual);
                    s.setNombre(c.getString(1));
                    s.setGenero(c.getString(2));
                    s.setFecha_nacimiento(c.getString(3));
                    s.setContNumeroSocio(c.getString(4));
                    s.setLisrR(new ArrayList<Respuesta>());
                    query = "SELECT " + camposResp + " FROM " + Tables.TBL_ENCUESTAS_RESPUESTAS.TABLE_NAME
                            + " where " + Tables.TBL_ENCUESTAS_RESPUESTAS.id_encuesta_sucursal + " = " + c.getString(0);
                    Cursor cResp = dbManger.db.rawQuery(query, null);
                    if (cResp.moveToFirst()) {
                        do {
                            Respuesta r = new Respuesta();
                            r.setIdPregunta(cResp.getString(0));
                            r.setRespuesta(cResp.getString(1));
                            r.setIdRespuesta(cResp.getString(2));
                            s.getLisrR().add(r);
                        } while (cResp.moveToNext());
                    }
                    lstSocios.add(s);
                } while (c.moveToNext());
            }

        } catch (Exception ex) {
            throw ex;
        }

        return lstSocios;
    }

    public boolean EliminarEncuestas() {
        try
        {
            dbManger = new EncuestasDbHelper(context);
            dbManger.db.execSQL("DELETE FROM "+ Tables.TBL_ENCUESTAS_RESPUESTAS.TABLE_NAME );
            dbManger.db.execSQL("DELETE FROM "+ Tables.TBL_ENCUESTAS_SUCURSALES.TABLE_NAME );

        }catch (Exception ex)
        {
            throw ex;
        }
        return  true;
    }

    public boolean InsertaLog(ArchivoRespuestas archivo) throws ParseException {
        try {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String myDate = timeStampFormat.format(new Date());
            dbManger = new EncuestasDbHelper(context);
            dbManger.db.execSQL("INSERT INTO " + Tables.TBL_LOG_ARCHIVOS_RESPUESTAS.TABLE_NAME + "" +
                    " ( " + Tables.TBL_LOG_ARCHIVOS_RESPUESTAS.NOMBRE + "," + Tables.TBL_LOG_ARCHIVOS_RESPUESTAS.FECHAALTA + "," + Tables.TBL_LOG_ARCHIVOS_RESPUESTAS.RESPUESTAS + "  )" +
                    "VALUES ('" + archivo.getNombre() + "','" +  myDate + "','" + archivo.getRespuestas() + "')");
        }catch (Exception ex) {
            throw ex;
        }
        return true;
    }

    public List<ArchivoRespuestas> ObtenerArchivos() throws SQLException {
        dbManger = new EncuestasDbHelper(context);
        List<ArchivoRespuestas> lstArchivos = new ArrayList<>();
        try {

            Dao dao = helper.getArchivoRespDAO();
            QueryBuilder queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy("id",false);
            lstArchivos = dao.query(queryBuilder.prepare());

        }catch (Exception ex){
            try {
                throw  ex;
            } catch (android.database.SQLException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  lstArchivos;
    }

}
