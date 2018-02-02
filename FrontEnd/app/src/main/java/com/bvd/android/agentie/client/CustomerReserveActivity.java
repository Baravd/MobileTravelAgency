package com.bvd.android.agentie.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bvd.android.agentie.MyApp;
import com.bvd.android.agentie.R;
import com.bvd.android.agentie.dal.AppDatabase;
import com.bvd.android.agentie.dal.ItemDao;
import com.bvd.android.agentie.employee.TripDetailsActivity;
import com.bvd.android.agentie.model.Item;
import com.bvd.android.agentie.model.dtos.TripBookDto;
import com.bvd.android.agentie.rest.TripController;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class CustomerReserveActivity extends AppCompatActivity {

    @BindView(R.id.customerReserverRooms)
    public EditText roomsText;

    @BindView(R.id.customerReserveProgressBar)
    public ProgressBar progressBar;

    @BindView(R.id.customerReserveBtn)
    public Button reserveBtn;

    @BindView(R.id.customerReserveDeleteBtn)
    public Button deleteBtn;

    @Inject
    public TripController controller;

    private Item item;
    private List<EditText> allEditTexts;
    private List<Button> allButtons;
    private List<EditText> checkEditTexts;
    private AppDatabase appDatabase;
    private ItemDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add);
        ButterKnife.bind(this);
        ((MyApp) getApplication()).getInjector().inject(this);
        Timber.v("on create");


        item = (Item) getIntent().getExtras().getSerializable("item");
        allEditTexts = Arrays.asList(roomsText);
        allButtons = Arrays.asList(reserveBtn, deleteBtn);
        checkEditTexts = Arrays.asList(roomsText);
        initDB();

        if (item == null) {
            Timber.v("Didn't receive the object from previous view");
            finish();
        } else {
            Timber.v("Received item=%s", item);
            makeEverythingVisibleAndHideProgress(progressBar, allEditTexts, allButtons);

        }


    }

    @OnClick(R.id.customerReserveBtn)
    public void reserve() {
        if (!fieldsValid(allEditTexts, checkEditTexts)) {
            Toast.makeText(CustomerReserveActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();
            return;
        }
        int nrRooms = Integer.valueOf(roomsText.getText().toString());
        makeEverythingInvisibleAndShowProgress(progressBar, allEditTexts, allButtons);
        TripBookDto tripBookDto = new TripBookDto(item.getId(), nrRooms);
        Call<Item> tripCall = controller.acquireItem(tripBookDto);
        tripCall.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                int code = response.code();
                Timber.v("Response code=%s and body=%s", code, response.body());
                if (code == 200) {
                    Toast.makeText(CustomerReserveActivity.this, "Successful Reservation", Toast.LENGTH_SHORT).show();
                    saveToDb(response.body());
                    finish();

                } else {
                    Toast.makeText(CustomerReserveActivity.this, "Failed whit:" + code, Toast.LENGTH_SHORT).show();
                }
                makeEverythingVisibleAndHideProgress(progressBar, allEditTexts, allButtons);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Toast.makeText(CustomerReserveActivity.this, "!!!Failed!!!", Toast.LENGTH_SHORT).show();
                makeEverythingVisibleAndHideProgress(progressBar, allEditTexts, allButtons);

            }
        });
        finish();

    }

    @OnClick(R.id.customerReserveDeleteBtn)
    void removeItem() {
        makeEverythingInvisibleAndShowProgress(progressBar, allEditTexts, allButtons);
        Call<Void> deleteItemCall = controller.deleteItem(item.getId());


        deleteItemCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                Timber.v("Response code=%s and body=" + response.body(), code);
                if (code == 200) {
                    Toast.makeText(CustomerReserveActivity.this, "Successful Delete", Toast.LENGTH_SHORT).show();
                    removeFromDb(item);
                    finish();

                } else {
                    Toast.makeText(CustomerReserveActivity.this, "Failed whit:" + code, Toast.LENGTH_SHORT).show();
                }
                makeEverythingVisibleAndHideProgress(progressBar, allEditTexts, allButtons);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CustomerReserveActivity.this, "!!!Failed!!!", Toast.LENGTH_SHORT).show();
                makeEverythingVisibleAndHideProgress(progressBar, allEditTexts, allButtons);

            }
        });
        finish();

    }

    private void removeFromDb(Item item) {
        dao.delete(item);
    }

    private void saveToDb(Item body) {
        dao.insertAll(body);
    }

    //utils
    public void makeEverythingInvisibleAndShowProgress(ProgressBar progressBar, List<EditText> editTexts, List<Button> buttons) {
        progressBar.setVisibility(View.VISIBLE);
        for (EditText text : editTexts) {
            text.setVisibility(View.INVISIBLE);

        }
        setButtonsVisibility(buttons, View.INVISIBLE);
    }

    private void setButtonsVisibility(List<Button> buttons, int visibility) {
        for (Button button : buttons) {
            button.setVisibility(visibility);
        }
    }

    public void makeEverythingVisibleAndHideProgress(ProgressBar progressBar, List<EditText> editTexts, List<Button> buttons) {
        progressBar.setVisibility(View.INVISIBLE);
        for (EditText text : editTexts) {
            text.setVisibility(View.VISIBLE);
        }
        setButtonsVisibility(buttons, View.VISIBLE);

    }

    private boolean fieldsNotEmpty(List<EditText> texts) {
        for (EditText text : texts) {
            if (isEmpty(text)) {
                return false;
            }
        }
        return true;

    }

    private boolean fieldsGreaterThan(Integer value, List<EditText> texts) {
        for (EditText text : texts) {
            if (Integer.parseInt(String.valueOf(text.getText())) <= value) {
                return false;
            }
        }
        return true;
    }

    public boolean fieldsValid(List<EditText> checkEmpty, List<EditText> checkGreaterThanZero) {
        return fieldsGreaterThan(0, checkGreaterThanZero) && fieldsNotEmpty(checkEmpty);
    }

    public boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void initDB() {
        appDatabase = AppDatabase.getAppDatabase(this);
        dao = appDatabase.getDAO();
    }


}
