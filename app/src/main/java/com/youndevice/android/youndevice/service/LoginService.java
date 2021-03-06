package com.youndevice.android.youndevice.service;

import com.youndevice.android.youndevice.backend.model.AuthResponse;
import com.youndevice.android.youndevice.backend.model.Credential;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by anuj on 24/4/17.
 */

public interface LoginService {

    @POST("api/v1/login")
    Call<AuthResponse> login(@Body Credential credential);

}
