package com.example.icar.my_notification.service;


import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class InvoceService extends Service {


    final String TAG = "TimeLog";

    @Override
    public void onCreate() {
        super.onCreate();
//        Log.d(TAG, "INVOKER SERVICE CREATED!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d(TAG, "INVOKER SERVICE STARTED!");
        invokeNotifService();
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
//        Log.d(TAG, "INVOKER SERVICE DESTROYED!");
        super.onDestroy();
    }

    private void invokeNotifService()
    {
        if(!checkCreatedAlarm(this))
        {
            //шлем рессивверу уникальный ури
            Log.d(TAG, "МЕНЕДЖЕР НЕ ЗАРЕГЕСТРИРОВАН!");
            Intent intent = new Intent("com.example.icar.my_notification.service");
            sendBroadcast(intent);
        } else {
            Log.d(TAG, "МЕНЕДЖЕР УЖЕ ЗАРЕГЕСТРИРОВАН!");
        }
    }

    boolean checkCreatedAlarm(Context context) {
        boolean answ = (PendingIntent.getService(context, 0, new Intent(context, NotificationService.class),
                PendingIntent.FLAG_NO_CREATE)
                != null);

        return  answ;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }






}
