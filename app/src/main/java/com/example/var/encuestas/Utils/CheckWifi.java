package com.example.var.encuestas.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by rexv666480 on 29/09/2016.
 */
public class CheckWifi {

private Context context;

    public CheckWifi(Context context) {
        this.context = context;
    }

    protected Boolean conectadoWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected Boolean conectadoRedMovil(){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public  boolean Conectado()
    {
        if(conectadoWifi())
            return true;
        if(conectadoRedMovil())
            return  true;

        return  false;
    }
}
