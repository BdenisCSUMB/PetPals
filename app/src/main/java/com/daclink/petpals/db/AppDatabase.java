package com.daclink.petpals.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.daclink.petpals.PetPalPost;
import com.daclink.petpals.PetPalProfile;
import com.daclink.petpals.PetPalUser;
import com.daclink.petpals.db.typeConverters.BooleanTypeConverter;
import com.daclink.petpals.db.typeConverters.DateTypeConverter;

/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: AppDatabase.java
 * Abstract: RoomDatabase abstract class. List Database values, names, and typeconverters.
 * Date: 11 - April - 2023
 */

@Database(entities = {PetPalUser.class, PetPalProfile.class, PetPalPost.class}, version = 3)
@TypeConverters({DateTypeConverter.class, BooleanTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "PETPALUSER_DATA";
    public static final String PETPAL_TABLE = "PETPAL_TABLE";
    public static final String PETPAL_POSTS = "PETPAL_POSTS";
    public  static final String PETPAL_PROFILE = "PETPAL_PROFILES";

    public abstract PetPalUserDAO getPetPalUserDAO();
}
