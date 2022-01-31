package org.moneydrop.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.moneydrop.app.databinding.ActivityEmailVerifyBinding;

public class Email_verify extends AppCompatActivity {
    ActivityEmailVerifyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
            startActivity(new Intent(this,MainActivity.class));
        }
        binding.verifyButton.setOnClickListener(v->{
            binding.verifyButton.setEnabled(false);
            sendEmail();
        });
    }

    private void sendEmail(){
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        auth.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Verification email sent!", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Email_verify.this,SigninActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
            startActivity(new Intent(this,MainActivity.class));
        }
    }

}