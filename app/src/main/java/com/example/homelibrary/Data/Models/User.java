package com.example.homelibrary.Data.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Users")
public class User {

    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "email")
    private String Email;

    @ColumnInfo(name = "books_active")
    private int Active;

    @ColumnInfo(name = "books_archive")
    private int Archive;

    @ColumnInfo(name = "password")
    private String Password;


    public User(){}
    public User(String email, String password) {
        Id = 0;
        Email = email;
        Active = 0;
        Archive = 0;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    public int getArchive() {
        return Archive;
    }

    public void setArchive(int archive) {
        Archive = archive;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
