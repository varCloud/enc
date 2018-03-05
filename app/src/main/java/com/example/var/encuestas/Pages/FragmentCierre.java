package com.example.var.encuestas.Pages;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.var.encuestas.R;


/**
 * Created by rexv666480 on 05/10/2016.
 */
public class FragmentCierre extends Fragment {

    private View v;
    public static final FragmentCierre newInstance()
    {
        FragmentCierre f  = new FragmentCierre();
        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        try {
            v = inflater.inflate(R.layout.fragment_cierre_layout, container, false);
        }
        catch(Exception ex)
        {
            throw  ex;
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        try {
            menu.findItem(R.id.nav_atras).setVisible(false);
            menu.findItem(R.id.nav_sig).setVisible(false);
            super.onCreateOptionsMenu(menu, inflater);
        }catch (Exception ex)
        {
            throw  ex;
        }
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
