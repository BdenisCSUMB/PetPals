package com.daclink.petpals;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.petpals.db.AppDatabase;

/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: PetPalUser.java
 * Abstract: POJO for use in ROOM DB to store user information.
 * Date: 11 - April - 2023
 */

@Entity(tableName = AppDatabase.PETPAL_TABLE)
public class PetPalUser {

    @PrimaryKey(autoGenerate = true)
    private int userID;
    private String userName;
    private String password;
    private Boolean isAdmin;

    public PetPalUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = false;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}