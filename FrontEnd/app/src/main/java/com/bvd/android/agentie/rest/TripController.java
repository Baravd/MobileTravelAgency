package com.bvd.android.agentie.rest;

import com.bvd.android.agentie.model.Item;
import com.bvd.android.agentie.model.dtos.TripBookDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by bara on 2/1/2018.
 */

public interface TripController {
    @GET("all")
    Call<List<Item>> getAll();

    @POST("add")
    Call<Item> addItem(@Body Item item);

    @POST("update")
    Call<Item> updateItem(@Body Item item);

    @DELETE("del/{id}")
    Call<Void> deleteItem(@Path("id") Integer id);

    @GET("trips")
    Call<List<Item>> getAllCustomer();

    @POST("book")
    Call<Item> acquireItem(@Body TripBookDto dto);

    @DELETE("cancel")
    Call<Void> returnItem(@Body Integer id);


}
