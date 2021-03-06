package com.alonar.android.passmanager.ui.login;

import androidx.annotation.Nullable;

class LoginResult {

    private boolean success = false;
    @Nullable
    private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(boolean success) {
        this.success = success;
    }

    boolean getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}