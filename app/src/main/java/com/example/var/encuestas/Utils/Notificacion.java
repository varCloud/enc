package com.example.var.encuestas.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by rexv666480 on 27/09/2016.
 */
public class Notificacion {

    private Context context;

    public Notificacion(Context c) {
        this.context=c;
    }

    public  void Mensaje(String mensaje) {
        try
        {
            Toast toast1 =
                    Toast.makeText(this.context,mensaje, Toast.LENGTH_LONG);
            toast1.show();
        }catch(Exception ex)
        {
            Log.e("ENCUESTAS ERROR EN ",ex.getMessage());
        }
    }
}
