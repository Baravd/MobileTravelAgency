package com.bvd.android.agentie.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bvd.android.agentie.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.clientSeeAllBtn)
    public void redirectToAllItems() {
        Intent intent = new Intent(this, CustomerAllItemsActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.clientMyItemsBtn)
    public void redirectMyItems() {
        Intent intent = new Intent(this, CustomerMyItemsActivity.class);
        startActivity(intent);


    }

}
