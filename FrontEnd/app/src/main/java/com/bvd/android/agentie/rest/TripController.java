package com.bvd.android.agentie.rest;

import com.bvd.android.agentie.model.Trip;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by bara on 2/1/2018.
 */

public interface TripController {
    @GET("all")
    Call<List<Trip>> getAll();
}
