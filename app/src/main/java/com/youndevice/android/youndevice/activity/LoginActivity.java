package com.youndevice.android.youndevice.activity;

import static com.youndevice.android.youndevice.YoundeviceApplication.retrofit;

import com.youndevice.android.youndevice.BuildConfig;
import com.youndevice.android.youndevice.R;
import com.youndevice.android.youndevice.backend.Model.AuthResponse;
import com.youndevice.android.youndevice.backend.Model.Credential;
import com.youndevice.android.youndevice.backend.Model.User;
import com.youndevice.android.youndevice.service.LoginService;
import com.youndevice.android.youndevice.util.Preferences;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.username)
    EditText mUsername;

    @BindView(R.id.password)
    EditText mPassword;

    Activity activity;

    private ProgressDialog authIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        activity = this;

        setSupportActionBar(mToolbar);

        if (BuildConfig.DEBUG) {
            mUsername.setText("tushar.saxena@tothenew.com");
            mPassword.setText("tushar");
        }
    }


    @OnClick(R.id.button_login)
    public void login() {

        Credential credential = new Credential();
        credential.setEmailId(mUsername.getText().toString());
        credential.setPassword(mPassword.getText().toString());
        LoginService service = retrofit.create(LoginService.class);

        Call call = service.login(credential);

        call.enqueue(new Callback<AuthResponse>() {
                         @Override
                         public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                             if (response.isSuccessful()) {
                                 User user = response.body().getData();
                                 Preferences.of(getApplicationContext()).firstName().set(user.getFirstName());
                                 Preferences.of(getApplicationContext()).lastName().set(user.getLastName());
                                 Preferences.of(getApplicationContext()).token().set(user.getToken());
                                 Preferences.of(getApplicationContext()).authenticated().set(true);

                                 startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                 activity.finish();

                                 Log.d("Success", "Success");
                             } else {
                                 Log.d("Success", "But no login");
                             }
                         }

                         @Override
                         public void onFailure(Call<AuthResponse> call, Throwable t) {
                             // something went completely south (like no internet connection)
                             Log.d("Error", t.getMessage());
                         }
                     });

    }

}
