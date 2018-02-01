package com.bvd.android.agentie.dagger;

import android.content.Context;

import com.bvd.android.agentie.MyApp;
import com.bvd.android.agentie.model.Trip;
import com.bvd.android.agentie.rest.TripController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bara on 2/1/2018.
 */

@Module
public class AppModule {

    private static final String API_BASE_URL = "http://10.0.2.2:3200/";
    private MyApp app;

    public AppModule(MyApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return app;
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideLoggingIntercepto() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    public OkHttpClient.Builder provideHtppClient(HttpLoggingInterceptor interceptor) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);
        return httpClient;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient.Builder httpClient) {
        return new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    public TripController provideTripController(Retrofit retrofit) {
        return retrofit.create(TripController.class);
    }
}
