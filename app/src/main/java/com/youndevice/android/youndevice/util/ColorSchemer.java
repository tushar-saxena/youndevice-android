
package com.youndevice.android.youndevice.util;

import com.youndevice.android.youndevice.R;

import android.support.annotation.NonNull;

/**
 * Color schemer.
 *
 * Provides a default color scheme, especially useful
 * for {@link android.support.v4.widget.SwipeRefreshLayout} configuration.
 */
public final class ColorSchemer {
    private ColorSchemer() {
    }

    @NonNull
    public static int[] getScheme() {
        return new int[]{
                R.color.colorPrimary,
                R.color.colorAccent
        };
    }
}
