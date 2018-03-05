package com.example.var.encuestas.Utils;

import android.os.Environment;
import android.provider.BaseColumns;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rexv666480 on 11/05/2017.
 */
public class EscribirArchivo {

    public static abstract class Archivo implements BaseColumns {

        public static String Escribir(String informacion ,String filename ) throws IOException {
            String sFileName="";
            try {
                if(filename.equals("")) {
                    sFileName = ObtenerNombreArchivoEncuesta();
                }
                else
                    sFileName = filename;
                File root = new File(Environment.getExternalStorageDirectory(), "Calidad en el servicio");
                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root, sFileName);
                if (!gpxfile.exists()) {
                    gpxfile.createNewFile();
                }
                FileWriter writer = new FileWriter(gpxfile, true);
                writer.append(informacion);
                writer.flush();
                writer.close();
            } catch (IOException e) {
              throw  e;
            }
            filename= "";
            return  sFileName;
        }

        public static String ObtenerNombreArchivoEncuesta() {
            String sFileName="";
            String filename="";
            try {

                    SimpleDateFormat timeStampFormat = new SimpleDateFormat("yy_MM_dd-HH_mm");
                    Date myDate = new Date();
                    filename = timeStampFormat.format(myDate);
                    sFileName = "Enc_" + filename + ".txt";

            } catch (Exception e) {
                throw  e;
            }
            return  sFileName;
        }
    }

}
