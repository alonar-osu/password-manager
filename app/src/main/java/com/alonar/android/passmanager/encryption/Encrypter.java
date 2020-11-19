package com.alonar.android.passmanager.encryption;

import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;

public class Encrypter {

    private static final String TAG = Encrypter.class.getSimpleName();

    public static EncryptedPassInfo encryptPassword(String enteredPassword) {

        byte[] enteredPasswordInBytes = enteredPassword.getBytes();
        GenCipher genCipher = GenCipher.getInstance();
        Cipher cipher = genCipher.getCipher();
        GenKey genKey = GenKey.getInstance();
        String encryptedPassword = null;
        String cipherIv = null;

        try {
            cipher.init(Cipher.ENCRYPT_MODE, genKey.getKey());
            byte[] encryptedPasswordBytes = cipher.doFinal(enteredPasswordInBytes);
            encryptedPassword = Base64.encodeToString(encryptedPasswordBytes, Base64.DEFAULT);
            byte[] cipherIvBytes = cipher.getIV();
            cipherIv = Base64.encodeToString(cipherIvBytes, Base64.DEFAULT);
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e);
        }
        return new EncryptedPassInfo(encryptedPassword, cipherIv);
    }
}
