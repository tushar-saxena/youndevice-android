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
package com.youndevice.android.youndevice.fragment;

import com.youndevice.android.youndevice.R;
import com.youndevice.android.youndevice.util.Preferences;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Settings fragment.
 *
 * Displays settings, at this point contains only the logging out action.
 */
public final class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {
    @BindString(R.string.settings_key_account_sign_out)
    String mAccountKey;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        setUpBindings();

        setUpSettings();

        setUpListeners();
    }

    private void setUpBindings() {
        ButterKnife.bind(this, getParentView());
    }

    private View getParentView() {
        return getActivity().getWindow().getDecorView();
    }

    private void setUpSettings() {
        addPreferencesFromResource(R.xml.settings);
    }

    private void setUpListeners() {
        findPreference(mAccountKey).setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (mAccountKey.equals(preference.getKey())) {
            tearDownAuthorization();
        }

        return true;
    }

    private void tearDownAuthorization() {
        Activity activity = getActivity();

        Preferences.of(activity).firstName().delete();
        Preferences.of(activity).lastName().delete();
        Preferences.of(activity).token().delete();
        Preferences.of(activity).authenticated().delete();
        Preferences.of(activity).email().delete();
        activity.setResult(Activity.RESULT_OK);
        activity.finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
