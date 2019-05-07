package com.example.icar.my_notification;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.example.icar.my_notification.helpers.MyPrefManager;

import io.fabric.sdk.android.Fabric;

/**
 * Created by icar on 28.03.17.
 */
public class CoreApplication  extends MultiDexApplication {

    public static final String TAG = CoreApplication.class.getSimpleName();


    private static CoreApplication mInstance;
    private MyPrefManager pref;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mInstance = this;
    }



    

    public static synchronized CoreApplication getInstance() {
        return mInstance;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    public MyPrefManager getPrefManager() {
        if (pref == null) {
            pref = new MyPrefManager(getApplicationContext());
        }

        return pref;
    }
}
