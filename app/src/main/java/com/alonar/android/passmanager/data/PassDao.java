package com.alonar.android.passmanager.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PassDao {

    @Query("SELECT * FROM entries ORDER BY mDate")
    List<PassEntry> loadAllEntries();

    @Insert
    void insertEntry(PassEntry passEntry);

}
