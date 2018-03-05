package com.example.var.encuestas.Sql;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.var.encuestas.Entidades.ArchivoRespuestas;
import com.example.var.encuestas.Entidades.Encuesta;
import com.example.var.encuestas.Entidades.Region;
import com.example.var.encuestas.Entidades.Sucursal;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by rexv666480 on 30/09/2016.
 */
public class OrmHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "encuestas.db";
    private static final int DATABASE_VERSION = 2;
    private Dao<Sucursal, Integer> sucursalDao;
    private Dao<Region, Integer> regionDao;
    private Dao<Encuesta, Integer> encEDAO;
    private Dao<ArchivoRespuestas, Integer> archivoRespDAO;

    public OrmHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
                TableUtils.createTable(connectionSource, Sucursal.class);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
                 onCreate(database, connectionSource);
    }

    public Dao<Sucursal, Integer> getSucursalDao() throws SQLException, java.sql.SQLException {
        if (sucursalDao == null) {
            sucursalDao = getDao(Sucursal.class);
        }
        return sucursalDao;
    }


    public Dao<Region, Integer> getRegionDao() throws SQLException, java.sql.SQLException {
        if (regionDao == null) {
            regionDao = getDao(Region.class);
        }
        return regionDao;
    }

    public Dao<Encuesta, Integer> getEncDao() throws SQLException, java.sql.SQLException {
        if ( encEDAO== null) {
            encEDAO = getDao(Encuesta.class);
        }
        return encEDAO;
    }

    public Dao<ArchivoRespuestas, Integer> getArchivoRespDAO() throws SQLException, java.sql.SQLException {
        if ( archivoRespDAO== null) {
            archivoRespDAO = getDao(ArchivoRespuestas.class);
        }
        return archivoRespDAO;
    }
}
