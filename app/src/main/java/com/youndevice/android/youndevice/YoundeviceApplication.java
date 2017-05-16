package com.youndevice.android.youndevice;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by anuj on 24/4/17.
 */

public class YoundeviceApplication extends Application implements FirebaseAuth.AuthStateListener {

    public static boolean isAuthenticated;

    public static Retrofit retrofit;
    @Override
    public void onCreate() {
        super.onCreate();

        retrofit= new Retrofit.Builder()
                 .baseUrl("http://api.youndevice.com/")
                 .addConverterFactory(MoshiConverterFactory.create())
                 .build();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(this);
        FirebaseUser user = auth.getCurrentUser();

        setUpUser(user);
    }

    private void setUpUser(FirebaseUser user) {

        if (user == null) {
            isAuthenticated = false;
        } else {
            isAuthenticated = true;
            User.getInstance().setUp(user);

            if(user.getDisplayName() == null) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(user.getEmail().split("@")[0])
                        .build();
                user.updateProfile(profileUpdates);
            }

        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        setUpUser(user);
    }

    public static class User implements OnCompleteListener<GetTokenResult> {

        public static String uid;
        public static String name;
        public static String email;
        public static String profile;
        public static String token;

        private static User user;

        private User(){

        }

        public static User getInstance(){
            if(user == null){
                user = new User();
            }

            return user;
        }

        public void setUp(FirebaseUser user){
            uid = user.getUid();
            name = user.getDisplayName();
            email = user.getEmail();
            profile = user.getPhotoUrl().toString();
            user.getToken(true).addOnCompleteListener(this);
        }

        @Override public void onComplete(@NonNull Task<GetTokenResult> task) {
            if(task.isSuccessful()){
                token = task.getResult().getToken();
            }
        }
    }


}
