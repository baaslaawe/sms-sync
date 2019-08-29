package com.taba.apps.smssync;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.taba.apps.smssync.local.SharedPreference;
import com.taba.apps.smssync.notification.NotificationService;
import com.taba.apps.smssync.tools.Tool;

public class MainActivity extends AppCompatActivity {

    public static final int RECEIVE_SMS_PERMISSION = 1;
    public static final int SEND_SMS_PERMISSION = 2;


    private EditText addressInput;
    private EditText updateNotificationServerAddress;
    private EditText updatedNotificationResponseServerAddress;
    private EditText updatedNotificationTimeInterval;
    private EditText phoneInput;
    private Button btnSaveDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.handlePermissions();

        btnSaveDetails = findViewById(R.id.btnDetails);
        addressInput = findViewById(R.id.serverAddress);
        updateNotificationServerAddress = findViewById(R.id.notificationServerAddress);
        updatedNotificationResponseServerAddress = findViewById(R.id.notificationResponseServerAddress);
        updatedNotificationTimeInterval = findViewById(R.id.notificationTime);
        phoneInput = findViewById(R.id.devicePhoneNumber);

        getSupportActionBar().setTitle(Html.fromHtml("<small>Sms Sync</small>"));


        if (SharedPreference.isFirstRun(getBaseContext())){
            Intent notificationServiceIntent = new Intent(MainActivity.this, NotificationService.class);
            startService(notificationServiceIntent);
        }


        if (!SharedPreference.isFirstRun(getBaseContext())){
            Intent intent = new Intent(MainActivity.this,DashboardActivity.class);
            startActivity(intent);
            finish();
        }


        btnSaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address = addressInput.getText().toString();
                String notificationAddress = updateNotificationServerAddress.getText().toString();
                String notificationResponseAddress = updatedNotificationResponseServerAddress.getText().toString();
                String phoneNumber = phoneInput.getText().toString();
                int notificationTimeInterval = Integer.parseInt(updatedNotificationTimeInterval.getText().toString());

                boolean condition = validate();
                condition &= SharedPreference.updateServerAddress(getBaseContext(),address);
                condition &= SharedPreference.updateDevicePhoneNumber(getBaseContext(),phoneNumber);
                condition &= SharedPreference.updateNotificationServerAddress(getBaseContext(), notificationAddress);
                condition &= SharedPreference.updateNotificationResponseServerAddress(getBaseContext(), notificationResponseAddress);
                condition &= SharedPreference.updateNotificationTimeInterval(getBaseContext(),notificationTimeInterval);

                if(condition){
                    SharedPreference.markNonFirstRun(getBaseContext());
                    Intent intent = new Intent(MainActivity.this,DashboardActivity.class);
                    Toast.makeText(getBaseContext(),"Details have been updated",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void handlePermissions() {
        //Receive SMS Permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, RECEIVE_SMS_PERMISSION);
        }
        //Send SMS Permission
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION);
        }
    }






    private boolean validate(){
        if (TextUtils.isEmpty(addressInput.getText().toString())) {
            addressInput.requestFocus();
            addressInput.setError("This field can not be empty");
            return false;
        }

        if (TextUtils.isEmpty(updateNotificationServerAddress.getText().toString())) {
            updatedNotificationResponseServerAddress.requestFocus();
            updateNotificationServerAddress.setError("This field can not be empty");
            return false;
        }

        if (TextUtils.isEmpty(updatedNotificationResponseServerAddress.getText().toString())) {
            updatedNotificationResponseServerAddress.requestFocus();
            updatedNotificationResponseServerAddress.setError("This field can not be empty");
            return false;
        }

        if (TextUtils.isEmpty(updatedNotificationTimeInterval.getText().toString())){
            updatedNotificationTimeInterval.requestFocus();
            updatedNotificationTimeInterval.setError("This field can not be empty");
            return false;
        }

        if (TextUtils.isEmpty(phoneInput.getText().toString())) {
            phoneInput.requestFocus();
            phoneInput.setError("Phone number can not be empty");
            return false;
        }


        return true;
    }



}
