package com.alonar.android.passmanager.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RegistrationDao {

    @Insert
    void insertRegistration(Registration registrInfo);

    @Query("SELECT * FROM registration_info WHERE mId = 1")
    LiveData<Registration> loadRegistrInfo();

}
