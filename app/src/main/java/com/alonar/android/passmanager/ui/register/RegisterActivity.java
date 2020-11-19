package com.alonar.android.passmanager.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.alonar.android.passmanager.R;
import com.alonar.android.passmanager.data.EntryDatabase;
import com.alonar.android.passmanager.data.Registration;
import com.alonar.android.passmanager.databinding.ActivityRegisterBinding;
import com.alonar.android.passmanager.encryption.EncryptedPassInfo;
import com.alonar.android.passmanager.encryption.Encrypter;
import com.alonar.android.passmanager.ui.login.LoginActivity;
import com.alonar.android.passmanager.utilities.AppExecutors;

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
        String enteredPassword = mMasterPasswordET.getText().toString();
        Date date = new Date();

        if (email.trim().equals("")) {
            mEmailET.setError("Email is empty");
        } else if (enteredPassword.trim().equals("")) {
            mMasterPasswordET.setError("Password is empty");
        } else {
            saveRegistration(Encrypter.encryptPassword(enteredPassword), email, date);
            updateSharedPrefs();
            updateUI(LoginActivity.class);
            finish();
        }
    }

    private void saveRegistration(EncryptedPassInfo encryptedPassInfo, String email, Date date) {
        String encryptedPassword = encryptedPassInfo.getEncryptedPassword();
        String cipherIV = encryptedPassInfo.getCipherIv();

        final Registration registrInfo = new Registration(email, encryptedPassword, cipherIV, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.registrationDao().insertRegistration(registrInfo);
            }
        });
    }

    private void updateSharedPrefs() {
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("notRegistered", false).apply();
    }

    private void updateUI(Class destination) {
        mLoadingProgressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(RegisterActivity.this, destination);
        startActivity(intent);
    }

}
