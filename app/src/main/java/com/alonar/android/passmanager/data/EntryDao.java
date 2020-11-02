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
public interface EntryDao {

    @Query("SELECT * FROM entries ORDER BY mDate")
    LiveData<List<Entry>> loadAllEntries();

    @Insert
    void insertEntry(Entry passEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEntry(Entry passEntry);

    @Query("SELECT * FROM entries WHERE mId = :id")
    LiveData<Entry> loadEntryById(int id);

    @Delete
    void deleteEntry(Entry passEntry);

}
