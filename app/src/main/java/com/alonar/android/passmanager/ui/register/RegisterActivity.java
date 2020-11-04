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
import com.alonar.android.passmanager.databinding.ActivityRegisterBinding;
import com.alonar.android.passmanager.ui.login.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        final EditText emailEditText = binding.email;
        final EditText passwordEditText = binding.password;
        final Button registerButton = binding.register;
        final ProgressBar loadingProgressBar = binding.loading;

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Clicked regsiter button", Toast.LENGTH_SHORT).show();

                // TODO: do registration

                // if registered successfully - update shared prefs
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                        .putBoolean("notRegistered", false).apply();

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
