package com.alonar.android.passmanager.ui.register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alonar.android.passmanager.EntryFeedActivity;
import com.alonar.android.passmanager.R;
import com.alonar.android.passmanager.data.EntryDatabase;
import com.alonar.android.passmanager.data.Registration;
import com.alonar.android.passmanager.databinding.ActivityRegisterBinding;
import com.alonar.android.passmanager.ui.login.LoginActivity;
import com.alonar.android.passmanager.utilities.AppExecutors;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private EntryDatabase mDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        mDb = EntryDatabase.getInstance(getApplicationContext());

        final EditText emailEditText = binding.email;
        final EditText passwordEditText = binding.password;
        final Button registerButton = binding.register;
        final ProgressBar loadingProgressBar = binding.loading;

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Clicked regsiter button", Toast.LENGTH_SHORT).show();

                // TODO: add view model
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Date date = new Date();

                if (email.trim().equals("")) {
                    emailEditText.setError("email is empty");
                } else if (password.trim().equals("")) {
                    passwordEditText.setError("password is empty");
                } else {

                    final Registration registrInfo = new Registration(email, password, date);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.registrationDao().insertRegistration(registrInfo);
                        }
                    });
                }
                // set registr completed to shared prefs
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                        .putBoolean("notRegistered", false).apply();
                loadingProgressBar.setVisibility(View.VISIBLE);
                goToLogin();
                finish();
            }
        });
    }

    private void goToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }


}
