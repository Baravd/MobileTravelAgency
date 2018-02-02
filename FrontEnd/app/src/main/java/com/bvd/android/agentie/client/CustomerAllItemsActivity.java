package com.bvd.android.agentie.client;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bvd.android.agentie.MyApp;
import com.bvd.android.agentie.R;
import com.bvd.android.agentie.model.Item;
import com.bvd.android.agentie.rest.TripController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class CustomerAllItemsActivity extends AppCompatActivity {

    @BindView(R.id.customerAllItemsList)
    public ListView allItemsList;

    @BindView(R.id.customerProgressBar)
    public ProgressBar progressBar;


    @Inject
    public TripController controller;

    private List<Item> items;
    private CustomerAllItemsAdapter adapter;
    private int retryCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_all_items);
        ButterKnife.bind(this);
        ((MyApp) getApplication()).getInjector().inject(this);
        Timber.v("on create");
        items = new ArrayList<>();


        refreshAdapter();


    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAdapter();
    }


    private void refreshAdapter() {
        progressBar.setVisibility(View.VISIBLE);


        Call<List<Item>> all = controller.getAll();
        all.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                int code = response.code();
                Timber.v("Response code=%s", code);
                if (code == 200) {
                    populateAdapter(response);
                    Toast.makeText(CustomerAllItemsActivity.this, "Successful Refresh", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CustomerAllItemsActivity.this, "Failed whit:" + code, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(CustomerAllItemsActivity.this, "!!!Failed!!!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(CustomerAllItemsActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();
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

    private void populateAdapter(Response<List<Item>> response) {
        items.clear();
        items.addAll(response.body());
        adapter = new CustomerAllItemsAdapter(this, R.layout.list_item_layout_1, items);
        allItemsList.setAdapter(adapter);
    }

    @OnItemClick(R.id.customerAllItemsList)
    public void redirectToBookView(AdapterView<?> adapterView, View view, int i) {
        Item item = adapter.getItem(i);
        Intent intent = new Intent(view.getContext(), CustomerReserveActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}
