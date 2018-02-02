package com.bvd.android.agentie.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bvd.android.agentie.MyApp;
import com.bvd.android.agentie.R;
import com.bvd.android.agentie.dal.AppDatabase;
import com.bvd.android.agentie.dal.ItemDao;
import com.bvd.android.agentie.model.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import timber.log.Timber;

public class CustomerMyItemsActivity extends AppCompatActivity {

    @BindView(R.id.myItemsList)
    public ListView itemsList;

    private AppDatabase appDatabase;
    private ItemDao dao;
    private List<Item> items;
    private CustomerMyItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_my_items);
        ButterKnife.bind(this);
        ((MyApp) getApplication()).getInjector().inject(this);
        Timber.v("on create");

        initDB();
        items = new ArrayList<>();
        refreshAdapter();


    }

    private void refreshAdapter() {
        items.clear();
        items.addAll(dao.getAll());

        adapter = new CustomerMyItemsAdapter(this, R.layout.list_item_layout_1, items);
        adapter.notifyDataSetChanged();
        itemsList.setAdapter(adapter);
    }

    private void initDB() {
        appDatabase = AppDatabase.getAppDatabase(this);
        dao = appDatabase.getDAO();
    }

    @OnItemClick(R.id.myItemsList)
    public void redirectToMyResercedCancel(AdapterView<?> adapterView, View view, int i) {
        Item item = adapter.getItem(i);
        Intent intent = new Intent(view.getContext(), CustomerCancelReservationActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAdapter();

    }
}
