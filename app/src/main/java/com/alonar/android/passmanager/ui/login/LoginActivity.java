package com.alonar.android.passmanager.ui.login;

import android.app.Activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alonar.android.passmanager.EntryFeedActivity;
import com.alonar.android.passmanager.R;
import com.alonar.android.passmanager.data.EntryDatabase;
import com.alonar.android.passmanager.data.Registration;
import com.alonar.android.passmanager.databinding.ActivityLoginBinding;
import com.alonar.android.passmanager.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private EntryDatabase mDb;
    private LoginViewModel viewModel;
    private String masterPass;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mDb = EntryDatabase.getInstance(getApplicationContext());

        LoginViewModelFactory factory = new LoginViewModelFactory(mDb);
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);

        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        final Boolean notRegistered = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("notRegistered", true);

        if (notRegistered) {
            goToRegister();
            finish();
        }

        viewModel.getRegistrInfo().observe(this, new Observer<Registration>() {
            @Override
            public void onChanged(@Nullable Registration registrInfo) {

                if (registrInfo == null) {
                    return;
                }
                masterPass = registrInfo.getPassword();
            }
        });

        viewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess()) {
                    updateUI();
                    finish();
                }
                setResult(Activity.RESULT_OK);
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                loginButton.setEnabled(true);
            }
        };

        passwordEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                viewModel.login(passwordEditText.getText().toString(), masterPass);
            }
        });
    }

    private void updateUI() {
        Intent intent = new Intent(LoginActivity.this, EntryFeedActivity.class);
        startActivity(intent);
    }

    private void goToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
        // lower keyboard
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) { view = new View(this); }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}