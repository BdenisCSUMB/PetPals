package com.daclink.petpals.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.daclink.petpals.PetPalProfile;
import com.daclink.petpals.PetPalUser;

import java.util.List;

/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: PetPalUserDAO.java
 * Abstract: DAO for ROOM DB - PETPAL_TABLE
 * Date: 11 - April - 2023
 */

@Dao
public interface PetPalUserDAO {

    @Insert
    void insert(PetPalUser... petPalUsers);

    @Update
    void update(PetPalUser... petPalUsers);

    @Delete
    void delete(PetPalUser petPalUser);

    @Query("SELECT * FROM " + AppDatabase.PETPAL_TABLE)
    List<PetPalUser> getUserIDs();

    @Query("SELECT * FROM " + AppDatabase.PETPAL_TABLE + " WHERE userID = :userID")
    PetPalUser findPetPalUser(int userID);

    @Query("SELECT * FROM " + AppDatabase.PETPAL_PROFILE + " WHERE profileID = :profileID")
    PetPalProfile findPetPalProfile(int profileID);

    @Insert
    void insert(PetPalProfile... petPalProfiles);

    @Update
    void update(PetPalProfile... petPalProfiles);

    @Delete
    void delete(PetPalProfile petPalProfile);
}
