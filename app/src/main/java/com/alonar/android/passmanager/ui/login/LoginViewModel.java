package com.alonar.android.passmanager.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alonar.android.passmanager.R;
import com.alonar.android.passmanager.data.EntryDatabase;
import com.alonar.android.passmanager.data.Registration;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LiveData<Registration> registrInfo;

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public LoginViewModel(EntryDatabase database) {
        registrInfo = database.registrationDao().loadRegistrInfo();
    }

    public LiveData<Registration> getRegistrInfo() {return registrInfo;}

    public void login(String password, String masterPass) {
        // can be launched in a separate asynchronous job
        if (password.equals(masterPass)) {
            loginResult.setValue(new LoginResult(true));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

}