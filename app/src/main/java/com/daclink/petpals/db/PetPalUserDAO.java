package com.daclink.petpals.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.daclink.petpals.PetPalUser;

import java.util.List;

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


}
