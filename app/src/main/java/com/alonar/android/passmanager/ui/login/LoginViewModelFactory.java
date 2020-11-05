package com.alonar.android.passmanager.ui.login;

import com.alonar.android.passmanager.data.EntryDatabase;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final EntryDatabase mDb;

    public LoginViewModelFactory(EntryDatabase database) {
        mDb = database;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

            return (T) new LoginViewModel(mDb);
    }

}
