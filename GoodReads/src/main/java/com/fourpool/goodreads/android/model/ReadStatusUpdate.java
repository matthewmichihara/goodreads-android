package com.fourpool.goodreads.android.model;

public class ReadStatusUpdate {
    private final Actor actor;
    private final Book book;

    public ReadStatusUpdate(Actor actor, Book book) {
        this.actor = actor;
        this.book = book;
    }
}
