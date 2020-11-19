package com.alonar.android.passmanager.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alonar.android.passmanager.R;
import com.alonar.android.passmanager.data.EntryDatabase;
import com.alonar.android.passmanager.data.Registration;
import com.alonar.android.passmanager.databinding.ActivityRegisterBinding;
import com.alonar.android.passmanager.encryption.EncodedPassInfo;
import com.alonar.android.passmanager.encryption.Encrypter;
import com.alonar.android.passmanager.ui.login.LoginActivity;
import com.alonar.android.passmanager.utilities.AppExecutors;

import java.util.Arrays;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private ActivityRegisterBinding binding;
    private EntryDatabase mDb;
    EditText mEmailET;
    EditText mMasterPasswordET;
    Button mRegisterButton;
    ProgressBar mLoadingProgressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        mDb = EntryDatabase.getInstance(getApplicationContext());

        initViews();
    }

    private void initViews() {
        mEmailET = binding.email;
        mMasterPasswordET = binding.password;
        mRegisterButton = binding.register;
        mLoadingProgressBar = binding.loading;
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterButtonClicked();
            }
        });
    }

    private void onRegisterButtonClicked()  {

        String email = mEmailET.getText().toString();
        String password = mMasterPasswordET.getText().toString();
        Date date = new Date();

        if (email.trim().equals("")) {
            mEmailET.setError("Email is empty");
        } else if (password.trim().equals("")) {
            mMasterPasswordET.setError("Password is empty");
        } else {

            // encrypt password
            EncodedPassInfo encodedPassInfo = Encrypter.encryptPassword(password);
            String encodedPassword = encodedPassInfo.getPasswordCipher();
            String cipherIV = encodedPassInfo.getIV();

            // save registration info
            final Registration registrInfo = new Registration(email, encodedPassword, cipherIV, date);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.registrationDao().insertRegistration(registrInfo);
                }
            });

            updateSharedPrefs();
            updateUI(LoginActivity.class);
            finish();
        }
    }

    private void updateSharedPrefs() {
        // set registr completed to shared prefs
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("notRegistered", false).apply();
    }

    private void updateUI(Class destination) {
        mLoadingProgressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(RegisterActivity.this, destination);
        startActivity(intent);
    }

}
