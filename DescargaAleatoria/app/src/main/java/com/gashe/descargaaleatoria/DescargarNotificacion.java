package com.gashe.descargaaleatoria;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cice on 22/4/17.
 */

public class DescargarNotificacion extends AsyncTask<Void, Void, Object> {

    private final static String URL_SERVLET = "http://femxa-ebtm.rhcloud.com/HayMensajes";

    private Bitmap obtenerImagen(HttpURLConnection httpURLConnection) throws IOException{
        Bitmap bitmap = null;

            InputStream inputStream = httpURLConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);

        return bitmap;
    }

    private String obtenerMensaje(HttpURLConnection httpURLConnection) throws  IOException{
        String string = null;
        BufferedReader bufferedReader = null;

            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            string = bufferedReader.readLine();

            bufferedReader.close(); // deberiamos poner esto en el finally de try cacth

        return string;
    }


    @Override
    protected Object doInBackground(Void... params) {
        Object object = null;
        URL url = null;
        HttpURLConnection httpURLConnection = null;

        try{

            url = new URL(URL_SERVLET);
            httpURLConnection = (HttpURLConnection) url.openConnection(); // omito el método que por defecto será GET

            switch (httpURLConnection.getResponseCode()){

                case HttpURLConnection.HTTP_NO_CONTENT:
                        Log.d(getClass().getCanonicalName(), "No hay content");
                    break;
                case HttpURLConnection.HTTP_OK:

                        if(httpURLConnection.getContentType().startsWith("text/plain")){
                            Log.d(getClass().getCanonicalName(), "recibimos texto");
                            String mensaje = obtenerMensaje(httpURLConnection);
                            object = mensaje;
                        }else{
                            Log.d(getClass().getCanonicalName(), "recibimos imagen");
                            Bitmap bitmap = obtenerImagen(httpURLConnection);
                            object = bitmap;
                        }

                    break;
                default:
                    Log.e(getClass().getCanonicalName(), "Ha ocurrido un error");

            }


        }catch(Exception e){

            Log.e(getClass().getCanonicalName(), "Error: "+e.getLocalizedMessage());

        }finally {

            if(httpURLConnection != null) httpURLConnection.disconnect();

        }

        return object;
    }
}
