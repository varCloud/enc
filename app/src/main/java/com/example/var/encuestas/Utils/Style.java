package com.example.var.encuestas.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by rexv666480 on 13/10/2016.
 */
public class Style {


    public static abstract class Fuentes {

        public static void AgregarFuente(Context c, TextView v)
        {
            try {
                Typeface mont = Typeface.createFromAsset(c.getAssets(), "fonts/Montserrat-Regular.ttf");
                v.setTypeface(mont);
            }catch (Exception ex)
            {
                throw  ex;
            }
        }
    }

}
