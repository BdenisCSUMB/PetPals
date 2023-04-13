package com.daclink.petpals.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.daclink.petpals.PetPalUser;

@Database(entities = {PetPalUser.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "com.daclink.petpals.PETPALUSER_DATA";
    public static final String PETPAL_TABLE = "com.daclink.petpals.PETPAL_TABLE";

    public abstract PetPalUserDAO getPetPalUserDAO();
}
