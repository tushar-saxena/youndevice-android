package com.youndevice.android.youndevice.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import info.metadude.android.typedpreferences.BooleanPreference;
import info.metadude.android.typedpreferences.StringPreference;

/**
 * Preference utilities.
 *
 * Configuration storage for simple objects, such as text and numbers.
 */
public final class Preferences {
    private static final class Locations {
        private Locations() {
        }

        public static final String BACKEND = "backend";
    }

    public static final class Keys {
        private Keys() {
        }

        public static final String BACKEND_FIRST_NAME = "firstName";
        public static final String BACKEND_LAST_NAME = "lastName";
        public static final String BACKEND_TOKEN = "token";
        public static final String BACKEND_AUTHENTICATED = "authenticated";

    }

    public static final class Defaults {
        private Defaults() {
        }
    }

    private final SharedPreferences serverPreferences;

    @NonNull
    public static Preferences of(@NonNull Context context) {
        return new Preferences(context, Locations.BACKEND);
    }

    private Preferences(Context context, String preferencesLocation) {
        this.serverPreferences = context.getSharedPreferences(preferencesLocation, Context.MODE_PRIVATE);
    }

    @NonNull
    public StringPreference firstName() {
        return new StringPreference(serverPreferences, Keys.BACKEND_FIRST_NAME);
    }

    @NonNull
    public StringPreference lastName() {
        return new StringPreference(serverPreferences, Keys.BACKEND_LAST_NAME);
    }

    @NonNull
    public StringPreference token() {
        return new StringPreference(serverPreferences, Keys.BACKEND_TOKEN);
    }

    @NonNull
    public BooleanPreference authenticated() {
        return new BooleanPreference(serverPreferences, Keys.BACKEND_AUTHENTICATED);
    }

}
