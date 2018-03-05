package com.example.var.encuestas;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.var.encuestas.Adapters.PreguntasAdapter;
import com.example.var.encuestas.Adapters.PreguntasClasificacion;
import com.example.var.encuestas.Config.AgenteWS;
import com.example.var.encuestas.Config.AppConfig;
import com.example.var.encuestas.Config.ServiciosWeb;
import com.example.var.encuestas.EncuestasDAO.EncuestaDAO;
import com.example.var.encuestas.Entidades.Encuesta;
import com.example.var.encuestas.Entidades.Preguntas;
import com.example.var.encuestas.Entidades.Respuesta;
import com.example.var.encuestas.Entidades.Socio;
import com.example.var.encuestas.Pages.FragmentCierre;
import com.example.var.encuestas.Pages.FragmentOpcion;
import com.example.var.encuestas.Pages.FragmentSocio;
import com.example.var.encuestas.Pages.FragmentValoracion;
import com.example.var.encuestas.Pages.ZoomOutPageTransformer;
import com.example.var.encuestas.Params.EnviarRepuestasParams;
import com.example.var.encuestas.Params.EstatusResponse;
import com.example.var.encuestas.Utils.CheckWifi;
import com.example.var.encuestas.Utils.DialogoProgreso;
import com.example.var.encuestas.Utils.Notificacion;
import com.example.var.encuestas.Utils.Style;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Encuesta_Activity extends AppCompatActivity {

    private DialogoProgreso pDialog = null;
    private ViewPager pager;
    private AppConfig appConfig;
    private ServiciosWeb serviciosWeb;
    private PreguntasAdapter pageAdapter;
    private EncuestaDAO encDAO = null;
    private Encuesta enc;
    private Notificacion notificacion;
    private Toolbar toolbar;
    ProgressDialog pd;
    CheckWifi checkWifi = null;
    private List<PreguntasClasificacion> listFragmentos;
    private static final int NUM_PAGES = 12;
    private Context context;
    private boolean showInstrucciones = true;
    private Menu menu;
    List<Respuesta> listRespuesta = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        notificacion = new Notificacion(this);
        pDialog = new DialogoProgreso(this);
        encDAO = new EncuestaDAO(getApplicationContext());
        checkWifi = new CheckWifi(this);
        context = this;
        obtenerEncuesta();
        try {
            InitPager();
        } catch (Exception ex) {
            throw ex;
        }
        toolbar = (Toolbar) findViewById(R.id.tbFragValoracion);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        listRespuesta  = new ArrayList<>();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //SI ESTE MENU SE HABILITA AQUI REPITE LAS OPCIONES EN EL TOOLBAR
        getMenuInflater().inflate(R.menu.menu_pages, menu);
        this.menu =menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            if (id == R.id.nav_sig || id == R.id.nav_sig_) {

                if (pager.getCurrentItem() == 0) {
                    FragmentSocio v = (FragmentSocio) listFragmentos.get(pager.getCurrentItem()).getFragment();
                    if (ValidaInfoSocio(v))
                        pager.setCurrentItem(pager.getCurrentItem() + 1);

                } else {
                    if (listFragmentos.get(pager.getCurrentItem()).getIdTipoPregunta().toString().equals("3")) {
                        FragmentValoracion v = (FragmentValoracion) listFragmentos.get(pager.getCurrentItem()).getFragment();
                        RatingBar r = (RatingBar) v.getView().findViewById(R.id.rbValoracion);
                        if (Float.toString(r.getRating()).equals("0.0")) {
                            notificacion.Mensaje("Por favor especifique un número de estrellas");
                        } else
                            pager.setCurrentItem(pager.getCurrentItem() + 1);
                    } else {
                        pager.setCurrentItem(pager.getCurrentItem() + 1);
                    }
                }
            } else if (id == R.id.nav_inicio) {
                pager.setAdapter(null);
                InitFragments();
                InitPager();

            } else {
                int position = pager.getCurrentItem() == 0 ? 0 : (pager.getCurrentItem() - 1);
                pager.setCurrentItem(position);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        }
    }

    public Boolean ValidaInfoSocio(FragmentSocio fs) {
        try {

            EditText txtSocio, txtEdad;
            TextInputLayout inputSocio, inputEdad;
            RadioGroup rg;

            inputSocio = (TextInputLayout) fs.getView().findViewById(R.id.input_layout_numeroSocio);
            inputEdad = (TextInputLayout) fs.getView().findViewById(R.id.input_layout_Edad);
            txtSocio = (EditText) fs.getView().findViewById(R.id.input_numeroSocio);
            txtEdad = (EditText) fs.getView().findViewById(R.id.input_edad);
            rg = (RadioGroup) fs.getView().findViewById(R.id.rgGenero);
            int indiceChecked = rg.getCheckedRadioButtonId();

            if (txtEdad.getText().toString().equals("") && (txtSocio.getText().toString().equals(""))) {
                notificacion.Mensaje("Por favor especifique información ");
                return false;
            } else if (!txtSocio.getText().toString().equals("")) {
                fs.setNumero(txtSocio.getText().toString());
                fs.setGenero(rg.findViewById(indiceChecked).getTag().toString());
                fs.setEdad(txtEdad.getText().toString().equals("") ? "0" : txtEdad.getText().toString());
                fs.setRecurdaNoSocio(true);
                return true;
            } else if (txtEdad.getText().toString().equals("")) {
                notificacion.Mensaje("Por favor especifique su edad");
                return false;
            } else if (indiceChecked == -1) {
                notificacion.Mensaje("Por favor seleccione un género");
                return false;
            } else {
                fs.setEdad(txtEdad.getText().toString().equals("") ? "0" : txtEdad.getText().toString());
                fs.setNumero(txtSocio.getText().toString().equals("") ? "0" : txtSocio.getText().toString());
                fs.setGenero(rg.findViewById(indiceChecked).getTag().toString());
                return true;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void obtenerEncuesta() {
        try {
            enc = encDAO.ObetenerEncuestaCompleta();
            if (enc != null)
                InitFragments();
        } catch (Exception ex) {
            Log.d("ERROR en ws", ex.getMessage());
        }
    }

    public void InitFragments() {
        listFragmentos = new ArrayList<>();
        try {
            listFragmentos.add(new PreguntasClasificacion("socio", new FragmentSocio()));
            for (int i = 0; i < enc.getPreguntas().size(); i++) {
//                1	abierta
//                2	opcion
//                3	valoración
                switch (enc.getPreguntas().get(i).getIdTipoPregunta()) {
                    case "1"://                1 abierta

                        break;

                    case "2"://                2 opcion
                        FragmentOpcion fOpc = new FragmentOpcion();
                        fOpc.setPreg(enc.getPreguntas().get(i));
                        listFragmentos.add(new PreguntasClasificacion("2", fOpc));
                        break;

                    case "3"://                3 valoración
                        FragmentValoracion f = new FragmentValoracion();
                        f.setPreg(enc.getPreguntas().get(i));
                        listFragmentos.add(new PreguntasClasificacion("3", f));
                        break;
                }
            }
            listFragmentos.add(new PreguntasClasificacion("-1", FragmentCierre.newInstance()));

        } catch (Exception ex) {
            notificacion.Mensaje(ex.getMessage());
        }
    }

    public void InitPager() {
        try {
            pager = (ViewPager) findViewById(R.id.viewpager);
            pager.setPageTransformer(true, new ZoomOutPageTransformer());
            pageAdapter = new PreguntasAdapter(getSupportFragmentManager(), listFragmentos);
            pager.setAdapter(pageAdapter);
            onPagerChange();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void GuardarRespuestas(Socio s) {
        try {
            if (encDAO.GuardarRespuesta(s)) {
                notificacion.Mensaje("Encuesta guardada exitosamente");
                finish();
            }
        } catch (Exception ex) {
            notificacion.Mensaje(ex.getMessage());
        }
    }

    public Socio ObtenerRespuestas() {
        Socio s = null;

        try {
            s = new Socio();
            FragmentSocio fs = (FragmentSocio) listFragmentos.get(0).getFragment();
            s.setGenero(fs.getGenero());
            s.setNombre(fs.getNumero());
            s.setFecha_nacimiento(fs.getEdad());
            s.setIdEncuesta(enc.getIdEncuesta());
            s.setContNumeroSocio(fs.getRecurdaNoSocio() ? "T" : "F");
            listRespuesta = new ArrayList<>();
            for (int i = 1; i < listFragmentos.size() - 1; i++) {
                //Respuesta respuesta = new Respuesta();
                String tipoPregunta = listFragmentos.get(i).getIdTipoPregunta();
                if (tipoPregunta.equals("3")) {
                    FragmentValoracion f = (FragmentValoracion) listFragmentos.get(i).getFragment();
                   Log.d("Calificacion :"+ i +" ",f.getResp().getRespuesta());
                    listRespuesta.add(f.getResp());
                } else if (tipoPregunta.equals("2")) {
                    FragmentOpcion fOpc = (FragmentOpcion) listFragmentos.get(i).getFragment();
                    listRespuesta.add(fOpc.getRespuesta());
                }
            }
            s.setLisrR(listRespuesta);
        } catch (Exception ex) {
            notificacion.Mensaje(ex.getMessage());
        }
        return s;
    }

    public void onPagerChange() {
        try {
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (listFragmentos.get(pager.getCurrentItem()).getIdTipoPregunta().equals("socio")) {
                        InitFragmentSocio();
                    }
                }

                @Override
                public void onPageSelected(int position) {
                    if (position == listFragmentos.size() - 1) {
                        EsparaMensajeSalida();

                    } else if (listFragmentos.get(pager.getCurrentItem()).getIdTipoPregunta().equals("3")) {
                        InitRatingBar();
                        if (position == 1 && showInstrucciones) {
                            showInstrucciones = false;
                            LanzarInstrucciones();
                        }
                    } else if (listFragmentos.get(pager.getCurrentItem()).getIdTipoPregunta().equals("2")) {
                        InitGB();
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            // SIRVE PARA DESABILIDAR EL EVENTO DEL TOUCH
            pager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void EsparaMensajeSalida() {

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SincronizarRespuestas(ObtenerRespuestas());
                //ObtenerRespuestas();
            }
        }, secondsDelayed * 1000);

    }

    public void InitFragmentSocio()
    {
        try
        {
            FragmentSocio fragmentSocio =(FragmentSocio) listFragmentos.get(0).getFragment();
            RadioGroup rg = (RadioGroup) fragmentSocio.getView().findViewById(R.id.rgGenero);
            if(rg!=null)
            {
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        try {
                            if( i != -1) {
                                onOptionsItemSelected(menu.findItem(R.id.nav_sig));
                            }
                        }catch(Exception ex)
                        {
                            notificacion.Mensaje(ex.getMessage());
                        }
                    }
                });
            }
        }catch(Exception ex)
        {
            notificacion.Mensaje(ex.getMessage());
        }

    }

    //ESTA FUNNCION SE SOBRECARGA AQUI YA QUE SE NECESITA QUE CADA QUE SELECCIONE  SOBRE EL RATIN BAR CAMBIEN EL PAGER
    //PERO SE SOBRE CARGA AQUI Y EN EL FRAGMENT YA QUE CUANDO SE REGRESAN EN LA PAGINA SE PIERDE LA INFORMACION DEL
    //OBJETO RESPUESTA PERO DEL RAITING BAR SE CONSERVA EL VALOR QUE YA ESTABA SELECCIONADO
    public void InitRatingBar() {
        try {

            FragmentValoracion fv = (FragmentValoracion) listFragmentos.get(pager.getCurrentItem()).getFragment();//(FragmentValoracion) pc.getItem(pager.getCurrentItem());
            RatingBar rb = (RatingBar) fv.getView().findViewById(R.id.rbValoracion);
            final TextView txt = (TextView) fv.getView().findViewById(R.id.textCalif);
            if (rb != null) {
                rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        notificacion.Mensaje("Calificacion: " + Math.round(v));
                        txt.setText("Calificacion: " + Math.round(v));
                        ((FragmentValoracion) listFragmentos.get(pager.getCurrentItem()).getFragment()).getResp().setRespuesta("" + Math.round(v));
                        pager.setCurrentItem(pager.getCurrentItem() + 1);
                    }
                });
            }
        } catch (Exception ex) {
            notificacion.Mensaje(ex.getMessage());
        }
    }

    public void InitGB()
    {
        try {

            final FragmentOpcion fOpcion = (FragmentOpcion) listFragmentos.get(pager.getCurrentItem()).getFragment();
            GridView gvOpcionesPreg = (GridView) fOpcion.getView().findViewById(R.id.gvOpciones);
            if (gvOpcionesPreg != null) {
                gvOpcionesPreg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ImageView img = (ImageView) view.findViewById(R.id.imgCheckOpc);
                        Preguntas preg =fOpcion.getPreg();
                        Respuesta respuesta= fOpcion.getRespuesta();
                        if( preg.getOpciones().get(i).getSeleccionada().toString().equals("F")) {
                            preg.getOpciones().get(i).setSeleccionada("T");
                            respuesta.setIdPregunta(preg.getIdPregunta());
                            respuesta.setRespuesta(preg.getOpciones().get(i).getDescripcion());
                            respuesta.setIdOpcion(preg.getOpciones().get(i).getIdOpcion());
                            img.setImageResource(R.mipmap.redondo_seleccionado);
                        }else
                        {
                            preg.getOpciones().get(i).setSeleccionada("F");
                            img.setImageResource(R.mipmap.redondo);
                        }
                        pager.setCurrentItem(pager.getCurrentItem() + 1);
                    }
                });
            }
        }catch (Exception ex)
        {
            notificacion.Mensaje(ex.getMessage());
        }
    }

    public  void SincronizarRespuestas(final Socio socio)
    {
        try {
            if (checkWifi.Conectado()) {
                EnviarRepuestasParams respuestas = new EnviarRepuestasParams();
                List<Socio> lstSocio = new ArrayList<Socio>();
                lstSocio.add(socio);
                respuestas.setIdSucursal(encDAO.ObtenerSucursalActual());
                respuestas.setListSocio(lstSocio);
                Call<EstatusResponse> respEnc = new AgenteWS().getServiciosWeb().enviarEncuestas(respuestas);
                respEnc.enqueue(new Callback<EstatusResponse>() {
                    @Override
                    public void onResponse(Call<EstatusResponse> call, Response<EstatusResponse> response) {
                        Log.d("cuerpo", call.request().url().toString());
                        EstatusResponse estatus = (EstatusResponse) response.body();
                        if(estatus.getEstatus() == -1)
                            GuardarRespuestas(socio);
                            finish();
                    }
                    @Override
                    public void onFailure(Call<EstatusResponse> call, Throwable t) {
                        GuardarRespuestas(socio);
                        finish();
                    }
                });
            }else
            {
                GuardarRespuestas(socio);
            }
        }
        catch(Exception ex)
        {
            notificacion.Mensaje(ex.getMessage());
        }
    }

    public  void LanzarInstrucciones()
    {
        try
        {
            final Dialog alertDialog = new Dialog(this);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(R.layout.instrucciones_dialogo_layout);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100,0,0,0)));
            alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            ImageView imageCerrar =(ImageView) alertDialog.findViewById(R.id.imageCerrar);
            ImageView ivComenzar =(ImageView) alertDialog.findViewById(R.id.ivComenzar);
            TextView txtInstrucciones = (TextView) alertDialog.findViewById(R.id.txtInstrucciones);
            TextView txtTituloInstrucciones = (TextView) alertDialog.findViewById(R.id.txtTituloInstrucciones);
            Style.Fuentes.AgregarFuente(context,txtInstrucciones);
            Style.Fuentes.AgregarFuente(context,txtTituloInstrucciones);
            alertDialog.setCancelable(true);
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();
            imageCerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            ivComenzar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

        }catch (Exception ex)
        {
            notificacion.Mensaje(ex.getMessage());
        }

    }


}
