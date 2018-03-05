package com.example.var.encuestas.Pages;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.var.encuestas.R;
import com.example.var.encuestas.Utils.Notificacion;
import com.example.var.encuestas.Utils.Style;
import com.example.var.encuestas.Utils.Teclado;

/**
 * Created by rexv666480 on 07/10/2016.
 */
public class FragmentSocio  extends Fragment {

    private  Notificacion notificacion;
    private  String Numero;
    private  String Edad;
    private  String genero;
    private  boolean recurdaNoSocio;
    EditText txtSocio, txtEdad;
    TextInputLayout inputSocio, inputEdad;
    CheckBox chNoRecuerdo;
    RadioGroup rgGenero;
    TextView txtGenero;
    LinearLayout llInfo;
    Animation slideUpAnimation, slideDownAnimation;

    public boolean getRecurdaNoSocio() {
        return recurdaNoSocio;
    }

    public void setRecurdaNoSocio(boolean recurdaNoSocio) {
        this.recurdaNoSocio = recurdaNoSocio;
    }



    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = null;
        try {

            setHasOptionsMenu(true);
            notificacion = new Notificacion(getContext());

            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_socio_layout, container, false);
            inputSocio = (TextInputLayout) rootView.findViewById(R.id.input_layout_numeroSocio);
            inputEdad = (TextInputLayout) rootView.findViewById(R.id.input_layout_Edad);
            txtSocio = (EditText) rootView.findViewById(R.id.input_numeroSocio);
            txtEdad = (EditText) rootView.findViewById(R.id.input_edad);

            //chNoRecuerdo = (CheckBox) rootView.findViewById(R.id.chNoRecuerdo);
            rgGenero = (RadioGroup) rootView.findViewById(R.id.rgGenero);
            txtGenero = (TextView) rootView.findViewById(R.id.txtGenero);
            Style.Fuentes.AgregarFuente(getContext(), txtEdad);
            Style.Fuentes.AgregarFuente(getContext(), txtSocio);
            Style.Fuentes.AgregarFuente(getContext(), txtEdad);
            Animated();

            //llInfo.setVisibility(View.INVISIBLE);
//            inputEdad.setVisibility(View.INVISIBLE);
//            rgGenero.setVisibility(View.INVISIBLE);
//            txtGenero.setVisibility(View.INVISIBLE);
            //chNoRecuerdo.setChecked(recurdaNoSocio);
            Teclado.setupUI(rootView,getActivity());

        }
        catch(Exception ex)
        {
            notificacion.Mensaje(ex.getMessage());
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        try {
            menu.findItem(R.id.nav_atras).setVisible(false);
            menu.findItem(R.id.nav_atras_).setVisible(false);
            menu.findItem(R.id.nav_inicio).setVisible(false);
            super.onCreateOptionsMenu(menu, inflater);
        }catch (Exception ex)
        {
            throw  ex;
        }
    }

    public void PintarVista(boolean check)
    {
        try
        {
            if(check)
            {
                inputSocio.setEnabled(false);
                txtSocio.setText("");
                llInfo.setVisibility(View.VISIBLE);
                llInfo.startAnimation(slideDownAnimation);

            }else
            {
                inputSocio.setEnabled(true);
                llInfo.startAnimation(slideUpAnimation);
                llInfo.setVisibility(View.INVISIBLE);
            }

        }catch(Exception ex)
        {
            throw ex;
        }

    }

    public void Animated()
    {
        slideUpAnimation = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_up_animation);

        slideDownAnimation = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_down_animation);
    }
}
