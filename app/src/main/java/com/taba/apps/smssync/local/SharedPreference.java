package com.taba.apps.smssync.local;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.taba.apps.smssync.notification.NotificationService;

public class SharedPreference {

    public static final String FIRST_RUN = "first_run";

    public static final String NOTIFICATION_SERVER_ADDRESS = "notification_server_address";

    public static final String NOTIFICATION_RESPONSE_SERVER_ADDRESS = "notification_response_server_address";

    public static final String NOTIFICATION_TIME_INTERVAL = "notification_time_interval";

    public static final String SERVER_ADDRESS = "server_address";

    public static final String DEVICE_PHONE_NUMBER = "device_phone_number";

    public static final int DEFAULT_TIME_INTERVAL = 5000;


    public static boolean markNonFirstRun(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(FIRST_RUN, 0);
        return preferences.edit().putBoolean(FIRST_RUN, false).commit();
    }

    public static boolean isFirstRun(Context context){
        SharedPreferences preferences = context.getSharedPreferences(FIRST_RUN,0);
        return preferences.getBoolean(FIRST_RUN,true);
    }

    public static boolean updateServerAddress(Context context,String address){
        SharedPreferences preferences = context.getSharedPreferences(SERVER_ADDRESS, 0);
        return preferences.edit().putString(SERVER_ADDRESS,address).commit();

    }

    public static boolean updateNotificationServerAddress(Context context,String address){
        SharedPreferences preferences = context.getSharedPreferences(NOTIFICATION_SERVER_ADDRESS, 0);
        return preferences.edit().putString(NOTIFICATION_SERVER_ADDRESS,address).commit();

    }

    public static boolean updateNotificationResponseServerAddress(Context context,String address){
        SharedPreferences preferences = context.getSharedPreferences(NOTIFICATION_RESPONSE_SERVER_ADDRESS, 0);
        return preferences.edit().putString(NOTIFICATION_RESPONSE_SERVER_ADDRESS,address).commit();

    }

    public static boolean updateNotificationTimeInterval(Context context, int seconds){
        int milliseconds = seconds * 1000;
        SharedPreferences preferences = context.getSharedPreferences(NOTIFICATION_TIME_INTERVAL,0);
        //Intent reloadServiceIntent = new Intent(context, NotificationService.class);
        //reloadServiceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //context.stopService(reloadServiceIntent);
       // context.startService(reloadServiceIntent);
        return preferences.edit().putInt(NOTIFICATION_TIME_INTERVAL,milliseconds).commit();
    }

    public static boolean updateDevicePhoneNumber(Context context,String phone){
        SharedPreferences preferences = context.getSharedPreferences(DEVICE_PHONE_NUMBER, 0);
        return preferences.edit().putString(DEVICE_PHONE_NUMBER,phone).commit();

    }

    public static String getServerAddress(Context context){
        SharedPreferences preferences = context.getSharedPreferences(SERVER_ADDRESS, 0);
        return preferences.getString(SERVER_ADDRESS,"");
    }


    public static String getNotificationServerAddress(Context context){
        SharedPreferences preferences = context.getSharedPreferences(NOTIFICATION_SERVER_ADDRESS, 0);
        return preferences.getString(NOTIFICATION_SERVER_ADDRESS,"");
    }

    public static String getNotificationResponseServerAddress(Context context){
        SharedPreferences preferences = context.getSharedPreferences(NOTIFICATION_RESPONSE_SERVER_ADDRESS, 0);
        return preferences.getString(NOTIFICATION_RESPONSE_SERVER_ADDRESS,"");
    }

    public static int getNotificationTimeInterval(Context context){
        SharedPreferences preferences = context.getSharedPreferences(NOTIFICATION_TIME_INTERVAL,0);
        return preferences.getInt(NOTIFICATION_TIME_INTERVAL,DEFAULT_TIME_INTERVAL)/1000;
    }


    public static String getDevicePhoneNumber(Context context){
        SharedPreferences preferences = context.getSharedPreferences(DEVICE_PHONE_NUMBER, 0);
        return preferences.getString(DEVICE_PHONE_NUMBER,"");
    }
}
