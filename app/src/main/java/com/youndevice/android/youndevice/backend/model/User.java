package com.youndevice.android.youndevice.backend.model;

import com.squareup.moshi.Json;

/**
 * Created by anuj on 24/4/17.
 */

public class User {

    private String firstName;

    private String lastName;

    @Json(name = "X-AUTH-TOKEN")
    private String token;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
