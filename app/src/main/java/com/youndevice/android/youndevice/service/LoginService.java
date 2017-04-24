package com.youndevice.android.youndevice.service;

import com.youndevice.android.youndevice.backend.Model.AuthResponse;
import com.youndevice.android.youndevice.backend.Model.Credential;
import com.youndevice.android.youndevice.backend.Model.User;

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
