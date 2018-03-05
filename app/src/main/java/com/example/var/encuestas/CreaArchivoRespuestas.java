package com.example.var.encuestas;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.var.encuestas.EncuestasDAO.EncuestaDAO;
import com.example.var.encuestas.Entidades.ArchivoRespuestas;
import com.example.var.encuestas.Utils.EscribirArchivo;
import com.example.var.encuestas.Utils.Notificacion;
import com.example.var.encuestas.Utils.Style;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CreaArchivoRespuestas extends AppCompatActivity {

    private ListView lvArchivos = null;
    private Notificacion notificacion = null;
    private EncuestaDAO encDAO = null;
    private List<ArchivoRespuestas> lstArchivos= null;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_archivo_respuestas);
        this.context = this;
        notificacion = new Notificacion(this);
        encDAO = new EncuestaDAO(context);
        lvArchivos = (ListView) findViewById(R.id.lvArchivos);
        try {
            lstArchivos =  encDAO.ObtenerArchivos();
            if(lstArchivos.size() > 0)
            {
                InitListView();
            }else
                notificacion.Mensaje("No existen respuestas para generar");


        } catch (SQLException e) {
            notificacion.Mensaje(e.getMessage());
        }
    }

    public  void InitListView()
    {
        try{
            lvArchivos.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return lstArchivos.size();
                }

                @Override
                public Object getItem(int i) {
                    return lstArchivos.get(i);
                }

                @Override
                public long getItemId(int i) {
                    return 0;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {

                    LayoutInflater inflater = (LayoutInflater) context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View rowView = inflater.inflate(R.layout.listview_item_crea_archivo_respuestas, null);
                    TextView txtNombreArchivo = (TextView) rowView.findViewById(R.id.txtNombreArchivo);
                    TextView txtFechaArchivo = (TextView) rowView.findViewById(R.id.txtFechaArchivo);


                    Style.Fuentes.AgregarFuente(context,txtNombreArchivo);
                    Style.Fuentes.AgregarFuente(context,txtFechaArchivo);
                    ArchivoRespuestas ar = lstArchivos.get(i);
                    SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    try {
                        Date dt = (Date)timeStampFormat.parse(ar.getFechaCreacion());
                        timeStampFormat  = new SimpleDateFormat("dd/MM/yyyy");
                        txtNombreArchivo.setText(ar.getNombre());
                        txtFechaArchivo.setText(timeStampFormat.format(dt));

                    } catch (ParseException e) {
                       notificacion.Mensaje(e.getMessage());
                    }

                    //SimpleDateFormat timeStampFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");

                    return rowView;
                }
            });

            lvArchivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        EscribirArchivo.Archivo.Escribir(lstArchivos.get(i).getRespuestas(),lstArchivos.get(i).getNombre());
                        notificacion.Mensaje("Archivo generado exitosamente.");
                    } catch (IOException e) {
                        notificacion.Mensaje(e.getMessage());
                    }

                }
            });

        }catch (Exception ex)
        {
            notificacion.Mensaje(ex.getMessage());
        }
    }
}
