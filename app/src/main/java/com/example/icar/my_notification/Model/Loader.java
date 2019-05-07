package com.example.icar.my_notification.Model;


import android.content.Context;
import android.database.Cursor;

import com.example.icar.my_notification.helpers.Priority;

import java.util.ArrayList;
import java.util.List;

public class Loader {

    Db database;
    Cursor cursor;

    public List<NotificationObject> getData(Priority priority, Context context) {
        List<NotificationObject> data = new ArrayList<>();

        database = new Db(context);
        database.open();

        cursor = database.getData(priority);

        if (cursor.moveToFirst()) {
            do{
                NotificationObject notification = new NotificationObject();
                notification.setTitle(cursor.getString(cursor.getColumnIndex(Db.KEY_TITLE)));
                notification.setDescription(cursor.getString(cursor.getColumnIndex(Db.KEY_DESCRIPTION)));
                notification.setDate(cursor.getString(cursor.getColumnIndex(Db.KEY_DATE)));
                notification.setPriority(cursor.getString(cursor.getColumnIndex(Db.KEY_PRIORITY)));
                notification.setId(cursor.getString(cursor.getColumnIndex(Db.KEY_ID)));
                notification.setNotification(cursor.getString(cursor.getColumnIndex(Db.KEY_NOTIFICATION)));
                notification.setIntData(cursor.getLong(cursor.getColumnIndex(Db.KEY_DATE_INTEGER)));
                data.add(notification);
            }while (cursor.moveToNext());

        }
        cursor.close();
        database.close();


        return data;
    }

    //it will be method for loading from server
    public void loadFromServer(){

    }

}
