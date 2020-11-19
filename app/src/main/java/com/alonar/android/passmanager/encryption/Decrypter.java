package com.alonar.android.passmanager.encryption;

import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public class Decrypter {

    private static final String TAG = Decrypter.class.getSimpleName();

    public static String decryptPassword(String encryptedPassword, String cipherIV) {

        GenCipher genCipher = GenCipher.getInstance();
        Cipher cipher = genCipher.getCipher();
        GenKey genKey = GenKey.getInstance();

        byte[] encryptedPasswordInBytes = Base64.decode(encryptedPassword, Base64.DEFAULT);
        byte[] cipherIvInBytes = Base64.decode(cipherIV, Base64.DEFAULT);
        String decryptedPassword = null;

        try {
            cipher.init(Cipher.DECRYPT_MODE, genKey.getKey(), new IvParameterSpec(cipherIvInBytes));
            decryptedPassword = new String(cipher.doFinal(encryptedPasswordInBytes));
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e);
        }
        return decryptedPassword;
    }
}
