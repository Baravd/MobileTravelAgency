package com.bvd.android.agentie.employee;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bvd.android.agentie.MyApp;
import com.bvd.android.agentie.R;
import com.bvd.android.agentie.model.Trip;
import com.bvd.android.agentie.rest.TripController;
import com.bvd.android.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class EmployeeActivity extends AppCompatActivity {

    private static final String TAG = EmployeeActivity.class.getName();
    @BindView(R.id.employeeTripList)
    public ListView tripsListView;
    @BindView(R.id.employeeProgressBar)
    public ProgressBar progressBar;

    @BindView(R.id.employeeAddBtn)
    public Button addBtn;

    private List<Trip> trips;
    private TripAdapter tripAdapter;

    @Inject
    public TripController controller;
    @Inject
    public NetworkUtils networkUtils;

    private Integer retryCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        ButterKnife.bind(this);
        ((MyApp) getApplication()).getInjector().inject(this);
        Timber.v("on create");
        retryCount = 3;


        trips = new ArrayList<>();
        trips.add(new Trip(1, "name1", 2, "Double", "Available"));

        refreshAdapter();


    }

    private void refreshAdapter() {
        progressBar.setVisibility(View.VISIBLE);
        addBtn.setVisibility(View.INVISIBLE);


        Call<List<Trip>> all = controller.getAll();
        all.enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                int code = response.code();
                Timber.v("Response code=%s", code);
                if (code == 200) {
                    populateAdapter(response);
                    Toast.makeText(EmployeeActivity.this, "Successful Refresh", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(EmployeeActivity.this, "Failed whit:" + code, Toast.LENGTH_SHORT).show();
                    addBtn.setEnabled(false);
                }
                progressBar.setVisibility(View.GONE);
                addBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
                Toast.makeText(EmployeeActivity.this, "!!!Failed!!!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                retryDialog();
            }
        });

    }

    private void retryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Can't reach server");
        builder.setMessage(" Do you want to retry?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Retry calll if yes clicked and not more than 3 times

                dialog.dismiss();
                if (retryCount > 0) {
                    retryCount--;
                    refreshAdapter();

                } else {
                    Toast.makeText(EmployeeActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // exit
                dialog.dismiss();
                finish();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }

    private void populateAdapter(Response<List<Trip>> response) {
        trips.clear();
        trips.addAll(response.body());
        tripAdapter = new TripAdapter(this, R.layout.list_item_layout_1, trips);
        tripsListView.setAdapter(tripAdapter);
    }

    @OnClick(R.id.employeeAddBtn)
    public void redirectToAddTrip() {
        Intent intent = new Intent(this, AddTripActivity.class);
        startActivity(intent);
    }

    @OnItemClick(R.id.employeeTripList)
    public void redirectToDetails(AdapterView<?> adapterView, View view, int i) {
        Trip trip = tripAdapter.getItem(i);
        Intent intent = new Intent(view.getContext(), TripDetailsActivity.class);
        intent.putExtra("item", trip);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAdapter();
    }
}
