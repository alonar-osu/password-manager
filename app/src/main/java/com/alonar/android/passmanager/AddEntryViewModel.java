package com.alonar.android.passmanager;

import com.alonar.android.passmanager.data.Entry;
import com.alonar.android.passmanager.data.EntryDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddEntryViewModel extends ViewModel {

    private LiveData<Entry> entry;

    public AddEntryViewModel(EntryDatabase database, int entryId) {
        entry = database.entryDao().loadEntryById(entryId);
    }

    public LiveData<Entry> getEntry() {
        return entry;
    }



}
