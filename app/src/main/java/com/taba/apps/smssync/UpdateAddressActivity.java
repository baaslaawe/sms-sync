package com.taba.apps.smssync;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.taba.apps.smssync.local.SharedPreference;

public class UpdateAddressActivity extends AppCompatActivity {

    private Button btnUpdateDetails;
    private EditText updatedServerAddress;
    private EditText updateNotificationServerAddress;
    private EditText updatedNotificationResponseServerAddress;
    private EditText updatedNotificationTimeInterval;
    private EditText updatedPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);

        this.init();
    }

    @SuppressLint("RestrictedApi")
    private void init() {


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.getSupportActionBar().setTitle(Html.fromHtml("<small>Update Device Details</small>"));

        btnUpdateDetails = findViewById(R.id.btnDetails);

        updatedServerAddress = findViewById(R.id.updatedServerAddress);

        updateNotificationServerAddress = findViewById(R.id.notificationServerAddress);

        updatedNotificationResponseServerAddress = findViewById(R.id.notificationResponseServerAddress);

        updatedNotificationTimeInterval = findViewById(R.id.notificationTime);

        updatedPhoneNumber = findViewById(R.id.devicePhoneNumber);

        if (SharedPreference.getServerAddress(getBaseContext()) != "") {
            updatedServerAddress.setText(SharedPreference.getServerAddress(getBaseContext()));
        }

        if (SharedPreference.getNotificationServerAddress(getBaseContext()) != "") {
            updateNotificationServerAddress.setText(SharedPreference.getNotificationServerAddress(getBaseContext()));
        }

        if (SharedPreference.getNotificationResponseServerAddress(getBaseContext()) != "") {
            updatedNotificationResponseServerAddress.setText(SharedPreference.getNotificationServerAddress(getBaseContext()));
        }

        if (SharedPreference.getNotificationTimeInterval(getBaseContext()) != 0){
            updatedNotificationTimeInterval.setText(SharedPreference.getNotificationTimeInterval(getBaseContext())+"");
        }

        if (SharedPreference.getDevicePhoneNumber(getBaseContext()) != "") {
            updatedPhoneNumber.setText(SharedPreference.getDevicePhoneNumber(getBaseContext()));
        }


        btnUpdateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = updatedServerAddress.getText().toString();
                String notificationAddress = updateNotificationServerAddress.getText().toString();
                String notificationResponseAddress = updatedNotificationResponseServerAddress.getText().toString();
                String phoneNumber = updatedPhoneNumber.getText().toString();
                int notificationTimeInterval = Integer.parseInt(updatedNotificationTimeInterval.getText().toString());

                boolean condition = validate();

                condition &= SharedPreference.updateServerAddress(getBaseContext(), address);
                condition &= SharedPreference.updateDevicePhoneNumber(getBaseContext(), phoneNumber);
                condition &= SharedPreference.updateNotificationServerAddress(getBaseContext(), notificationAddress);
                condition &= SharedPreference.updateNotificationResponseServerAddress(getBaseContext(), notificationResponseAddress);
                condition &= SharedPreference.updateNotificationTimeInterval(getBaseContext(),notificationTimeInterval);

                if (condition) {
                    SharedPreference.markNonFirstRun(getBaseContext());
                    Intent intent = new Intent(UpdateAddressActivity.this, DashboardActivity.class);
                    Toast.makeText(getBaseContext(), "Details have been updated", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private boolean validate(){
        if (TextUtils.isEmpty(updatedServerAddress.getText().toString())) {
            updatedServerAddress.requestFocus();
            updatedServerAddress.setError("This field can not be empty");
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

        if (TextUtils.isEmpty(updatedPhoneNumber.getText().toString())) {
            updatedPhoneNumber.requestFocus();
            updatedPhoneNumber.setError("Phone number can not be empty");
            return false;
        }


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }
}
