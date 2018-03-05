package com.example.var.encuestas.Utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by rexv666480 on 10/01/2017.
 */
public class DialogoProgreso {

    private Context context;
    private MaterialDialog dialog;
    public DialogoProgreso(Context c) {
         this.context =  c;
    }

    public void MuestraDialogIndeterminado(String titulo , String contenido)
    {
        try
        {
            dialog  = new MaterialDialog.Builder(context)
                    .title(titulo)
                    .content(contenido)
                    .progress(true,0)
                    .show();

        }  catch (Exception ex)
        {
            throw ex;
        }
    }

    public void CerrarDialog()
    {
        try {
            dialog.dismiss();
        }catch (Exception ex)
        {
            throw  ex;
        }

    }

}
