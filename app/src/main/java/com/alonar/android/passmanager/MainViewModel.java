package com.alonar.android.passmanager;

import android.app.Application;

import com.alonar.android.passmanager.data.Entry;
import com.alonar.android.passmanager.data.EntryDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<Entry>> entries;

    public MainViewModel(@NonNull Application application) {
        super(application);
        EntryDatabase database = EntryDatabase.getInstance(this.getApplication());
        entries = database.passDao().loadAllEntries();
    }

    public LiveData<List<Entry>> getEntries() {
        return entries;
    }


}
