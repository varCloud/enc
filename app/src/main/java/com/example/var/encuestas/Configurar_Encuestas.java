package com.example.var.encuestas;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.var.encuestas.EncuestasDAO.EncuestaDAO;
import com.example.var.encuestas.Entidades.Encuesta;
import com.example.var.encuestas.Entidades.Region;
import com.example.var.encuestas.Entidades.Sucursal;
import com.example.var.encuestas.Utils.Alerts;
import com.example.var.encuestas.Utils.Notificacion;

import java.sql.SQLException;
import java.util.List;

public class Configurar_Encuestas extends AppCompatActivity {

    Encuesta enc = null;
    private EncuestaDAO encDAO= null;
    private List<Region> regiones;
    private List<Sucursal> sucursales;
    private Spinner spiSucursales;
    private Spinner spiRegiones;
    private Context context;
    private Button btnGuardar;
    private Alerts alert;
    private Notificacion noti;
    private RadioGroup rg = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar__encuestas);
        enc = new Encuesta();
        encDAO = new EncuestaDAO(getApplicationContext());
        Bundle parameters = getIntent().getExtras();
        enc.setIdEncuesta(parameters.getString("idEncuesta"));
        enc.setDescripcion(parameters.getString("descripcion"));
        context = this;
        alert = new Alerts(this);
        noti = new Notificacion(this);
        rg = (RadioGroup) findViewById(R.id.rgArea);

        try {
            InitActividad();
            GuardarConfiguracion();
            ItemSelectSucursales();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public  void InitActividad() throws SQLException {
        try
        {
            btnGuardar= (Button) findViewById(R.id.btnGuardaConfig);
            regiones  = encDAO.ObtenerRegiones();
            final ArrayAdapter regionAdapter = new ArrayAdapter(this,  android.R.layout.simple_spinner_dropdown_item, regiones);
            spiRegiones = (Spinner) findViewById(R.id.spiRegiones);
            spiSucursales = (Spinner) findViewById(R.id.spiSucursales);
            spiRegiones.setAdapter(regionAdapter);
            spiRegiones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    enc.setIdRegion(regiones.get(i).getId());//seteamos el id de la region
                    try {
                        InitAdapterSucursales(enc.getIdRegion());

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });



        }catch (Exception ex)
        {
            throw ex;
        }
    }

    public void InitAdapterSucursales(String idRegion) throws SQLException {
        try
        {
            sucursales =   encDAO.ObtenerScursales(idRegion);
            if(sucursales.size() >0) {
                ArrayAdapter sucuAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, sucursales);
                spiSucursales.setAdapter(null);
                spiSucursales.setAdapter(sucuAdapter);
            }
        }catch(Exception ex)
        {
            throw ex;
        }
    }

    public void ItemSelectSucursales()
    {
        try
        {
            spiSucursales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    enc.setIdSucursal(sucursales.get(i).getIdSucursal());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }catch(Exception ex)
        {
            throw  ex;
        }
    }

    public  void GuardarConfiguracion ()
    {
        try
        {
            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(Configurar_Encuestas.this, R.style.AlertCmv));
                    builder.setMessage("Estas seguro que deseas guardar los cambios?")
                            .setTitle("Mensaje")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if (validaForm(enc)) {
                                        if (encDAO.GuardarConfiguracion(enc)) {
                                            noti.Mensaje("Configuración terminada, Ahora puedes contestar encuestas");
                                            onBackPressed();
                                        }
                                        else
                                            noti.Mensaje("Espere un momento porfavor y vuelva a intentarlo");
                                    }
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }catch(Exception ex)
        {
            throw  ex;
        }
    }


    public boolean validaForm(Encuesta enc)
    {
        String mensaje = "";
        Boolean valido = false;
        int radioButtonID=-1;
        try {
            if (enc.getIdRegion().equals("") || enc.getIdRegion().equals("-1"))
                mensaje = "Especifique un region valida";
            if (enc.getIdSucursal().equals("") || enc.getIdSucursal().equals("-1"))
                mensaje = "Especifique una sucursal valida";

            radioButtonID  = rg.getCheckedRadioButtonId();
            if(radioButtonID == -1)
                mensaje = "Especifique una area valida";
            else
            {
                View radioButton = rg.findViewById(radioButtonID);
                enc.setArea(radioButton.getTag().toString());
            }

        }catch(Exception ex)
        {
            noti.Mensaje(ex.getMessage());
        }
        if(mensaje.equals(""))
            valido = true;
        else
        {
            noti.Mensaje(mensaje);
            valido = false;
        }
            return  valido;
    }

}
