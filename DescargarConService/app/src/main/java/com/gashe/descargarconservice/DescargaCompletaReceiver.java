package com.gashe.descargarconservice;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

public class DescargaCompletaReceiver extends BroadcastReceiver {

    private Context actividad;
    private ProgressDialog progressDialog;
    private long id_descarga;

    public void setId_descarga(long id_descarga) {
        this.id_descarga = id_descarga;
    }

    public DescargaCompletaReceiver(Context actividad, ProgressDialog progressDialog) {
        this.actividad = actividad;
        this.progressDialog = progressDialog;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(getClass().getCanonicalName(), "Servicio de descarga finalizado");

        // acceder a la info de la descarga
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id_descarga);
        Cursor cursor = downloadManager.query(query);
        cursor.moveToFirst();
        int ref = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int estado_descarga = cursor.getInt(ref);

        switch (estado_descarga){
            case DownloadManager.STATUS_SUCCESSFUL:

                    Log.d(getClass().getCanonicalName(), "La descarga ha finaliado bien");

                break;

            case DownloadManager.STATUS_FAILED:

                    Log.d(getClass().getCanonicalName(), "La descarga ha fallado");
                    Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_LONG).show();
                break;

        }

        progressDialog.dismiss();
        context.unregisterReceiver(this);

    }
}
