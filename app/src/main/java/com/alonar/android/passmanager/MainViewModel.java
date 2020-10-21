package com.alonar.android.passmanager;

import android.app.Application;

import com.alonar.android.passmanager.data.PassDatabase;
import com.alonar.android.passmanager.data.PassEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<PassEntry>> entries;

    public MainViewModel(@NonNull Application application) {
        super(application);
        PassDatabase database = PassDatabase.getInstance(this.getApplication());
        entries = database.passDao().loadAllEntries();
    }

    public LiveData<List<PassEntry>> getEntries() {
        return entries;
    }


}
