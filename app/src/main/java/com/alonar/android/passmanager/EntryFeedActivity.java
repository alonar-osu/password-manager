package com.alonar.android.passmanager;

import android.content.Intent;
import android.os.Bundle;

import com.alonar.android.passmanager.data.Entry;
import com.alonar.android.passmanager.data.EntryDatabase;
import com.alonar.android.passmanager.databinding.ActivityMainBinding;
import com.alonar.android.passmanager.utilities.AppExecutors;
import com.alonar.android.passmanager.utilities.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class EntryFeedActivity extends AppCompatActivity implements EntryAdapter.ItemClickListener {

    private static final String TAG = EntryFeedActivity.class.getSimpleName();

    private ActivityMainBinding binding;
    private EntryDatabase mDb;
    private RecyclerView mRecyclerView;
    private EntryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupMainView();
        mDb = EntryDatabase.getInstance(getApplicationContext());
        initAddButton();
        setupRecyclerView();
        initSwipeToDeleteIem();
    }

    private void setupMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initSwipeToDeleteIem() {
        // swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
                | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Entry> entries = mAdapter.getEntries();
                        mDb.entryDao().deleteEntry(entries.get(position));
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    private void initAddButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addEntryIntent = new Intent(EntryFeedActivity.this, AddEntryActivity.class);
                startActivity(addEntryIntent);
            }
        });
    }

    private void setupRecyclerView() {
        mRecyclerView = binding.passlistRecView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        setupViewModel();

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
    }

    private void setupViewModel() {
        EntryFeedViewModel viewModel = ViewModelProviders.of(this).get(EntryFeedViewModel.class);
        viewModel.getEntries().observe(EntryFeedActivity.this, new Observer<List<Entry>>() {

            @Override
            public void onChanged(List<Entry> entries) {
                Log.d(TAG, "Updating list of entries from LiveData in ViewModel");
                mAdapter = new EntryAdapter((ArrayList) entries, EntryFeedActivity.this);
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
        Intent intent = new Intent(EntryFeedActivity.this, AddEntryActivity.class);
        intent.putExtra(Constants.EXTRA_ENTRY_ID, itemId);
        startActivity(intent);
    }
}