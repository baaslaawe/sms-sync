package com.taba.apps.smssync;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.taba.apps.smssync.local.SharedPreference;
import com.taba.apps.smssync.tools.Tool;

public class MainActivity extends AppCompatActivity {

    public static final int RECEIVE_SMS_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.handlePermissions();


        if (!SharedPreference.isFirstRun(getBaseContext())){
            Intent intent = new Intent(MainActivity.this,DashboardActivity.class);
            startActivity(intent);
        }

        SharedPreference.markNonFirstRun(getBaseContext());

    }

    private void handlePermissions() {
        //Receive SMS Permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, RECEIVE_SMS_PERMISSION);
        }
    }

}
