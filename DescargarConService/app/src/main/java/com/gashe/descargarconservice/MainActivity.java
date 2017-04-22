package com.gashe.descargarconservice;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DESCARGA = "http://www.hrsanroque.com/cervezas/cice.pdf";
    private static final String RUTA_DESTINO = Environment.getExternalStorageDirectory().getPath() + "/pgmcice.pdf";


    private void pedir_permisos(){
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Descargando archivo ...");
        progressDialog.setIcon(R.mipmap.ic_launcher_round);
        progressDialog.setTitle("Accediendo al servidor");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        BroadcastReceiver broadcastReceiver = null;
        IntentFilter intentFilter = null;

        intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        broadcastReceiver = new DescargaCompletaReceiver(this, progressDialog);

        // asocio el fin de la descarga del servicio a mi receiver
        registerReceiver(broadcastReceiver, intentFilter);

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL_DESCARGA));
        // estos mensajes aparecerán en la barra de descarga / notificación
        request.setDescription("en proceso ...");
        request.setTitle("DESCARGANDO PDF");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            // clasifica el documento descargado en el sistema (fotos, pdfs, etc)
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        Uri uri_destino = Uri.fromFile(new File(RUTA_DESTINO));
        request.setDestinationUri(uri_destino);

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        long id_descarga = manager.enqueue(request);
        ((DescargaCompletaReceiver)broadcastReceiver).setId_descarga(id_descarga);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pedir_permisos();


    }
}
