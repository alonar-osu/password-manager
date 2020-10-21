package com.alonar.android.passmanager.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PassDao {

    @Query("SELECT * FROM entries ORDER BY mDate")
    LiveData<List<PassEntry>> loadAllEntries();

    @Insert
    void insertEntry(PassEntry passEntry);

}
