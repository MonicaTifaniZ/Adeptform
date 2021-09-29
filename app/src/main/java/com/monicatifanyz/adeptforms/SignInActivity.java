package com.monicatifanyz.adeptforms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.monicatifanyz.adeptforms.api.ApiClient;
import com.monicatifanyz.adeptforms.api.LoginResponse;
import com.monicatifanyz.adeptforms.api.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    Button btnLogin;
    ImageView ivFingerprint;
    TextInputEditText edEmail, edPassword;
    CheckBox checkBoxSave;

    Context mContext;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;

    private final static String TAG = " ini nama log";


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

        mContext = this;

        sharedPrefManager = new SharedPrefManager(getApplicationContext());

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember" , "");

        if (checkbox.equals("true")){
            Intent i = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(i);

        } else if (checkbox.equals("false")){
            Toast.makeText(SignInActivity.this, "Harap Login " , Toast.LENGTH_SHORT).show();
        }

        executor = ContextCompat.getMainExecutor(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesLogin();
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
        checkBoxSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                } else if (!compoundButton.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                }
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
        String username = edEmail.getText().toString();
        String password = edPassword.getText().toString();

        if (username.isEmpty()){
            edEmail.requestFocus();
            edEmail.setError("Email tidak boleh kosong!");
            return;
        }
        if (password.isEmpty()){
            edPassword.requestFocus();
            edPassword.setError("Password tidak boleh kosong!");
            return;
        }

        Call<ResponseBody> call = ApiClient
                .getClient()
                .signIn(username,password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("error").equals("false")){
                            Toast.makeText(mContext, "Login berhasil!", Toast.LENGTH_SHORT).show();

                            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                            Intent i = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(mContext, "Username/ Password Salah!",Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException | IOException e){
                        e.printStackTrace();
                    }
                } else {
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}