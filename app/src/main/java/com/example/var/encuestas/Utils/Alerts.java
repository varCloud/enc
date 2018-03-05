package com.example.var.encuestas.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by rexv666480 on 29/09/2016.
 */
public class Alerts {

    private  Context context;

    public Alerts(Context context) {
        this.context = context;
    }

    public void showAlertDialog(String title, String message, Boolean status) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}


