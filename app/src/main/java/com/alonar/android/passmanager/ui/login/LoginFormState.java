package com.alonar.android.passmanager.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {

    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    LoginFormState(boolean isDataValid) {
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }


    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}