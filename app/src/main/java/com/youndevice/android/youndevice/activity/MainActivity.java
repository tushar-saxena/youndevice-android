package com.youndevice.android.youndevice.activity;

import com.youndevice.android.youndevice.R;
import com.youndevice.android.youndevice.util.Intents;
import com.youndevice.android.youndevice.util.Preferences;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer)
    DrawerLayout drawer;

    @BindView(R.id.navigation)
    NavigationView navigation;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.title)
    TextView title;

    @IdRes
    int currentNavigationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // -- Bind view
        ButterKnife.bind(this);

        // -- Toolbar

        setSupportActionBar(toolbar);

        // -- Navigation
        navigation.setNavigationItemSelectedListener(this);


        if(Preferences.of(getApplicationContext()).authenticated().get() == false) {
            this.finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else {

        }

    }

    @Override
    protected void onActivityResult(int request, int result, Intent intent) {
        super.onActivityResult(request, result, intent);

        if (request == Intents.Requests.DEAUTHORIZATION) {
            if (result == Activity.RESULT_OK) {
                if(Preferences.of(getApplicationContext()).authenticated().get() == false) {
                    this.finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        }
    }

    // -- NavigationView.OnNavigationItemSelectedListener -----------------------------------------

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_settings:
                Log.d("Crater", "Inside");
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(settingsIntent, Intents.Requests.DEAUTHORIZATION);
                break;

            default:

                Log.d("Crater", "Outside");
                break;
        }

        currentNavigationId = menuItem.getItemId();

        drawer.closeDrawers();

        return true;

    }


    // -- Navigation ------------------------------------------------------------------------------

    @OnClick(R.id.drawer_menu_icon)
    void openDrawerMenu() {
        drawer.openDrawer(GravityCompat.START);
    }

    private void showNavigation(@IdRes int navigationId) {
        navigation.getMenu().findItem(navigationId).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
    }
}
