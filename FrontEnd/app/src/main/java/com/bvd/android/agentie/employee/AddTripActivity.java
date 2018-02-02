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
import com.bvd.android.agentie.model.Item;
import com.bvd.android.agentie.rest.TripController;
import com.bvd.android.utils.NetworkUtils;

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

public class AddTripActivity extends AppCompatActivity {


    @Inject
    public NetworkUtils networkUtils;

    @Inject
    public TripController controller;


    @BindView(R.id.addStatusText)
    public EditText statusText;

    @BindView(R.id.addTypeText)
    public EditText typeText;

    @BindView(R.id.addRoomsText)
    public EditText roomsText;

    @BindView(R.id.addNameText)
    public EditText nameText;

    @BindView(R.id.addProgrssBar)
    public ProgressBar progressBar;

    @BindView(R.id.addAddBtn)
    public Button addBtn;


    private List<EditText> allEditTexts;
    private List<EditText> checkGreater;
    private List<Button> allButtons;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        ButterKnife.bind(this);
        ((MyApp) getApplication()).getInjector().inject(this);

        allEditTexts = Arrays.asList(typeText, roomsText, nameText, statusText);
        checkGreater = Arrays.asList(roomsText);
        allButtons = Arrays.asList(addBtn);

        makeEverythingVisibleAndHideProgress(progressBar, allEditTexts, allButtons);


    }

    @OnClick(R.id.addAddBtn)
    void addItem() {
        if (!fieldsValid(allEditTexts, checkGreater)) {
            Toast.makeText(AddTripActivity.this, "Check Input", Toast.LENGTH_SHORT).show();
            return;
        }

        makeEverythingInvisibleAndShowProgress(progressBar, allEditTexts, allButtons);
        item = new Item(
                nameText.getText().toString(),
                Integer.valueOf(roomsText.getText().toString()),
                typeText.getText().toString(),
                statusText.getText().toString());


        Call<Item> addItemCall = controller.addItem(item);
        addItemCall.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                int code = response.code();
                Timber.v("Response code=%s and body=%s", code, response.body());
                if (code == 200) {
                    Toast.makeText(AddTripActivity.this, "Successful Update", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(AddTripActivity.this, "Failed whit:" + code, Toast.LENGTH_SHORT).show();
                }
                makeEverythingVisibleAndHideProgress(progressBar, allEditTexts, allButtons);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Toast.makeText(AddTripActivity.this, "!!!Failed!!!", Toast.LENGTH_SHORT).show();
                makeEverythingVisibleAndHideProgress(progressBar, allEditTexts, allButtons);

            }
        });
        finish();


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



}
