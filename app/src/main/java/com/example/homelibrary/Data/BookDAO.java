package com.example.homelibrary.Data;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert
    void addBook(Book book);
    @Insert
    void insertListBooks(List<Book> books);
    @Update
    void updateBook(Book book);
    @Delete
    void deleteBook(Book book);
    @Query("SELECT * FROM Books")
    List<Book> getAllBooks();
    @Query("SELECT * FROM Books WHERE book_id = :id")
    Book getBookById(int id);
    @Query("SELECT * FROM Books WHERE :userId IN (owners_ids)")
    List<Book> getBooksByOwner(String userId);
    @Query("DELETE FROM Books")
    void deleteAll();
}
