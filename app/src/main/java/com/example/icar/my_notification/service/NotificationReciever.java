package com.example.icar.my_notification.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.icar.my_notification.helpers.Constants;

import java.util.Calendar;


public class NotificationReciever extends BroadcastReceiver {

    final String TAG = "TimeLog";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "РЕСИВЕР НАЧАЛ РАБОТУ : )");

        registerInAlarmManager(context);
    }


    void registerInAlarmManager(Context context) {

       int flag = PendingIntent.FLAG_UPDATE_CURRENT;

        //register pending intent NOTICE!!! flag must be FLAG_NO_CREATE
        Intent notif_service = new Intent(context,NotificationService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, notif_service, flag);


       AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

       Calendar alarmStartTime = Calendar.getInstance();
       Calendar now = Calendar.getInstance();


       //каждую минуту
       if(Constants.VERSION.equals("debug")){
           alarm.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), 60 * 1000 , pi);
       }else{

           //каждое утро в 8 часов - потому что утро это хорошо)
           alarmStartTime.set(Calendar.HOUR_OF_DAY, 8);
           alarmStartTime.set(Calendar.MINUTE, 00);
           alarmStartTime.set(Calendar.SECOND, 0);

           if (now.after(alarmStartTime)) {
               Log.d(TAG,"Нужно добавить день, нынешнее время больше 8");
               alarmStartTime.add(Calendar.DATE, 1);
           }

           alarm.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
           Log.d(TAG,"Сигнал задан на 8 утра");

       }

    }

}
