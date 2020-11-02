package com.alonar.android.passmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.alonar.android.passmanager.data.EntryDatabase;
import com.alonar.android.passmanager.data.Entry;
import com.alonar.android.passmanager.databinding.ActivityAddEntryBinding;
import com.alonar.android.passmanager.utilities.AppExecutors;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import static com.alonar.android.passmanager.utilities.Constants.DEFAULT_ENTRY_ID;
import static com.alonar.android.passmanager.utilities.Constants.EXTRA_ENTRY_ID;

public class AddEntryActivity extends AppCompatActivity {

    private static final String TAG = AddEntryActivity.class.getSimpleName();

    private ActivityAddEntryBinding binding;
    EditText mName;
    EditText mPassword;
    RadioGroup mRadioGroup;
    Button mButton;
    private int mEntryId = DEFAULT_ENTRY_ID;
    private EntryDatabase mDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_entry);

        initViews();

        mDb = EntryDatabase.getInstance(getApplicationContext());

        launchAsEditableIfUpdating();
    }

    private void launchAsEditableIfUpdating() {
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_ENTRY_ID)) {
            mButton.setText(R.string.update_button);
            if (mEntryId == DEFAULT_ENTRY_ID) {
                mEntryId = intent.getIntExtra(EXTRA_ENTRY_ID, DEFAULT_ENTRY_ID);

                Log.d(TAG, "Retrieving specific task from database");
                final LiveData<Entry> entry = mDb.entryDao().loadEntryById(mEntryId);

                entry.observe(this, new Observer<Entry>() {
                    @Override
                    public void onChanged(@Nullable Entry passEntry) {
                        entry.removeObserver(this);
                        Log.d(TAG, "Receiving database update from LiveData");
                        populateUI(passEntry);
                    }
                });
            }
        }
    }

    private void populateUI(Entry entry) {
        if (entry == null) {
            return;
        }
        mName.setText(entry.getName());
        mPassword.setText(entry.getPassword());

        mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        setTypeInViews(entry.getType());
    }

    private void initViews() {
        mName = binding.etEntryName;
        mPassword = binding.etEntryPassword;
        mRadioGroup = binding.radioGroup;
        mButton = binding.saveButton;
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked() {
        String name = mName.getText().toString();
        String password = mPassword.getText().toString();
        Type type = getTypeFromViews();
        Date date = new Date();

        if (name.trim().equals("")) {
            mName.setError("Name this entry?");
        } else {
            final Entry passEntry = new Entry(name, type, password, date);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if (mEntryId == DEFAULT_ENTRY_ID) {
                        mDb.entryDao().insertEntry(passEntry);
                    } else {
                        passEntry.setId(mEntryId);
                        mDb.entryDao().updateEntry(passEntry);
                    }
                    finish();
                }
            });
        }
    }

    public void ShowHidePass(View view){

        if(view.getId()==R.id.show_pass_icon) {

            if (mPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView) view).setImageResource(R.drawable.ic_visibility);
                mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else {
                ((ImageView) view).setImageResource(R.drawable.ic_visibility_off);
                mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    public Type getTypeFromViews() {
        Type type = Type.EMAIL;
       int checkId = ((RadioGroup) binding.radioGroup).getCheckedRadioButtonId();
       switch (checkId) {
           case R.id.radButton1:
               type = Type.EMAIL;
               break;
           case R.id.radButton2:
               type = Type.APP;
               break;
           case R.id.radButton3:
               type = Type.WEBSITE;
               break;
           case R.id.radButton4:
               type = Type.BANK;
               break;
           case R.id.radButton5:
               type = Type.UTILITY;
               break;
           case R.id.radButton6:
               type = Type.OTHER;
       }
       return type;
    }

    public void setTypeInViews(Type type) {
        switch (type) {
            case EMAIL:
                binding.radioGroup.check(R.id.radButton1);
                break;
            case APP:
                binding.radioGroup.check(R.id.radButton2);
                break;
            case WEBSITE:
                binding.radioGroup.check(R.id.radButton3);
                break;
            case BANK:
                binding.radioGroup.check(R.id.radButton4);
                break;
            case UTILITY:
                binding.radioGroup.check(R.id.radButton5);
                break;
            case OTHER:
                binding.radioGroup.check(R.id.radButton6);
        }
    }
}
