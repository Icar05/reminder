package com.example.icar.my_notification.Model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.icar.my_notification.helpers.Priority;

public class Db {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final String TABLE_NAME = "notice";

    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DATE = "date";
    public static final String KEY_PRIORITY = "priority";
    public static final String KEY_DATE_INTEGER = "date_integer";
    public static final String KEY_NOTIFICATION = "notification";




    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context cont;
    private String order_by = KEY_DATE_INTEGER;

    public Db(Context context)
    {
        cont = context;
    }

    public String getPath()
    {
        return cont.getDatabasePath(DATABASE_NAME).getAbsolutePath();
    }

    public void open() {
        dbHelper = new DBHelper(cont, DATABASE_NAME, null, DATABASE_VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        if(dbHelper != null)
            dbHelper.close();
    }

    //set data in db
    public void writeData(String title, String description, String date, long intD, Priority priority, String notification) {



        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, title);
        cv.put(KEY_DESCRIPTION, description);
        cv.put(KEY_DATE, date);
        cv.put(KEY_DATE_INTEGER, intD);
        cv.put(KEY_PRIORITY, priority.toString());
        cv.put(KEY_NOTIFICATION, notification);
        db.insert(TABLE_NAME,null,cv);
    }

    public void updateItem(String title, String description, String date, long intDate, Priority priority,String notification, String id){
        String selection = "_id = ?";
        String[] selectionArgs = new String[] { id };


        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, title);
        cv.put(KEY_DESCRIPTION, description);
        cv.put(KEY_DATE, date);
        cv.put(KEY_DATE_INTEGER, intDate);
        cv.put(KEY_PRIORITY, priority.toString());
        cv.put(KEY_NOTIFICATION, notification);
        db.update(TABLE_NAME, cv, selection, selectionArgs);
    }

    public Cursor getItemForDate(String date) {
        String selection = "date = ? AND notification = ?";
        String[] selectionArgs = new String[] { date, "show" };
        return db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    public  Cursor getItem(String id) {
        String selection = "_id = ?";
        String[] selectionArgs = new String[] { id };
        return db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }



    Cursor getData(Priority priority){
        if(priority == Priority.ALL){
            return db.query(TABLE_NAME, null, null, null, null, null,order_by);
        }else{
            String selection = "priority = ?";
            String[] selectionArgs = new String[] { priority.toString() };
            return db.query(TABLE_NAME, null, selection, selectionArgs, null, null, order_by);
        }
    }


    //get item by id
    public void deleteItem(long id)
    {
        db.delete(TABLE_NAME, KEY_ID + " = " +id, null);
    }



    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table "+TABLE_NAME+ "(" +
                    KEY_ID + " integer primary key, " +
                    KEY_TITLE + " text, " +
                    KEY_DESCRIPTION + " text, " +
                    KEY_DATE + " text, " +
                    KEY_DATE_INTEGER + " integer ,"+
                    KEY_NOTIFICATION + " text, " +
                    KEY_PRIORITY + " text"
                    +")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("drop table if exist "+TABLE_NAME);
            onCreate(db);
        }
    }
}