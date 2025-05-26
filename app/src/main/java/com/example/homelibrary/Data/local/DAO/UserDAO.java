package com.example.homelibrary.Data.local.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.homelibrary.Data.Models.User;

@Dao
public interface UserDAO {
    @Insert
    public void addUser(User user);
    @Delete
    public void deleteUser(User user);
    @Update
    public void updateUser(User user);
    @Query("SELECT * FROM Users WHERE user_id==:user_id")
    public User getUser(int user_id);
    @Query("SELECT user_id FROM Users WHERE Email = :Email")
    int getIdByEmail(String Email);
    @Query("SELECT books_active FROM Users WHERE user_id==:user_id")
    public int getActive(int user_id);
    @Query("SELECT books_archive FROM Users WHERE user_id==:user_id")
    public int getArchive(int user_id);
    @Query("SELECT * FROM Users WHERE email ==:email AND password = :password")
    public User validate(String email, String password);
}
