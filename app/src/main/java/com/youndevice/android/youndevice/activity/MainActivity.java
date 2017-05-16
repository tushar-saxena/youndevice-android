package com.youndevice.android.youndevice.activity;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.youndevice.android.youndevice.R;
import com.youndevice.android.youndevice.util.Intents;
import com.youndevice.android.youndevice.util.Preferences;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer)
    DrawerLayout mDrawer;

    @BindView(R.id.navigation)
    NavigationView mNavigation;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.title)
    TextView mTitle;

    ImageView mProfileImage;

    TextView mName;

    TextView mEmail;

    @IdRes
    int currentNavigationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // -- Bind view
        ButterKnife.bind(this);

        View headerView = mNavigation.getHeaderView(0);
        mProfileImage = ButterKnife.findById(headerView, R.id.profile_image);
        mName = ButterKnife.findById(headerView, R.id.text_user_name);
        mEmail = ButterKnife.findById(headerView, R.id.text_email);

        // -- Toolbar

        setSupportActionBar(mToolbar);

        // -- Navigation
        mNavigation.setNavigationItemSelectedListener(this);


        if(FirebaseAuth.getInstance() == null) {
            this.finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else {
            setupDrawer();
        }

    }

    private void setupDrawer() {
        Picasso.with(getApplicationContext())
                .load(Preferences.of(getApplicationContext()).profile().get()).into(mProfileImage);
        mName.setText(Preferences.of(getApplicationContext()).name().get());
        mEmail.setText(Preferences.of(getApplicationContext()).email().get());
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

        mDrawer.closeDrawers();

        return true;

    }


    // -- Navigation ------------------------------------------------------------------------------

    @OnClick(R.id.drawer_menu_icon)
    void openDrawerMenu() {
        mDrawer.openDrawer(GravityCompat.START);
    }

    private void showNavigation(@IdRes int navigationId) {
        mNavigation.getMenu().findItem(navigationId).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
            return;
        }
    }
}
