package com.bvd.android.agentie;

import android.app.Application;

import com.bvd.android.agentie.dagger.AppComponent;
import com.bvd.android.agentie.dagger.AppModule;
import com.bvd.android.agentie.dagger.DaggerAppComponent;

import timber.log.Timber;

/**
 * Created by bara on 2/1/2018.
 */

public class MyApp extends Application {
    private static final String TAG = MyApp.class.getName();

    public AppComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();
        injector = initDagger(this);
        Timber.plant(new Timber.DebugTree());
    }

    public AppComponent getInjector() {
        return injector;
    }

    public static String getTAG() {
        return TAG;
    }

    protected AppComponent initDagger(MyApp application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }
}
