package com.bvd.android.agentie.employee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.bvd.android.agentie.MyApp;
import com.bvd.android.agentie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripDetailsActivity extends AppCompatActivity {
    @BindView(R.id.detailsNameText)
    public EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        ButterKnife.bind(this);
        ((MyApp) getApplication()).getInjector().inject(this);

    }
}
