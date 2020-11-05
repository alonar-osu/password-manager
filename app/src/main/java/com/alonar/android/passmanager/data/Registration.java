package com.alonar.android.passmanager.data;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "registration_info")
public class Registration {

    @PrimaryKey(autoGenerate = true)
    private int mId;
    private String mEmail;
    private String mPassword;
    private Date mDate;

    @Ignore
    public Registration(String email, String password, Date date) {
        mEmail = email;
        mPassword = password;
        this.mDate = date;
    }

    // includes id
    public Registration(int id, String email, String password, Date date) {
        mId = id;
        mEmail = email;
        mPassword = password;
        this.mDate = date;
    }

    public int getId() {
        return mId;
    }
    public String getEmail() {return mEmail;}
    public String getPassword() {return mPassword;}
    public Date getDate() {return mDate;}

}
