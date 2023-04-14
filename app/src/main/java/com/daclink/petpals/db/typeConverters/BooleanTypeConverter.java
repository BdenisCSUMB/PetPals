package com.daclink.petpals.db.typeConverters;

import androidx.room.TypeConverter;


public class BooleanTypeConverter {
    @TypeConverter
    public int convertBooleanToInt(Boolean adminStatus){
        if (adminStatus){
            return 1;
        } else {
            return 0;
        }
    }
    @TypeConverter
    public Boolean convertIntToBoolean(int adminStatus){

        return adminStatus == 1;

    }




}
