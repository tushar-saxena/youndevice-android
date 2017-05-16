package com.youndevice.android.youndevice.backend.model;

/**
 * Created by anuj on 24/4/17.
 */

public class AuthResponse {

    private Boolean status;

    private User data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
