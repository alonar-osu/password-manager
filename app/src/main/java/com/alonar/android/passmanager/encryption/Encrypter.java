package com.alonar.android.passmanager.encryption;

import android.util.Base64;
import android.util.Log;

import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public class Encrypter {

    private static final String TAG = Encrypter.class.getSimpleName();

    public static EncodedPassInfo encryptPassword(String passwordtext) {

        byte[] plaintext = passwordtext.getBytes();
        GenCipher genCipher = GenCipher.getInstance();
        Cipher cipher = genCipher.getCipher();
        GenKey genKey = GenKey.getInstance();
        String encodedPasswordStr = null;
        String ivStr = null;

        try {
            cipher.init(Cipher.ENCRYPT_MODE, genKey.getKey());
            byte[] ciphertext = cipher.doFinal(plaintext);
            encodedPasswordStr = Base64.encodeToString(ciphertext, Base64.DEFAULT);
            byte[] iv = cipher.getIV();
            ivStr = Base64.encodeToString(iv, Base64.DEFAULT);
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e);
        }
        return new EncodedPassInfo(encodedPasswordStr, ivStr);
    }
}
