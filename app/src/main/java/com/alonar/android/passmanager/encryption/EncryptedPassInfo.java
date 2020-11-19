package com.alonar.android.passmanager.encryption;

public class EncryptedPassInfo {
    String mEncryptedPassword;
    String mCipherIv;

    public EncryptedPassInfo(String encryptedPassword, String cipherIv) {
        mEncryptedPassword = encryptedPassword;
        mCipherIv = cipherIv;
    }

    public String getEncryptedPassword() {
        return mEncryptedPassword;
    }
    public String getCipherIv() {
        return mCipherIv;
    }
}
