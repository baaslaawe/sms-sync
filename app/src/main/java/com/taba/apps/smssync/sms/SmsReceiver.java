package com.taba.apps.smssync.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.taba.apps.smssync.api.Api;
import com.taba.apps.smssync.local.Database;
import com.taba.apps.smssync.local.SharedPreference;
import com.taba.apps.smssync.tools.Tool;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        if (bundle != null){
            SmsMessage[] smsMessage = null;
            String messageBody = "";
            String senderPhone = "";

            Object[] pdus = (Object[]) bundle.get("pdus");
            smsMessage = new SmsMessage[pdus.length];
            for (int index = 0; index < smsMessage.length; index++) {
                smsMessage[index] = SmsMessage.createFromPdu((byte[]) pdus[index]);
                senderPhone = smsMessage[index].getDisplayOriginatingAddress();
                messageBody += smsMessage[index].getDisplayMessageBody();
                //messageBody += "\r\n";
            }

            //String tesString = messageBody + " from "+senderPhone;
            //Toast.makeText(context, senderPhone, Toast.LENGTH_LONG).show();


            //Save sms to Database
            Sms sms = new Sms();
            sms.setSenderPhone(senderPhone);
            sms.setReceiverPhone(SharedPreference.getDevicePhoneNumber(context));
            sms.setMessage(messageBody);
            sms.setReceivedTime(Tool.getCurrentTimeStamp());

            Api.postSms(context,sms,false);
        }

    }
}
