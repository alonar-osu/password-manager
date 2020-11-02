package com.alonar.android.passmanager.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alonar.android.passmanager.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String password) {
        // can be launched in a separate asynchronous job
        if (password.equals("secretpass")) {
            loginResult.setValue(new LoginResult(true));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

}