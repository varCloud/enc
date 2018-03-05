package com.example.var.encuestas.Pages;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.var.encuestas.Entidades.OpcionesPreguntas;
import com.example.var.encuestas.Entidades.Preguntas;
import com.example.var.encuestas.Entidades.Respuesta;
import com.example.var.encuestas.R;

/**
 * Created by rexv666480 on 27/09/2016.
 */
public class FragmentOpcion  extends Fragment {

    private GridView gvOpcionesPreg;
    private TextView opcionRespuesta;
    private Context context;
    private Respuesta respuesta;

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }

    public Preguntas getPreg() {
        return preg;
    }

    public void setPreg(Preguntas preg) {
        this.preg = preg;
    }

    private Preguntas preg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = null;
        try {
            rootView = (ViewGroup) inflater.inflate( R.layout.fragment_opcion_layout, container, false);
            setHasOptionsMenu(true);
            gvOpcionesPreg = (GridView) rootView.findViewById(R.id.gvOpciones);
            TextView txtPregunta = (TextView) rootView.findViewById(R.id.txtPregunta);
            txtPregunta.setText(preg.getDescripcion());
            context = getActivity().getApplicationContext();
            respuesta = new Respuesta();
            if(gvOpcionesPreg != null)
            {
                //gvOpcionesPreg.setAdapter(null);
                InitGvOpciones();
            }
        }
        catch(Exception ex)
        {
            throw  ex;
        }
        return rootView;
    }

    public void InitGvOpciones()
    {

        gvOpcionesPreg.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return preg.getOpciones().size();
            }

            @Override
            public Object getItem(int i) {
                return preg.getOpciones().get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                try {
                    if (view == null) {
                        LayoutInflater inflater = (LayoutInflater) context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = inflater.inflate(R.layout.grid_item_opcion_pregunta_layout, viewGroup, false);
                    }

                    opcionRespuesta = (TextView) view.findViewById(R.id.txtOpcionPregunta);
                    OpcionesPreguntas objResp = (OpcionesPreguntas) getItem(i);
                    opcionRespuesta.setText(objResp.getDescripcion());
                } catch (Exception ex) {
                    throw ex;
                }
                return view;
            }
        });

        gvOpcionesPreg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                limpiarChek();
                ImageView img = (ImageView) view.findViewById(R.id.imgCheckOpc);
                if(preg.getOpciones().get(i).getSeleccionada().toString().equals("F")) {
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
            }
        });
    }

    public void limpiarChek()
    {
        try
        {
            int count = gvOpcionesPreg.getAdapter().getCount();
            for (int i = 0; i < count; i++) {
                LinearLayout itemLayout = (LinearLayout)gvOpcionesPreg.getChildAt(i); // Find by under LinearLayout
                if(itemLayout != null) {
                    ImageView img = (ImageView) itemLayout.findViewById(R.id.imgCheckOpc);
                    img.setImageResource(R.mipmap.redondo);;
                    preg.getOpciones().get(i).setSeleccionada("F");
                }
            }
        }catch(Exception ex)
        {
            throw  ex;
        }
    }

}
