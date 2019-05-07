package com.example.icar.my_notification.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by icar on 28.01.17.
 */
public class MyPrefManager {

    private final String LOG = "mypreference";

    private String TAG = MyPrefManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "androidhive_gcm";


    //sound key
    private static String KEY_CURRENT = "key_current";


    // Constructor
    public MyPrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


}
