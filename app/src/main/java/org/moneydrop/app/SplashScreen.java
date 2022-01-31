package org.moneydrop.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

import org.moneydrop.app.DataClasses.NetworkDataset;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreen extends Activity {
    private FirebaseAuth auth;
    private JsonPlaceHolder jsonPlaceHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://geolocation-db.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<NetworkDataset> data = jsonPlaceHolder.getNetWorks();
        data.enqueue(new Callback<NetworkDataset>() {
            @Override
            public void onResponse(Call<NetworkDataset> call, Response<NetworkDataset> response) {
                if (response.isSuccessful()){
                    NetworkDataset res = response.body();
                    String country = res.getCountry_code();
                    if (!country.equals("US")){
                        startActivity(new Intent(SplashScreen.this, ConnectVPNAndroid.class));
                    }
                    else {
                        if (auth.getCurrentUser() != null){

                            if (auth.getCurrentUser().isEmailVerified()){
                                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                            }
                            else {

                                finish();
                                startActivity(new Intent(SplashScreen.this,Email_verify.class));
                            }
                        }
                        else {
                            startActivity(new Intent(SplashScreen.this,AuthActivity.class));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NetworkDataset> call, Throwable t) {
                Toast.makeText(SplashScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
