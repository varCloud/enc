package com.example.var.encuestas.Pages;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;



import com.example.var.encuestas.Entidades.Preguntas;
import com.example.var.encuestas.Entidades.Respuesta;
import com.example.var.encuestas.R;
import com.example.var.encuestas.Utils.Style;

/**
 * Created by rexv666480 on 27/09/2016.
 */
public class FragmentValoracion extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    private  TextView txt;
    private  Preguntas preg;
    private  Respuesta resp = new Respuesta();
    private  RatingBar rb;

    public String getTextIdTIpoPregunta() {
        return textIdTIpoPregunta;
    }

    public void setTextIdTIpoPregunta(String textIdTIpoPregunta) {
        this.textIdTIpoPregunta = textIdTIpoPregunta;
    }

    private String textIdTIpoPregunta;


    public Respuesta getResp() {
        return resp;
    }

    public void setResp(Respuesta result) {
        this.resp = result;
    }

    public void setPreg(Preguntas preg) {
        this.preg = preg;
    }

    public  Preguntas getPreg() {
        return preg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = null;
        try {

            rootView = (ViewGroup) inflater.inflate( R.layout.fragment_valoracion_layout, container, false);
            setHasOptionsMenu(true);
            rb =(RatingBar) rootView.findViewById(R.id.rbValoracion);
            txt = (TextView) rootView.findViewById(R.id.textCalif);
            textIdTIpoPregunta = rootView.findViewById(R.id.idTIpoPregunta).getTag().toString();
            TextView messageTextView = (TextView) rootView.findViewById(R.id.txtPregunta);
            InitBar();
            messageTextView.setText(preg.getDescripcion());
            txt.setText("");
            InitRespuesta();
            Style.Fuentes.AgregarFuente(getContext(),txt);
            Style.Fuentes.AgregarFuente(getContext(),messageTextView);
        }
        catch(Exception ex)
        {
            throw  ex;
        }
        return rootView;
    }


    public  void InitRespuesta()
    {
        this.resp = new Respuesta();
        this.resp.setIdPregunta(preg.getIdPregunta());
        this.resp.setRespuesta("0");
        this.resp.setIdOpcion("null");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        try {
            super.onCreateOptionsMenu(menu, inflater);
        }catch (Exception ex)
        {
            throw  ex;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public void InitBar()
    {
        try {

            rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    txt.setText("Calificacion: " + Math.round(v));
                    resp.setRespuesta(""+Math.round(v));

                }
            });
        }catch (Exception ex) {
            throw  ex;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
