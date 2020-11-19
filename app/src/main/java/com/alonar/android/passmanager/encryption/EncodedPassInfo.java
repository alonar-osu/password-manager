package com.alonar.android.passmanager.encryption;

public class EncodedPassInfo {
    String mPasswordCipher;
    String mIV;

    public EncodedPassInfo(String passwordCipher, String passwordIV) {
        mPasswordCipher = passwordCipher;
        mIV = passwordIV;
    }

    public String getPasswordCipher() {
        return mPasswordCipher;
    }

    public String getIV() {
        return mIV;
    }
}
