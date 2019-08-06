package com.taba.apps.smssync;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.taba.apps.smssync.api.Api;
import com.taba.apps.smssync.local.Database;
import com.taba.apps.smssync.local.SharedPreference;
import com.taba.apps.smssync.sms.Sms;

public class DashboardActivity extends AppCompatActivity {


    private TextView nonSynchronizedSmsCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        this.init();

    }

    private void init() {
        this.getSupportActionBar().setTitle(R.string.dashbord_title);

        this.nonSynchronizedSmsCount = this.findViewById(R.id.nonSynchronizedSmsCount);
        this.updateSmsCounter();

    }

    private void updateSmsCounter() {
        Database database = new Database(getBaseContext());
        nonSynchronizedSmsCount.setText(database.getNonSynchronizedSms().size() + "");
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuSyncMessages:
                Database database = new Database(getBaseContext());

                for (Sms sms : database.getNonSynchronizedSms()) {
                    Api.postSms(getBaseContext(), sms, true);

                }

                nonSynchronizedSmsCount.setText(database.getNonSynchronizedSms().size()+"");

                if (nonSynchronizedSmsCount.getText().toString().equals("0")){
                    Toast.makeText(getBaseContext(),"All pending messages have been synchronized with the server",Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.menuUpdateDetails:

                Intent intent = new Intent(DashboardActivity.this, UpdateAddressActivity.class);
                startActivity(intent);
                finish();

                break;
        }


        return super.onOptionsItemSelected(item);
    }


}
