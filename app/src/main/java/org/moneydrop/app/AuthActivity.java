package org.moneydrop.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.moneydrop.app.DataClasses.UserDatasetClass;
import org.moneydrop.app.databinding.ActivityAuthBinding;

import java.util.Objects;


public class AuthActivity extends AppCompatActivity {
    private ActivityAuthBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.signupName.getText().toString().isEmpty()){
                    Snackbar.make(view,"Please Enter your name", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                else if (binding.signupEmail.getText().toString().isEmpty()){
                    Snackbar.make(view,"Please Enter your email address", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                else if (binding.signupPass.getText().toString().isEmpty()){
                    Snackbar.make(view,"Please Enter a password", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                else if (binding.signupPhone.getText().toString().isEmpty()){
                    Snackbar.make(view,"Please Enter a phone number", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                else {
                    auth.createUserWithEmailAndPassword(binding.signupEmail.getText().toString(),binding.signupPass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            reference.child(authResult.getUser().getUid()).setValue(
                                    new UserDatasetClass(
                                            binding.signupName.getText().toString(),
                                            binding.signupEmail.getText().toString(),
                                            binding.signupPhone.getText().toString(),
                                            "0",
                                            "false"
                                    )).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(AuthActivity.this,MainActivity.class));
                                    Toast.makeText(AuthActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Snackbar.make(view, Objects.requireNonNull(e.getMessage()), BaseTransientBottomBar.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }
        });
        binding.buttonSignin.setOnClickListener((v)->{
            startActivity(new Intent(AuthActivity.this,SigninActivity.class));
        });
    }
}