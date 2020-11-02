package com.alonar.android.passmanager.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.alonar.android.passmanager.data.LoginRepository;
import com.alonar.android.passmanager.data.Result;
import com.alonar.android.passmanager.data.model.LoggedInUser;
import com.alonar.android.passmanager.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String password) {
        // can be launched in a separate asynchronous job

        String username = "default"; // TODO: temporary
        Result<LoggedInUser> result = loginRepository.login(username, password);

      //  if (result instanceof Result.Success) {
        if (password.equals("secretpass")) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged( String password) {
    //  if (!isPasswordValid(password)) {
     //       loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
      //  } else {
            loginFormState.setValue(new LoginFormState(true));
       // }
    }



    // A placeholder password validation check
//    private boolean isPasswordValid(String password) {
//        return password != null;
//    }
}