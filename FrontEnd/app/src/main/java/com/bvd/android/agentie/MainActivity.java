package com.bvd.android.agentie;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.bvd.android.agentie.client.ClientActivity;
import com.bvd.android.agentie.employee.EmployeeActivity;
import com.bvd.android.utils.NetworkUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Inject
    NetworkUtils networkUtils;

    @BindView(R.id.mainEmployeeBtn)
    public Button employeeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((MyApp) getApplication()).getInjector().inject(this);

        employeeBtn.setEnabled(networkUtils.isNetworkAvailable(this));


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
