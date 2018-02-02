package com.bvd.android.agentie.rest;

import com.bvd.android.agentie.model.Trip;

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
    Call<List<Trip>> getAll();

    @POST("add")
    Call<Trip> addItem(@Body Trip item);

    @POST("update")
    Call<Trip> updateItem(@Body Trip item);

    @DELETE("del/{id}")
    Call<Void> deleteItem(@Path("id") Integer id);

}
