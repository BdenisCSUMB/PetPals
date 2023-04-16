package com.daclink.petpals.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.daclink.petpals.PetPalUser;
import com.daclink.petpals.db.typeConverters.BooleanTypeConverter;

/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: AppDatabase.java
 * Abstract: RoomDatabase abstract class. List Database values, names, and typeconverters.
 * Date: 11 - April - 2023
 */

@Database(entities = {PetPalUser.class}, version = 1)
@TypeConverters(BooleanTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "PETPALUSER_DATA";
    public static final String PETPAL_TABLE = "PETPAL_TABLE";

    public abstract PetPalUserDAO getPetPalUserDAO();
}
