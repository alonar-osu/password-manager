package com.alonar.android.passmanager.data;


import com.alonar.android.passmanager.Type;

import java.util.Date;
import java.util.Formatter;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "entries")
public class Entry {

    @PrimaryKey(autoGenerate = true)
    private int mId;
    private String mName;
    private Type mType;
    private String mPassword;
    private String mIv;
    private Date mDate;

    @Ignore
    public Entry(String name, Type type, String password, String iv, Date date) {
        mName = name;
        mType = type;
        mPassword = password;
        mIv = iv;
        mDate = date;
    }

    // includes mId
    public Entry(int id, String name, Type type, String password, String iv, Date date) {
        mId = id;
        mName = name;
        mType = type;
        mPassword = password;
        mIv = iv;
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
    public String getIv() {return mIv;}
    public Date getDate() {
        return mDate;
    }
}



