package com.youndevice.android.youndevice.activity;

import com.youndevice.android.youndevice.R;
import com.youndevice.android.youndevice.util.Preferences;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fist_name)
    TextView mFirstName;

    @BindView(R.id.last_name)
    TextView mLastName;

    @BindView(R.id.token)
    TextView mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if(Preferences.of(getApplicationContext()).authenticated().get() == false) {
            this.finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else {

            mFirstName.setText(Preferences.of(getApplicationContext()).firstName().get());
            mLastName.setText(Preferences.of(getApplicationContext()).lastName().get());
            mToken.setText(Preferences.of(getApplicationContext()).token().get());
        }

    }

    @OnClick(R.id.button_logout)
    public void logout() {
        Preferences.of(getApplicationContext()).authenticated().set(false);
        this.finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}
