package com.example.homelibrary.Data.local;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;

import com.example.homelibrary.Data.Models.Book;
import com.example.homelibrary.Data.local.DAO.BookDAO;
import com.example.homelibrary.Data.Models.User;
import com.example.homelibrary.Data.local.DAO.UserDAO;

@Database(entities = {Book.class, User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract BookDAO bookDAO();
    public abstract UserDAO userDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "HomeLibraryDB")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
