package com.alonar.android.passmanager.data;


import android.content.Context;
import android.util.Log;

import com.alonar.android.passmanager.utilities.DateConverter;
import com.alonar.android.passmanager.utilities.PassTypeConverter;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import static com.alonar.android.passmanager.utilities.Constants.DATABASE_NAME;

@Database(entities = {PassEntry.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, PassTypeConverter.class})
public abstract class PassDatabase extends RoomDatabase {

    private static final String TAG = PassDatabase.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static PassDatabase sInstance;

    public static PassDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        PassDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        Log.d(TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract PassDao passDao();
}
