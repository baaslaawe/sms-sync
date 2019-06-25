package com.taba.apps.smssync.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    public static final String FIRST_RUN = "first_run";


    public static boolean markNonFirstRun(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(FIRST_RUN, 0);
        return preferences.edit().putBoolean(FIRST_RUN, false).commit();
    }

    public static boolean isFirstRun(Context context){
        SharedPreferences preferences = context.getSharedPreferences(FIRST_RUN,0);
        return preferences.getBoolean(FIRST_RUN,true);
    }
}
