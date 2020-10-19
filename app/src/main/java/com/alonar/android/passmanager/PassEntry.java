package com.alonar.android.passmanager;


import java.util.Date;

public class PassEntry {
    private String mName;
    private Type mType;
    private String mPassword;
    private Date mDate;

    public PassEntry(String name, Type type, String password, Date date) {
        mName = name;
        mType = type;
        mPassword = password;
        mDate = date;
    }

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



