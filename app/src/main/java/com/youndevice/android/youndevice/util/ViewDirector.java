package com.youndevice.android.youndevice.util;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ViewAnimator;

/**
 * View director.
 *
 * Makes {@link ViewAnimator} interaction better, automatically casting all necessary views
 * and changing them only if necessary.
 *
 * It is necessary to call the {@link #using(int)} method before {@link #show(int)}.
 */
public final class ViewDirector {
    private final View sceneView;

    private int animatorId;

    @NonNull
    public static ViewDirector of(@NonNull Activity activity) {
        return new ViewDirector(activity.getWindow().getDecorView());
    }

    @NonNull
    public static ViewDirector of(@NonNull Fragment fragment) {
        return new ViewDirector(fragment.getView());
    }

    private ViewDirector(View sceneView) {
        this.sceneView = sceneView;
    }

    @NonNull
    public ViewDirector using(@IdRes int animatorId) {
        this.animatorId = animatorId;

        return this;
    }

    @UiThread
    public void show(@IdRes int viewId) {
        ViewAnimator animator = (ViewAnimator) sceneView.findViewById(animatorId);
        View view = sceneView.findViewById(viewId);

        if (animator.getDisplayedChild() != animator.indexOfChild(view)) {
            animator.setDisplayedChild(animator.indexOfChild(view));
        }
    }
}