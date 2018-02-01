package com.bvd.android.agentie.employee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bvd.android.agentie.MyApp;
import com.bvd.android.agentie.R;
import com.bvd.android.agentie.model.Trip;
import com.bvd.android.agentie.rest.TripController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class EmployeeActivity extends AppCompatActivity {

    private static final String TAG = EmployeeActivity.class.getName();
    @BindView(R.id.employeeTripList)
    public ListView tripsListView;
    private List<Trip> trips;
    private TripAdapter tripAdapter;
    @Inject
    public TripController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        ButterKnife.bind(this);
        ((MyApp) getApplication()).getInjector().inject(this);


        trips = new ArrayList<>();
        trips.add(new Trip(1, "name1", 2, "Double", "Available"));

        refreshAdapter();


    }

    private void refreshAdapter() {
        Call<List<Trip>> all = controller.getAll();
        all.enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                int code = response.code();
                Timber.v("Response code=" + code);
                if (code == 200) {
                    populateAdapter(response);
                } else {
                    Toast.makeText(EmployeeActivity.this, "Failed whit:" + code, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
                Toast.makeText(EmployeeActivity.this, "!!!Failed!!!", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void populateAdapter(Response<List<Trip>> response) {
        trips.clear();
        trips.addAll(response.body());
        tripAdapter = new TripAdapter(this, R.layout.list_item_layout_1, trips);
        tripsListView.setAdapter(tripAdapter);
    }

    @OnClick(R.id.employeeAddBtn)
    public void redirectToAddTrip() {
        Intent intent = new Intent(this, TripDetailsActivity.class);
        startActivity(intent);
    }

}
