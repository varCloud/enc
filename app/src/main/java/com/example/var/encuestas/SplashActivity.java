package com.example.var.encuestas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.var.encuestas.Config.AgenteWS;
import com.example.var.encuestas.EncuestasDAO.EncuestaDAO;
import com.example.var.encuestas.Model.EncuestaResponse;
import com.example.var.encuestas.Utils.CheckWifi;
import com.example.var.encuestas.Utils.Notificacion;
import com.example.var.encuestas.Utils.Style;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity {

    ProgressBar pb;
    private  boolean bandera = false;
    private ImageView imgIniciar = null;
    private Notificacion notificacion = null;
    private EncuestaDAO encDAO = null;
    //private AppConfig appConfig;
    //private ServiciosWeb serviciosWeb;
    private AgenteWS agenteWS;
    private CheckWifi checkWifi;
    private TextView tv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        notificacion = new Notificacion(getApplicationContext());
        encDAO = new EncuestaDAO(getApplicationContext());
        checkWifi = new CheckWifi(getApplicationContext());
        pb = (ProgressBar) findViewById(R.id.pbVerificando);
        pb.setVisibility(View.VISIBLE);
        tv = (TextView) findViewById(R.id.tvSplash);
        AddStyle();
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (checkWifi.Conectado()) {

                     pb.setVisibility(View.VISIBLE);
                     tv.setText("Verificando si existe una nueva encuesta");
                     InsertarEncuesta();

                } else {
                    LanazarMain();
                }
            }
        }, secondsDelayed * 2000);
    }

    public void AddStyle()
    {
        try{        Style.Fuentes.AgregarFuente(this,tv);}
        catch(Exception ex)
        {
            notificacion.Mensaje(ex.getMessage());
        }
    }

    public void InsertarEncuesta() {

        try {

            tv.setText("Descargando encuesta");
            String IdEncuestaActual = (encDAO.ObtenerIdEncuestaActual());
            IdEncuestaActual = (IdEncuestaActual.equals("") ? "0" : IdEncuestaActual);
            JsonObject payerReg = new JsonObject();
            payerReg.addProperty("idEncuesta", IdEncuestaActual);

            Call<EncuestaResponse> encuestaR = new AgenteWS().getServiciosWeb().obtenerEncuesta(payerReg);
            encuestaR.enqueue(new Callback<EncuestaResponse>() {
                @Override
                public void onResponse(Call<EncuestaResponse> call, Response<EncuestaResponse> response) {
                    if(response.body() != null) {
                        EncuestaResponse encuesta = (EncuestaResponse) response.body();
                        if (encuesta.getEstatus().toString().equals("200")) {
                            InitShared(encuesta.getUsuario(), encuesta.getPass());
                            try {
                                encDAO.InsertarEncuesta(encuesta.getEncuesta());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            LanazarMain();
                        } else  if(encuesta.getEstatus().toString().equals("500")){
                            LanazarMain();

                        } else if (encuesta.getEstatus().toString().equals("ERROR"))
                        {
                            tv.setText("Error al iniciar la aplicacion  \n"+encuesta.getMsj());
                            pb.setVisibility(View.INVISIBLE);
                        }
                    }else {
                        notificacion.Mensaje(response.raw().message().toString());
                        tv.setText("Revisa tu conexión a intenernet \n"+response.raw().message().toString());
                    }
                }

                @Override
                public void onFailure(Call<EncuestaResponse> call, Throwable t) {
                    notificacion.Mensaje(t.getMessage());
                    pb.setVisibility(View.INVISIBLE);
                    tv.setText("Revisa tu conexión a intenernet \n "+t.getMessage());
                }
            });

        } catch (Exception ex) {
            notificacion.Mensaje( ex.getMessage());
            tv.setText("Revisa tu conexión a intenernet \n "+ex.getMessage());
        }
    }



    public void LanazarMain()
    {
        try{
            Intent i = new Intent(getApplication(), MainActivity.class);
            Bundle b = new Bundle();
            i.putExtras(b);
            startActivity(i);
            this.finish();

        }catch(Exception ex)
        {
            throw  ex;
        }
    }

    public void InitShared(String usuario ,String contraseña)
    {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuario", usuario);
        editor.putString("pass", contraseña);
        editor.commit();
    }
}
