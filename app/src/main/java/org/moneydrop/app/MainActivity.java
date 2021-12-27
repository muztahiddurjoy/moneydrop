package org.moneydrop.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import org.moneydrop.app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, new TaskActivity()).commit();
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile_fragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, new ProfileFragment()).commit();
                        break;
                    case R.id.tasks_fragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, new TaskActivity()).commit();
                        break;
                    case  R.id.withdraw_money:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, new WithdrawFragment()).commit();
                        break;
                    case R.id.earn_coin:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, new AdsFragment()).commit();
                        break;
                    case R.id.spin_ad:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, new SpinFragment()).commit();
                        break;
                }
                return true;
            }
        });

        StartAppSDK.setUserConsent (this,
                "pas",
                System.currentTimeMillis(),
                true);
        StartAppSDK.init(this, "211527131", false);
        StartAppAd.showAd(this);
        StartAppAd.enableAutoInterstitial();
      //  StartAppSDK.setTestAdsEnabled(true);
      //  StartAppAd.showSplash(MainActivity.this,savedInstanceState,);
    }

    @Override
    public void onBackPressed() {
        StartAppAd.showAd(this);
        StartAppAd.onBackPressed(this);
        super.onBackPressed();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this,AuthActivity.class));
    }
}