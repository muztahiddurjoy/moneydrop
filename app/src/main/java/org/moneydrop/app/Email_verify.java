package org.moneydrop.app;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.moneydrop.app.DataClasses.EmailSenderDataset;
import org.moneydrop.app.databinding.ActivityEmailVerifyBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Email_verify extends AppCompatActivity {
    ActivityEmailVerifyBinding binding;
    JsonPlaceHolder jsonPlaceHolder;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        retrofit = new Retrofit.Builder()
                .baseUrl("localhost:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        sendEmail();
        Bundle extras = getIntent().getExtras();
        binding.verifyButton.setOnClickListener((v)->{
            String code = extras.getString("verifynunu");
            if (binding.verifyCode.getText().toString().equals(code)){
                Snackbar.make(v,"Worked", BaseTransientBottomBar.LENGTH_LONG).show();
            }
            else if(binding.verifyCode.getText().toString().isEmpty()){
                Snackbar.make(v,"Please enter the code", BaseTransientBottomBar.LENGTH_LONG).show();
            }
            else {
                Snackbar.make(v,"Wrong code!", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
    }

    private void sendEmail() {
        EmailSenderDataset dataset = new EmailSenderDataset(
                "muztahiddurjoy99@gmail.com",
                "noreply@moneydrop.org",
                "Email verify test",
                "Hello",
                "<h1>Hello Email </h1>");
        Call<EmailSenderDataset> call = jsonPlaceHolder.sendEmail(dataset);
        call.enqueue(new Callback<EmailSenderDataset>() {
            @Override
            public void onResponse(Call<EmailSenderDataset> call, Response<EmailSenderDataset> response) {
                Toast.makeText(Email_verify.this, response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<EmailSenderDataset> call, Throwable t) {
                Toast.makeText(Email_verify.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}