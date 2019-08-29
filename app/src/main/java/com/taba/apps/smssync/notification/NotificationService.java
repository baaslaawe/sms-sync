package com.taba.apps.smssync.notification;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.taba.apps.smssync.local.Database;
import com.taba.apps.smssync.local.SharedPreference;
import com.taba.apps.smssync.sms.Sms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class NotificationService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                String url = SharedPreference.getNotificationServerAddress(getBaseContext());

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {

                                    JSONObject messagesObject = new JSONObject(response);
                                    JSONArray messages = messagesObject.getJSONArray("messages");

                                    for (int index = 0; index < messages.length(); index++) {

                                        JSONObject currentMessage = messages.getJSONObject(index);
                                        String message = currentMessage.getString("message");
                                        String receiverPhone = currentMessage.getString("receiverPhone");

                                        SmsManager smsManager = SmsManager.getDefault();
                                        smsManager.sendTextMessage(receiverPhone, null, message, null, null);


                                        //Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();


                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }


                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return null;
                    }
                };

                queue.add(stringRequest);


            }
        }, 0,5000);

        return START_STICKY;
    }
}
