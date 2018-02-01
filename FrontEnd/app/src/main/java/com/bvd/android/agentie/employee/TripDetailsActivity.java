package com.bvd.android.agentie.employee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bvd.android.agentie.MyApp;
import com.bvd.android.agentie.R;
import com.bvd.android.agentie.model.Trip;
import com.bvd.android.agentie.rest.TripController;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class TripDetailsActivity extends AppCompatActivity {

    @Inject
    public TripController controller;

    private Trip trip;

    @BindView(R.id.detailsNameText)
    public EditText nameText;
    @BindView(R.id.detailsStatusText)
    public EditText statusText;
    @BindView(R.id.detailsTypeText)
    public EditText typeText;
    @BindView(R.id.detailsRoomsText)
    public EditText roomsText;

    @BindView(R.id.detailsProgressBar)
    public ProgressBar progressBar;

    @BindView(R.id.detailsUpdateBtn)
    public Button updateBtn;
    @BindView(R.id.detailsDeleteBtn)
    public Button deleteBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        ButterKnife.bind(this);
        ((MyApp) getApplication()).getInjector().inject(this);
        progressBar.setVisibility(View.INVISIBLE);

        trip = (Trip) getIntent().getExtras().getSerializable("item");
        if (trip == null) {
            Timber.v("Didn't receive the object from previous view");
            finish();
        } else {
            Timber.v("Received item=" + trip);
            setFields();

        }

    }

    private void setFields() {
        nameText.setText(trip.getName());
        statusText.setText(trip.getStatus());
        typeText.setText(trip.getType());
        roomsText.setText(String.valueOf(trip.getRooms()));
    }

    @OnClick(R.id.detailsDeleteBtn)
    void removeItem() {
        makeEverythingInvisibleAndShowProgress();

        //call
        //if succes else put a Toast
        finish();
    }


    @OnClick(R.id.detailsUpdateBtn)
    void updateItem() {
        makeEverythingInvisibleAndShowProgress();

        if (fieldsValid()) {

        } else {
            Toast.makeText(this, "Check your input", Toast.LENGTH_SHORT).show();
        }
        finish();


    }

    private boolean fieldsValid() {
        if (istEmpty(nameText) || istEmpty(statusText) || istEmpty(typeText) || istEmpty(roomsText))
            return false;
        return Integer.parseInt(String.valueOf(roomsText.getText())) > 0;
    }


    private void makeEverythingInvisibleAndShowProgress() {
        progressBar.setVisibility(View.VISIBLE);
        nameText.setVisibility(View.INVISIBLE);
        statusText.setVisibility(View.INVISIBLE);
        typeText.setVisibility(View.INVISIBLE);
        roomsText.setVisibility(View.INVISIBLE);
        updateBtn.setVisibility(View.INVISIBLE);
        deleteBtn.setVisibility(View.INVISIBLE);
    }

    private void makeEverythingVisibleAndHideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        nameText.setVisibility(View.VISIBLE);
        statusText.setVisibility(View.VISIBLE);
        typeText.setVisibility(View.VISIBLE);
        roomsText.setVisibility(View.VISIBLE);
        updateBtn.setVisibility(View.VISIBLE);
        deleteBtn.setVisibility(View.VISIBLE);
    }

    private boolean istEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
