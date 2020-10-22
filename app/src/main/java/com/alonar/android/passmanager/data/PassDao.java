package com.alonar.android.passmanager.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PassDao {

    @Query("SELECT * FROM entries ORDER BY mDate")
    LiveData<List<PassEntry>> loadAllEntries();

    @Insert
    void insertEntry(PassEntry passEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEntry(PassEntry passEntry);

    @Query("SELECT * FROM entries WHERE mId = :id")
    LiveData<PassEntry> loadEntryById(int id);

    @Delete
    void deleteEntry(PassEntry passEntry);

}
