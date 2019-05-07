package com.example.icar.my_notification.service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.icar.my_notification.MainActivity;
import com.example.icar.my_notification.Model.Db;
import com.example.icar.my_notification.R;
import com.example.icar.my_notification.helpers.Constants;

import java.text.SimpleDateFormat;



public class NotificationService extends Service {


    Db database;
    String date, id, title, description, notification;
    int notifIndex = 1;

    final String TAG = "TimeLog";

    @Override
    public void onCreate() {
        super.onCreate();
        database = new Db(this);

        Log.d(TAG, "Сервис уведомлений запущен ...");
    }



    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "Сервис уведомлений стартовал ...");
        sendNotif();
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }



    void sendNotif() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
        date = simpleDateFormat.format(new java.util.Date());

        database.open();


        Cursor cursor = database.getItemForDate(date);
        Context context = this;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder;


        Resources res = context.getResources();


        if(cursor.moveToFirst())
        {

            do{
               final String  id = cursor.getString(cursor.getColumnIndex(Db.KEY_ID));


                if(Constants.VERSION.equals("debug")){
                    title = "my id "+id;
                }else{
                    title = cursor.getString(cursor.getColumnIndex(Db.KEY_TITLE));
                }

                description = cursor.getString(cursor.getColumnIndex(Db.KEY_DESCRIPTION));
                notification = cursor.getString(cursor.getColumnIndex(Db.KEY_NOTIFICATION));


                final Intent intent = new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //activity clear task i find 2 years. i realy don't know reason of bug




                final PendingIntent pendingIntent = PendingIntent.getActivity(context, notifIndex, intent, PendingIntent.FLAG_ONE_SHOT);
                //другой баг. нужно ствить one_shot чтоб заметки выводили корректный айди


                builder = new Notification.Builder(context);

                builder.setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.ic_message_text)
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                        .setTicker(context.getResources().getString(R.string.new_notification))
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentTitle(title)
                        .setContentText(description); // Текст уведомления

                // Notification notification = builder.getNotification(); // до API 16
                Notification notification = builder.build();

                notification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;


                notificationManager.notify(notifIndex, notification);

                notifIndex+=1;

            }
            while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Сервис уведомлений уничтожен!");
    }
}