package org.moneydrop.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.moneydrop.app.databinding.ActivitySigninBinding;

import java.util.Objects;

public class SigninActivity extends AppCompatActivity {
ActivitySigninBinding binding;
private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.signinBtn.setOnClickListener((v)->{
            if (binding.signinEmail.getText().toString().isEmpty() && binding.signinPassword.getText().toString().isEmpty()){
                Snackbar.make(v, "Please fill the fields", BaseTransientBottomBar.LENGTH_LONG).show();
            }
            else {
                auth.signInWithEmailAndPassword(binding.signinEmail.getText().toString(), binding.signinPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(SigninActivity.this, MainActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(v, Objects.requireNonNull(e.getMessage()), BaseTransientBottomBar.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}