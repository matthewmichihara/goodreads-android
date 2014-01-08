package com.fourpool.goodreads.android.shelves;

import com.fourpool.goodreads.android.event.ShelvesFetchedEvent;
import com.fourpool.goodreads.android.model.GoodReadsService;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

public class ShelvesController {
    private final GoodReadsService goodReadsService;
    private ShelvesDisplay shelvesDisplay;

    @Inject ShelvesController(GoodReadsService goodReadsService) {
        this.goodReadsService = goodReadsService;
    }

    public void onCreateView(ShelvesDisplay shelvesDisplay) {
        this.shelvesDisplay = shelvesDisplay;
    }

    @Subscribe public void onShelvesFetched(ShelvesFetchedEvent event) {
        if (shelvesDisplay != null) {
            shelvesDisplay.display(event.getShelves());
        }
    }
}
