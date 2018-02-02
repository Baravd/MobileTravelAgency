package com.bvd.android.agentie.employee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.bvd.android.agentie.MyApp;
import com.bvd.android.agentie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTripActivity extends AppCompatActivity {
    @BindView(R.id.addStatusText)
    public EditText statusText;

    @BindView(R.id.addTypeText)
    public EditText typeText;

    @BindView(R.id.addRoomsText)
    public EditText roomsText;

    @BindView(R.id.addNameText)
    public EditText nameText;

    @BindView(R.id.addAddBtn)
    public Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        ButterKnife.bind(this);
        ((MyApp) getApplication()).getInjector().inject(this);

    }

    private boolean fieldsValid() {
        if (isEmpty(nameText) || isEmpty(statusText) || isEmpty(typeText) || isEmpty(roomsText))
            return false;
        return Integer.parseInt(String.valueOf(roomsText.getText())) > 0;
    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

}
