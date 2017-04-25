/*
 * Copyright 2015-2016 Red Hat, Inc. and/or its affiliates
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
package com.youndevice.android.youndevice.activity;

import com.youndevice.android.youndevice.R;
import com.youndevice.android.youndevice.fragment.SettingsFragment;
import com.youndevice.android.youndevice.util.Fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;


public final class SettingsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_container);

        setUpBindings();

        setUpToolbar();

        setUpSettings();
    }

    private void setUpBindings() {
        ButterKnife.bind(this);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() == null) {
            return;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpSettings() {
        Fragments.Operator.of(this).set(R.id.layout_container, new SettingsFragment());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
