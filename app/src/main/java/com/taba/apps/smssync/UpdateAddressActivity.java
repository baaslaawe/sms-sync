package com.taba.apps.smssync;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private EditText updatedPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);

        this.init();
    }

    @SuppressLint("RestrictedApi")
    private void init(){


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.getSupportActionBar().setTitle("Update Device Details");

        btnUpdateDetails = findViewById(R.id.btnDetails);

        updatedServerAddress = findViewById(R.id.updatedServerAddress);

        updatedPhoneNumber = findViewById(R.id.devicePhoneNumber);

        if (SharedPreference.getServerAddress(getBaseContext()) != ""){
            updatedServerAddress.setText(SharedPreference.getServerAddress(getBaseContext()));
        }

        if (SharedPreference.getDevicePhoneNumber(getBaseContext()) != ""){
            updatedPhoneNumber.setText(SharedPreference.getDevicePhoneNumber(getBaseContext()));
        }


        btnUpdateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = updatedServerAddress.getText().toString();
                String phoneNumber = updatedPhoneNumber.getText().toString();

                boolean condition = validateAddress(address);
                condition &= validatePhoneNumber(phoneNumber);
                condition &= SharedPreference.updateServerAddress(getBaseContext(),address);
                condition &= SharedPreference.updateDevicePhoneNumber(getBaseContext(),phoneNumber);

                if(condition){
                    SharedPreference.markNonFirstRun(getBaseContext());
                    Intent intent = new Intent(UpdateAddressActivity.this,DashboardActivity.class);
                    Toast.makeText(getBaseContext(),"Details have been updated",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private boolean validateAddress(String address){
        if (TextUtils.isEmpty(address)){
            updatedServerAddress.setError("This field can not be empty");
            return false;
        }
        return true;
    }

    private boolean validatePhoneNumber(String phone){
        if (TextUtils.isEmpty(phone)){
            updatedPhoneNumber.setError("Phone number can not be empty");
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){

            onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }
}
