package com.fourpool.goodreads.android.event;

import com.fourpool.goodreads.android.shelves.Shelf;

import java.util.List;

public class ShelvesFetchedEvent {
    private final List<Shelf> shelves;

    public ShelvesFetchedEvent(List<Shelf> shelves) {
        this.shelves = shelves;
    }

    public List<Shelf> getShelves() {
        return shelves;
    }
}
