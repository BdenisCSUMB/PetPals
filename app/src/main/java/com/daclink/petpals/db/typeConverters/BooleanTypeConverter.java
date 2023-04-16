package com.daclink.petpals.db.typeConverters;

import androidx.room.TypeConverter;

/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: BooleanTypeConverter.java
 * Abstract: TypeConverter for BOOLEAN value for ROOM architecture
 * Date: 11 - April - 2023
 */

public class BooleanTypeConverter {
    @TypeConverter
    public int convertBooleanToInt(Boolean adminStatus) {
        if (adminStatus) {
            return 1;
        } else {
            return 0;
        }
    }

    @TypeConverter
    public Boolean convertIntToBoolean(int adminStatus) {

        return adminStatus == 1;

    }


}
