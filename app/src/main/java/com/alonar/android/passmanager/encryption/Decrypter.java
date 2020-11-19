package com.alonar.android.passmanager.encryption;

import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public class Decrypter {

    private static final String TAG = Decrypter.class.getSimpleName();

    public static String decryptPassword(String password, String passwordIV) {

        GenCipher genCipher = GenCipher.getInstance();
        Cipher cipher = genCipher.getCipher();
        GenKey genKey = GenKey.getInstance();

        byte[] passwordInBytes =Base64.decode(password, Base64.DEFAULT);
        byte[] ivInBytes = Base64.decode(passwordIV, Base64.DEFAULT);
        String decryptedPassword = null;

        try {
            cipher.init(Cipher.DECRYPT_MODE, genKey.getKey(), new IvParameterSpec(ivInBytes));
            decryptedPassword = new String(cipher.doFinal(passwordInBytes));
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e);
        }
        return decryptedPassword;
    }
}
