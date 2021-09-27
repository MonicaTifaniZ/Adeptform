package com.monicatifanyz.adeptforms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.monicatifanyz.adeptforms.api.ApiClient;
import com.monicatifanyz.adeptforms.api.LoginResponse;
import com.monicatifanyz.adeptforms.api.User;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    Button btnLogin;
    ImageView ivFingerprint;
    TextInputEditText edEmail, edPassword;
    CheckBox checkBoxSave;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnLogin = findViewById(R.id.button);
        edEmail = findViewById(R.id.editTextValueEmail);
        edPassword = findViewById(R.id.editTextValuePassword);
        checkBoxSave = findViewById(R.id.checkBoxSaveLogin);
        ivFingerprint = findViewById(R.id.imageViewFingerprint);

        executor = ContextCompat.getMainExecutor(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        ivFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                loginFingerPrint();
                biometricPrompt.authenticate(promptInfo);
            }
        });

    }

    public void loginFingerPrint(){
        biometricPrompt = new BiometricPrompt(SignInActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                Toast.makeText(SignInActivity.this, "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Intent i = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(i);

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

                Toast.makeText(SignInActivity.this, "Authentication failed...!", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Login using fingerprint authentification")
                .setNegativeButtonText("User App Password")
                .build();

    }

    public  void  prosesLogin(){
        User user = new User();
        user.setUsername(edEmail.getText().toString());
        user.setPassword(edPassword.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getClient().loginUser(user.getUsername(), user.getPassword());
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(SignInActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "Error"+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}