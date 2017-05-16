package com.youndevice.android.youndevice.service;

import java.util.List;

import com.youndevice.android.youndevice.backend.model.AuthResponse;
import com.youndevice.android.youndevice.backend.model.Credential;
import com.youndevice.android.youndevice.backend.model.Device;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by anuj on 24/4/17.
 */

public interface DeviceService {

    @GET("api/v1/devices")
    Call<List<Device>> getDevices();
}
