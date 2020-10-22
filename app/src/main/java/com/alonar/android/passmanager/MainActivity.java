package com.alonar.android.passmanager;

import android.content.Intent;
import android.os.Bundle;

import com.alonar.android.passmanager.data.PassDatabase;
import com.alonar.android.passmanager.data.PassEntry;
import com.alonar.android.passmanager.databinding.ActivityMainBinding;
import com.alonar.android.passmanager.utilities.AppExecutors;
import com.alonar.android.passmanager.utilities.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PassAdapter.ItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;
    private PassDatabase mDb;
    private RecyclerView mRecyclerView;
    private PassAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDb = PassDatabase.getInstance(getApplicationContext());
        initAddButton();
        setupRecyclerView();
    }

    private void initAddButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addEntryIntent = new Intent(MainActivity.this, AddEntryActivity.class);
                startActivity(addEntryIntent);
            }
        });
    }

    private void setupRecyclerView() {
        mRecyclerView = binding.passlistRecView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        setupViewModel();
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getEntries().observe(MainActivity.this, new Observer<List<PassEntry>>() {

            @Override
            public void onChanged(List<PassEntry> entries) {
                Log.d(TAG, "Updating list of entries from LiveData in ViewModel");
                mAdapter = new PassAdapter((ArrayList) entries, MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClickListener(int itemId) {
        Intent intent = new Intent(MainActivity.this, AddEntryActivity.class);
        intent.putExtra(Constants.EXTRA_ENTRY_ID, itemId);
        startActivity(intent);
    }
}