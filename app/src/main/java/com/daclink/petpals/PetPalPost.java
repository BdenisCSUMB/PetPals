package com.daclink.petpals;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.petpals.db.AppDatabase;

import java.util.Date;

/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: PetPalPost.java
 * Abstract: POJO for use in ROOM DB to store post information.
 * Date: 22 - April - 2023
 */
@Entity(tableName = AppDatabase.PETPAL_POSTS)
public class PetPalPost {

    @PrimaryKey(autoGenerate = true)
    private int mPostID;
    private int mUserID;
    private String mPostText;
    private Date mDate;

    private boolean isReported;

    public PetPalPost(int mUserID, String mPostText) {
        this.mUserID = mUserID;
        this.mPostText = mPostText;
        this.mDate = new Date();
    }

    public int getPostID() {
        return mPostID;
    }

    public void setPostID(int mPostID) {
        this.mPostID = mPostID;
    }

    public int getUserID() {
        return mUserID;
    }

    public void setUserID(int mUserID) {
        this.mUserID = mUserID;
    }

    public String getPostText() {
        return mPostText;
    }

    public void setPostText(String mPostText) {
        this.mPostText = mPostText;
    }

    public boolean isReported() {
        return isReported;
    }

    public void setReported(boolean reported) {
        isReported = reported;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    @Override
    public String toString() {
        return mDate + "\n  " + mPostText;
    }
}
