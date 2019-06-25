package com.taba.apps.smssync.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tool {

    public static String getCurrentTimeStamp(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStamp  = dateFormat.format(new Date());
        return timeStamp;
    }
}
