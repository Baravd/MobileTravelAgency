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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
        Call<Void> deleteItemCall = controller.deleteItem(trip.getId());


        deleteItemCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                Timber.v("Response code=%s and body=" + response.body(), code);
                if (code == 200) {
                    Toast.makeText(TripDetailsActivity.this, "Successful Delete", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(TripDetailsActivity.this, "Failed whit:" + code, Toast.LENGTH_SHORT).show();
                    makeEverythingVisibleAndHideProgress();
                }
                makeEverythingVisibleAndHideProgress();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TripDetailsActivity.this, "!!!Failed!!!", Toast.LENGTH_SHORT).show();
                makeEverythingVisibleAndHideProgress();

            }
        });
        finish();

    }


    @OnClick(R.id.detailsUpdateBtn)
    void updateItem() {
        makeEverythingInvisibleAndShowProgress();

        if (!fieldsValid()) {
            Toast.makeText(this, "Check your input", Toast.LENGTH_SHORT).show();
            return;
        }
        Trip trip = update();
        Call<Trip> updateItemCall = controller.updateItem(trip);


        updateItemCall.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                int code = response.code();
                Timber.v("Response code=%s and body=" + response.body(), code);
                if (code == 200) {
                    Toast.makeText(TripDetailsActivity.this, "Successful Update", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(TripDetailsActivity.this, "Failed whit:" + code, Toast.LENGTH_SHORT).show();
                    makeEverythingVisibleAndHideProgress();
                }
                makeEverythingVisibleAndHideProgress();
            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {
                Toast.makeText(TripDetailsActivity.this, "!!!Failed!!!", Toast.LENGTH_SHORT).show();
                makeEverythingVisibleAndHideProgress();

            }
        });
        finish();


    }

    private Trip update() {
        trip.setName(nameText.getText().toString());
        trip.setRooms(Integer.valueOf(roomsText.getText().toString()));
        trip.setStatus(statusText.getText().toString());
        trip.setType(typeText.getText().toString());
        return trip;
    }

    private boolean fieldsValid() {
        if (isEmpty(nameText) || isEmpty(statusText) || isEmpty(typeText) || isEmpty(roomsText))
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

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }


}
