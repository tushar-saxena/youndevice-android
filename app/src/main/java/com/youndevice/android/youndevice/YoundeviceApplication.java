package com.youndevice.android.youndevice;

import android.app.Application;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by anuj on 24/4/17.
 */

public class YoundeviceApplication extends Application {

    public static Retrofit retrofit;
    @Override
    public void onCreate() {
        super.onCreate();

         retrofit= new Retrofit.Builder()
                 .baseUrl("http://api.youndevice.com/")
                 .addConverterFactory(MoshiConverterFactory.create())
                 .build();
    }

}
