package com.example.homelibrary.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


@Entity(tableName = "Books")
public class Book {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    private int bookId;

    @ColumnInfo(name = "owners_ids")
    private String ownerIds; //userId, у кого есть книга

    private String title;//Название
    private String authors;//Авторы
    private String description;//Описание

    @ColumnInfo(name = "published_date")
    private String publishedDate;//Дата публикации

    @ColumnInfo(name = "cover_url")
    private String сoverUrl; // ссылка на изображение обложки

    public Book(String title, String authors, String description, String publishedDate, String сoverUrl) {
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.publishedDate = publishedDate;
        this.сoverUrl = сoverUrl;
        this.ownerIds = null;
    }

    // Геттеры и сеттеры
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getOwnerIds() {
        return ownerIds;
    }

    public void setOwnerIds(String ownerIds) {
        this.ownerIds = ownerIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }


    public String getСoverUrl() {
        return сoverUrl;
    }

    public void setСoverUrl(String thumbnailUrl) {
        this.сoverUrl = thumbnailUrl;
    }
}
