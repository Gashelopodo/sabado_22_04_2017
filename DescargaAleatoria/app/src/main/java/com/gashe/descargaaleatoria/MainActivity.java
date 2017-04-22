package com.gashe.descargaaleatoria;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private void lanzarNotificacion(String string){
        //builder, notification manager
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle("Nueva alerta");
        builder.setContentText("Aqui el texto de la alerta de la aplicación");
        builder.setAutoCancel(true);

        //aquí iré cuando el usuario toque la not
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("MENSAJE", string);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntent);

        //lanzar la notificación
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notificationManager.notify(350, notification);

    }

    private void lanzarNotificacionImagen(Bitmap bitmap){
        //builder, notification manager
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle("Nueva alerta");
        builder.setContentText("Aqui el texto de la alerta de la aplicación");
        builder.setAutoCancel(true);

        //aquí iré cuando el usuario toque la not
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("FOTO", bitmap);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntent);
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));

        //lanzar la notificación
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notificationManager.notify(350, notification);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lanzarNotificacion("CADIIIII");

        try{

            Object object = new DescargarNotificacion().execute().get();
            if(object == null) Log.d(getClass().getCanonicalName(), "Nada que notificar");
            else if(object instanceof Bitmap){
                Log.d(getClass().getCanonicalName(), "REcibí una imagen");
                lanzarNotificacionImagen((Bitmap)object);
            }else{
                Log.d(getClass().getCanonicalName(), "Recibí un String");
                //TODO: lanzar notificación
                lanzarNotificacion((String)object);
            }

        }catch (Exception e){

            Log.e(getClass().getCanonicalName(), "Error: "+e.getLocalizedMessage());

        }




    }

}
