package com.example.var.encuestas.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.var.encuestas.Entidades.Preguntas;

import java.util.List;

/**
 * Created by rexv666480 on 27/09/2016.
 */
public class PreguntasAdapter extends FragmentStatePagerAdapter {
    private  List<Preguntas>  preg;
    private FragmentManager fm;
    private  List<PreguntasClasificacion> fragmentos;

//    public PreguntasAdapter(FragmentManager fm,List<Preguntas> preg) {
//        super(fm);
//        this.preg=preg;
//        this.fm = fm;
//    }

    public PreguntasAdapter(FragmentManager fm,List<PreguntasClasificacion> f) {
        super(fm);
        this.fm = fm;
        this.fragmentos= f;
    }

    @Override
    public Fragment getItem(int position) {

        //FragmentValoracion f = new FragmentValoracion();
        //f.setPreg(preg.get(position));
        return this.fragmentos.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragmentos.size();
    }
}

