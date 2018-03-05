package com.example.var.encuestas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.var.encuestas.Config.AppConfig;
import com.example.var.encuestas.Config.ServiciosWeb;
import com.example.var.encuestas.EncuestasDAO.EncuestaDAO;
import com.example.var.encuestas.Entidades.ArchivoRespuestas;
import com.example.var.encuestas.Entidades.Encuesta;
import com.example.var.encuestas.Entidades.Socio;
import com.example.var.encuestas.Params.EnviarRepuestasParams;
import com.example.var.encuestas.Params.EstatusResponse;
import com.example.var.encuestas.Utils.CheckWifi;
import com.example.var.encuestas.Utils.EscribirArchivo;
import com.example.var.encuestas.Utils.Notificacion;
import com.facebook.stetho.Stetho;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView imgIniciar = null;
    ImageView imgCerrarMenu = null;
    ImageView imgBlue = null;
    private Notificacion notificacion = null;
    private EncuestaDAO encDAO = null;
    private AppConfig appConfig;
    private ServiciosWeb serviciosWeb;
    private CheckWifi checkWifi;
    private Context c = this;
    private boolean contraCorrecta = false;
    private int idMenu = 0;
    private DrawerLayout drawer;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Stetho.initializeWithDefaults(this);


            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            notificacion = new Notificacion(getApplicationContext());
            imgIniciar = (ImageView) findViewById(R.id.imgIniciar);
            //imgNoGracias = (ImageView) findViewById(R.id.imgCerrar);
            //dbManger = new EncuestasDbHelper(getApplicationContext());
            encDAO = new EncuestaDAO(getApplicationContext());
            checkWifi = new CheckWifi(getApplicationContext());
            imgCerrarMenu = (ImageView) (navigationView.getHeaderView(0).findViewById(R.id.imageCerrarMenu));
            imgBlue = (ImageView) (navigationView.getHeaderView(0).findViewById(R.id.imageViewBlue));
            initClickImageMenuCerrar();
            InitProcessDialos();
            InitImgIniciar();
            ConfigurarEncuesta();



        } catch (Exception ex) {
            notificacion.Mensaje(ex.getMessage());
        }
        //InitClick();
    }

    public void initClickImageMenuCerrar() {

        imgCerrarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    drawer.closeDrawer(GravityCompat.START);
                } catch (Exception ex) {
                    notificacion.Mensaje(ex.getMessage());
                }

            }
        });

    }

    public void InitProcessDialos() {
        pd = ProgressDialog.show(MainActivity.this, "Caja Morelia", null, true);
        pd.setContentView(R.layout.progress_bar_layout);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pd.dismiss();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        idMenu = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (idMenu == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        idMenu = item.getItemId();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bluecloud.com.mx/"));
        startActivity(browserIntent);
        //LanzarDialogoContra();
        //drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void InitImgIniciar() {

        try {
            imgIniciar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (encDAO.ExisteEncuestaConfigurada()) {
                        Intent i = new Intent(getApplication(), Encuesta_Activity.class);
                        startActivity(i);
                    } else {
                        notificacion.Mensaje("No tienes encuestas configuradas");
                    }
                }
            });

            imgBlue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bluecloud.com.mx/"));
                    startActivity(browserIntent);
                }
            });

        } catch (Exception ex) {
            notificacion.Mensaje(ex.getMessage());
        }
    }

    public void ConfigurarEncuesta() {
        try {

            if (encDAO.ExisteEncuestaPorConfigurar()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.AlertCmv));
                builder.setMessage("Tienes una nueva encuesta por Configurar \n si tienes una encuesta previamente configurada se desactivará")
                        .setTitle("Mensaje")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                Encuesta enc = encDAO.ObtenerEncuesta();
                                Intent i = new Intent(getApplication(), Configurar_Encuestas.class);
                                Bundle b = new Bundle();
                                b.putString("idEncuesta", enc.getId());
                                b.putString("descripcion", enc.getDescripcion());
                                i.putExtras(b);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.AlertCmv));
                builder.setMessage("Por el momento no existen encuestas por configurar")
                        .setTitle("Mensaje")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        } catch (Exception ex) {
            notificacion.Mensaje(ex.getMessage());
        }
    }

    public boolean LanzarDialogoContra() {
        try {
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
            View mView = layoutInflaterAndroid.inflate(R.layout.cuadro_dialogo_layout, null);
            AlertDialog.Builder alertDialogBuilderWithText = new AlertDialog.Builder(c);
            alertDialogBuilderWithText.setView(mView);
            final EditText txtContra = (EditText) mView.findViewById(R.id.userInputDialog);
            alertDialogBuilderWithText
                    .setCancelable(false)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                            SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                            String pass = prefs.getString("pass", "null");
                            if (txtContra.getText().toString().equals(pass)) {
                                if (idMenu == R.id.nav_sync) {
                                    EnviarRepuestasParams resp = new EnviarRepuestasParams();
                                    resp.setListSocio(encDAO.ObtenerRespuestas());
                                    resp.setIdSucursal(encDAO.ObtenerSucursalActual());
                                    if (resp.getIdSucursal().equals("")) {
                                        notificacion.Mensaje("Esta encuesta no cuenta con una sucursal configurada.");
                                        return;
                                    }
                                    SincronizarRespuestas(resp);
                                }
                                else if (idMenu == R.id.nav_crea_archivo) {
                                    Intent i = new Intent(getApplication(), CreaArchivoRespuestas.class);
                                    startActivity(i);
                                }
                            } else if (pass.toString().equals("null")) {
                                ConfigurarEncuesta();
                            } else
                                notificacion.Mensaje("Contraseña incorrecta");
                        }
                    })
                    .setNegativeButton("Cancelar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogBox, int id) {
                                    contraCorrecta = false;
                                    dialogBox.cancel();
                                }
                            });
            alertDialogBuilderWithText.setCancelable(false);
            AlertDialog alertDialogAndroid = alertDialogBuilderWithText.create();
            alertDialogAndroid.show();

        } catch (Exception ex) {
            throw ex;
        }
        return contraCorrecta;
    }

    public void SincronizarRespuestas(final EnviarRepuestasParams resp) {
        try {

            pd.show();
            if (checkWifi.Conectado()) {
                appConfig = new AppConfig();
                serviciosWeb = appConfig.getRetrofit().create(ServiciosWeb.class);
                Call<EstatusResponse> respEnc = serviciosWeb.enviarEncuestas(resp);
                respEnc.enqueue(new Callback<EstatusResponse>() {
                    @Override
                    public void onResponse(Call<EstatusResponse> call, Response<EstatusResponse> response) {
                        Log.d("cuerpo", call.request().url().toString());
                        pd.dismiss();
                        EstatusResponse estatus = (EstatusResponse) response.body();
                        if (estatus.getEstatus() == 200) {
                            try {
                                String nombreArchivo= EscribirArchivo.Archivo.ObtenerNombreArchivoEncuesta();
                                encDAO.InsertaLog(new ArchivoRespuestas(nombreArchivo,new Gson().toJson(resp)));
                                encDAO.EliminarEncuestas();
                                notificacion.Mensaje("Encuestas sctualizadas correctamente");

                            } catch (Exception e) {
                                notificacion.Mensaje(e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<EstatusResponse> call, Throwable t) {
                        Log.d("Respuesta del SERVICIO", t.getMessage());
                        pd.dismiss();
                    }
                });
            } else {
                notificacion.Mensaje("Creando archivo para sincronizar de manera local");
                if(resp.getListSocio().size() >0) {
                    String info = new Gson().toJson(resp);
                    String nombreArchivo= EscribirArchivo.Archivo.Escribir(info,"");
                    encDAO.InsertaLog(new ArchivoRespuestas(nombreArchivo,info));
                    encDAO.EliminarEncuestas();
                    notificacion.Mensaje("El archivo ha sido generado exitosamente");
                    pd.dismiss();
                }else
                {
                    pd.dismiss();
                    notificacion.Mensaje("No existen encuestas contestadas para sincronizar");
                }
            }

        } catch (Exception ex) {
            pd.dismiss();
            notificacion.Mensaje(ex.getMessage());
        }
    }

    public void GuardarRespuestas(final Socio s) {
        try {

            if (checkWifi.Conectado()) {
                EnviarRepuestasParams respuestas = new EnviarRepuestasParams();
                List<Socio> lstSocio = new ArrayList<Socio>();
                lstSocio.add(s);
                respuestas.setIdSucursal(encDAO.ObtenerSucursalActual());
                respuestas.setListSocio(lstSocio);
                appConfig = new AppConfig();
                serviciosWeb = appConfig.getRetrofit().create(ServiciosWeb.class);
                Call<EstatusResponse> respEnc = serviciosWeb.enviarEncuestas(respuestas);
                respEnc.enqueue(new Callback<EstatusResponse>() {
                    @Override
                    public void onResponse(Call<EstatusResponse> call, Response<EstatusResponse> response) {
                        Log.d("cuerpo", call.request().url().toString());
                        EstatusResponse estatus = (EstatusResponse) response.body();
                        if(estatus.getEstatus() == 500)
                            if (encDAO.GuardarRespuesta(s)) {
                                notificacion.Mensaje("Encuesta guardada exitosamente");
                            }
                    }
                    @Override
                    public void onFailure(Call<EstatusResponse> call, Throwable t) {
                        if (encDAO.GuardarRespuesta(s)) {
                            notificacion.Mensaje("Encuesta guardada exitosamente");
                        }
                    }
                });
            }else
            {
                if (encDAO.GuardarRespuesta(s)) {
                    notificacion.Mensaje("Encuesta guardada exitosamente");
                }
            }

        } catch (Exception ex) {
            pd.dismiss();
            notificacion.Mensaje(ex.getMessage());
        }
    }

}
