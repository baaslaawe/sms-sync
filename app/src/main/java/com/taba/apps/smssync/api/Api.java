package com.taba.apps.smssync.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.taba.apps.smssync.local.Database;
import com.taba.apps.smssync.local.SharedPreference;
import com.taba.apps.smssync.sms.Sms;

import java.util.HashMap;
import java.util.Map;

public class Api {

    public static final String SERVER_ADDRESS = "http://192.168.1.104:5200/sms";

    public static final String SECURITY_KEY = "";

    public static void postSms(final Context context, final Sms sms, final boolean fromDatabase) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = SharedPreference.getServerAddress(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(context, response, Toast.LENGTH_LONG).show();

                        if (fromDatabase) {
                            Database database = new Database(context);
                            database.synchronizeSms(sms.getId());

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                Database database = new Database(context);

                if (!fromDatabase) {
                    sms.setStatus(Sms.STATUS_UNSYNCHRONIZED);
                    if (database.addSms(sms)) {
                        // Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show();
                    }
                }

            }


        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();


                //params.put("id",sms.getId()+"");
                params.put("senderPhone", sms.getSenderPhone());
                params.put("receiverPhone", sms.getReceiverPhone());
                params.put("message", sms.getMessage());
                params.put("receivedTime", sms.getReceivedTime());
                //params.put("status",sms.getStatus()+"");


                return params;
            }
        };

        queue.add(stringRequest);
    }

}
