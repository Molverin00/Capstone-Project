package com.example.newsfeed.util;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

public class MyApp extends MultiDexApplication {

    private static Context appContext;


    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        /* If you has other classes that need context object to initialize when application is created,
         you can use the appContext here to process. */
    }


    public static Context getAppContext() {
        return appContext;
    }

}
