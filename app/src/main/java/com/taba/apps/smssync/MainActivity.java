package com.taba.apps.smssync;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.taba.apps.smssync.local.SharedPreference;
import com.taba.apps.smssync.tools.Tool;

public class MainActivity extends AppCompatActivity {

    public static final int RECEIVE_SMS_PERMISSION = 1;

    private EditText addressInput;
    private EditText phoneInput;
    private Button btnSaveDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.handlePermissions();

        btnSaveDetails = findViewById(R.id.btnDetails);
        addressInput = findViewById(R.id.serverAddress);
        phoneInput = findViewById(R.id.devicePhoneNumber);



        if (!SharedPreference.isFirstRun(getBaseContext())){
            Intent intent = new Intent(MainActivity.this,DashboardActivity.class);
            startActivity(intent);
            finish();
        }


        btnSaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address = addressInput.getText().toString();
                String phoneNumber = phoneInput.getText().toString();

                boolean condition = validateAddress(address);
                condition &= validatePhoneNumber(phoneNumber);
                condition &= SharedPreference.updateServerAddress(getBaseContext(),address);
                condition &= SharedPreference.updateDevicePhoneNumber(getBaseContext(),phoneNumber);

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
    }

    private boolean validateAddress(String address){
        if (TextUtils.isEmpty(address)){
            addressInput.setError("This field can not be empty");
            return false;
        }
        return true;
    }

    private boolean validatePhoneNumber(String phone){
        if (TextUtils.isEmpty(phone)){
            phoneInput.setError("Phone number can not be empty");
            return false;
        }
        return true;
    }

}
