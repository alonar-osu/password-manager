package com.alonar.android.passmanager;

import android.app.Application;

import com.alonar.android.passmanager.data.Entry;
import com.alonar.android.passmanager.data.EntryDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EntryFeedViewModel extends AndroidViewModel {

    private static final String TAG = EntryFeedViewModel.class.getSimpleName();

    private LiveData<List<Entry>> entries;

    public EntryFeedViewModel(@NonNull Application application) {
        super(application);
        EntryDatabase database = EntryDatabase.getInstance(this.getApplication());
        entries = database.entryDao().loadAllEntries();
    }

    public LiveData<List<Entry>> getEntries() {
        return entries;
    }


}
