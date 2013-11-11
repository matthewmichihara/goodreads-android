package com.fourpool.goodreads.android.model;

public class Book {
    private final long isbn;
    private final String title;
    private final String author;

    public Book(long isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public long getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
