/*
 * Copyright 2015-2017 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.youndevice.android.youndevice.backend;

import static com.youndevice.android.youndevice.YoundeviceApplication.retrofit;

import com.youndevice.android.youndevice.service.DeviceService;

import android.Manifest;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.Fragment;
import retrofit2.Callback;

public final class BackendClient {
    private final Activity activity;
    private final Fragment fragment;

    @NonNull
    @RequiresPermission(Manifest.permission.INTERNET)
    public static BackendClient of(@NonNull Activity activity) {
        return new BackendClient(activity, null);
    }

    @NonNull
    @RequiresPermission(Manifest.permission.INTERNET)
    public static BackendClient of(@NonNull Fragment fragment) {
        return new BackendClient(null, fragment);
    }

    private BackendClient(Activity activity, Fragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
    }

    public void getDevices(Callback callback) {
        DeviceService service = retrofit.create(DeviceService.class);
        service.getDevices().enqueue(callback);
    }
}