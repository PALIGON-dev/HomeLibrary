package com.example.homelibrary.Data;


import androidx.room.TypeConverter;

import java.util.Arrays;

public class Converters {
    @TypeConverter
    public static String fromStringArray(String[] array) {
        return array != null ? String.join(";", array) : null;
    }

    @TypeConverter
    public static String[] toStringArray(String data) {
        return data != null ? data.split(";") : new String[]{};
    }
}

