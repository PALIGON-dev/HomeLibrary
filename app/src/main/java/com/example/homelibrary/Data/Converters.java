package com.example.homelibrary.Data;


import androidx.room.TypeConverter;

import java.util.Arrays;

public class Converters {
    @TypeConverter
    public static String fromStringArray(String[] array) {
        return array != null ? String.join(";", array) : null;//Конвертер для преобразования
        //из массива
    }

    @TypeConverter
    public static String[] toStringArray(String data) {
        return data != null ? data.split(";") : new String[]{};//Конвертер для преобразования
        //в массив
    }
}

