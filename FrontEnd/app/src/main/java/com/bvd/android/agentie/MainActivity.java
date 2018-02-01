package com.bvd.android.agentie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bvd.android.agentie.client.ClientActivity;
import com.bvd.android.agentie.employee.EmployeeActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.mainClientBtn)
    public void redirectClient() {
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);

    }
    @OnClick(R.id.mainEmployeeBtn)
    public void redirectEmployee() {
        Intent intent = new Intent(this, EmployeeActivity.class);
        startActivity(intent);

    }

}
