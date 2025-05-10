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

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "owners_ids")
    private String[] ownerIds; // список userId, у кого есть книга

    private String title;//Название
    private String[] authors;//Авторы
    private String description;//Описание

    @ColumnInfo(name = "published_date")
    private String publishedDate;//Дата публикации

    private double averageRating;//Рейтинг

    @ColumnInfo(name = "thumbnail_url")
    private String thumbnailUrl; // ссылка на изображение обложки

    public Book(String title, String[] authors, String description, String publishedDate,
                double averageRating, String thumbnailUrl, String[] ownerIds) {
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.publishedDate = publishedDate;
        this.averageRating = averageRating;
        this.thumbnailUrl = thumbnailUrl;
        this.ownerIds = ownerIds;
    }

    // Геттеры и сеттеры
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String[] getOwnerIds() {
        return ownerIds;
    }

    public void setOwnerIds(String[] ownerIds) {
        this.ownerIds = ownerIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
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

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
