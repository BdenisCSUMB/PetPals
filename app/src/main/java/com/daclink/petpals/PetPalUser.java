package com.daclink.petpals;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.petpals.db.AppDatabase;

@Entity(tableName = AppDatabase.PETPAL_TABLE)
public class PetPalUser {

    @PrimaryKey(autoGenerate = true)
    private Integer UserID;
    private String userName;
    private String petname;
    private String password;
    private Integer petage;
    private String petsex;
    private String petlocation;
    private String petbreed;


    public PetPalUser(Integer UserID, String userName, String password) {
        this.UserID = UserID;
        this.userName = userName;
        this.password = password;
    }

    public Integer getUserID() {
        return UserID;
    }

    public void setUserID(Integer userID) {
        UserID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPetage() {
        return petage;
    }

    public void setPetage(Integer petage) {
        this.petage = petage;
    }

    public String getPetsex() {
        return petsex;
    }

    public void setPetsex(String petsex) {
        this.petsex = petsex;
    }

    public String getPetlocation() {
        return petlocation;
    }

    public void setPetlocation(String petlocation) {
        this.petlocation = petlocation;
    }

    public String getPetbreed() {
        return petbreed;
    }

    public void setPetbreed(String petbreed) {
        this.petbreed = petbreed;
    }
}