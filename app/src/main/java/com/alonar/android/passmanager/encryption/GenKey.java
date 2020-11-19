package com.alonar.android.passmanager.encryption;


import android.util.Log;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class GenKey {

    private static final String TAG = GenKey.class.getSimpleName();

    private static GenKey objGenKey;
    private static SecretKey key;

    public GenKey() {
        // initialize the key
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(256);
            key = keygen.generateKey();
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e);
        }
    }

    public static GenKey getInstance() {
        if (objGenKey == null) {
            objGenKey = new GenKey();
        }
        return objGenKey;
    }

    public static SecretKey getKey() {
        return key;
    }

}
