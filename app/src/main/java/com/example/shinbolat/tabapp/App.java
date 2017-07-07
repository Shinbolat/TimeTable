package com.example.shinbolat.tabapp;

import android.app.Application;

import com.example.shinbolat.tabapp.utils.Prefs;

/**
 * Created by Shinbolat on 12.01.2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Prefs.init(getApplicationContext());
    }
}
