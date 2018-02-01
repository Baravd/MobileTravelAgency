package com.bvd.android.agentie.dagger;

import com.bvd.android.agentie.MyApp;
import com.bvd.android.agentie.employee.EmployeeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by bara on 2/1/2018.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MyApp target);
    void inject(EmployeeActivity target);

}

