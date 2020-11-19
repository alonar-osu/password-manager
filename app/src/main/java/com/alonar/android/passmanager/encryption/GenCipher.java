package com.alonar.android.passmanager.encryption;

import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class GenCipher {

    private static final String TAG = GenCipher.class.getSimpleName();

    private static GenCipher objGenCipher;
    private static Cipher cipher;
    private GenKey genKey;

    public GenCipher() {

        genKey = GenKey.getInstance();
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); // CBC/PKCS5PADDING
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e);
        }
    }

    public static GenCipher getInstance() {
        if (objGenCipher == null) {
            objGenCipher = new GenCipher();
        }
        return objGenCipher;
    }

    public static Cipher getCipher() {
        return cipher;
    }




}
