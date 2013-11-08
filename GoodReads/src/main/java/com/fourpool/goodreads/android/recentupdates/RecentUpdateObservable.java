package com.fourpool.goodreads.android.recentupdates;

import rx.Observable;

public class RecentUpdateObservable extends Observable {
    protected RecentUpdateObservable(OnSubscribeFunc onSubscribe) {
        super(onSubscribe);
    }
}
