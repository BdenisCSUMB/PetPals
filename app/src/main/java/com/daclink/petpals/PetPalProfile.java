package com.daclink.petpals;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.petpals.db.AppDatabase;

/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: PetPalProfile.java
 * Abstract: POJO for use in ROOM DB to store user information.
 * Date: 19 - April - 2023
 */
@Entity(tableName = AppDatabase.PETPAL_PROFILE)
public class PetPalProfile {

    @PrimaryKey
    private int profileID;
    private String petName = "Name";
    private String petBreed = "Breed";
    private String petSex = "Sex";
    private String petLocation = "Location";
    private int age = -1;
    private boolean isBanned;

    public PetPalProfile(int profileID) {
        this.profileID = profileID;
    }

    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetSex() {
        return petSex;
    }

    public void setPetSex(String petSex) {
        this.petSex = petSex;
    }

    public String getPetLocation() {
        return petLocation;
    }

    public void setPetLocation(String petLocation) {
        this.petLocation = petLocation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
