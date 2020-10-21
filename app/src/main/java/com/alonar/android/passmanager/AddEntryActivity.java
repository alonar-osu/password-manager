package com.alonar.android.passmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.alonar.android.passmanager.data.PassDatabase;
import com.alonar.android.passmanager.data.PassEntry;
import com.alonar.android.passmanager.databinding.ActivityAddEntryBinding;
import com.alonar.android.passmanager.utilities.AppExecutors;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class AddEntryActivity extends AppCompatActivity {

    private static final String TAG = AddEntryActivity.class.getSimpleName();

    private ActivityAddEntryBinding binding;
    EditText mName;
    EditText mPassword;
    RadioGroup mRadioGroup;
    Button mSaveButton;

    private PassDatabase mDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_entry);

        initViews();

        mDb = PassDatabase.getInstance(getApplicationContext());

    }

    private void initViews() {
        mName = binding.etEntryName;
        mPassword = binding.etEntryPassword;
        mRadioGroup = binding.radioGroup;
        mSaveButton = binding.saveButton;
        mSaveButton.setOnClickListener(new View.OnClickListener() {
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

        final PassEntry passEntry = new PassEntry(name, type, password, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.passDao().insertEntry(passEntry);
                finish();
            }
        });
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



}
