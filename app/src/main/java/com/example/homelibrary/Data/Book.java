package com.example.homelibrary.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Books")
public class Book {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    private int bookId;

    @ColumnInfo(name = "owners_ids")
    private int ownerIds; //userId, у кого есть книга

    private String title;//Название
    private String authors;//Авторы
    private String description;//Описание

    @ColumnInfo(name = "published_date")
    private String publishedDate;//Дата публикации

    @ColumnInfo(name = "cover_url")
    private String coverUrl; // ссылка на изображение обложки

    public Book(String title, String authors, String description, String publishedDate, String coverUrl) {
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.publishedDate = publishedDate;
        this.coverUrl = coverUrl; //Исправлено на coverUrl
        this.ownerIds = -1; //Изначально книги ни у кого нет
    }

    // Геттеры и сеттеры
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getOwnerIds() {
        return ownerIds;
    }

    public void setOwnerIds(int ownerIds) {
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


    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String thumbnailUrl) {
        this.coverUrl = thumbnailUrl;
    }
}
