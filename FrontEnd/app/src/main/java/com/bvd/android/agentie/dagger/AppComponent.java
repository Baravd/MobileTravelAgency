package com.bvd.android.agentie.dagger;

import com.bvd.android.agentie.MainActivity;
import com.bvd.android.agentie.MyApp;
import com.bvd.android.agentie.client.CustomerAllItemsActivity;
import com.bvd.android.agentie.client.CustomerCancelReservationActivity;
import com.bvd.android.agentie.client.CustomerMyItemsActivity;
import com.bvd.android.agentie.client.CustomerReserveActivity;
import com.bvd.android.agentie.employee.AddTripActivity;
import com.bvd.android.agentie.employee.EmployeeActivity;
import com.bvd.android.agentie.employee.TripDetailsActivity;

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

    void inject(TripDetailsActivity target);

    void inject(AddTripActivity target);

    void inject(MainActivity target);

    void inject(CustomerMyItemsActivity target);

    void inject(CustomerAllItemsActivity target);

    void inject(CustomerReserveActivity target);

    void inject(CustomerCancelReservationActivity  target);

}

