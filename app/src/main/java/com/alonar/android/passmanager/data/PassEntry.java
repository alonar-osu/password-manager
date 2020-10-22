package com.alonar.android.passmanager.data;


import com.alonar.android.passmanager.Type;

import java.util.Date;
import java.util.Formatter;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "entries")
public class PassEntry {

    @PrimaryKey(autoGenerate = true)
    private int mId;
    private String mName;
    private Type mType;
    private String mPassword;
    private Date mDate;

    @Ignore
    public PassEntry(String name, Type type, String password, Date date) {
        mName = name;
        mType = type;
        mPassword = password;
        mDate = date;
    }

    // includes mId
    public PassEntry(int id, String name, Type type, String password, Date date) {
        mId = id;
        mName = name;
        mType = type;
        mPassword = password;
        mDate = date;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) { mId = id; }

    public String getName() {
        return mName;
    }

    public Type getType() {
        return mType;
    }

    public String getPassword() {
        return mPassword;
    }

    public Date getDate() {
        return mDate;
    }
}



