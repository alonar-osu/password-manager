package com.alonar.android.passmanager;

import com.alonar.android.passmanager.data.EntryDatabase;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AddEntryViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final EntryDatabase mDb;
    private final int mEntryId;

    public AddEntryViewModelFactory(EntryDatabase database, int entryId) {
        mDb = database;
        mEntryId = entryId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddEntryViewModel(mDb, mEntryId);
    }


}
