package com.fourpool.goodreads.android.controller;

import com.fourpool.goodreads.android.model.SessionStore;
import com.fourpool.goodreads.android.ui.RecentUpdatesFragment;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import oauth.signpost.OAuthConsumer;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.concurrency.AndroidSchedulers;
import rx.concurrency.Schedulers;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

public class RecentUpdatesController {
    @Inject SessionStore sessionStore;

    private RecentUpdatesFragment recentUpdatesFragment;

    public void onCreateView(final RecentUpdatesFragment recentUpdatesFragment) {
        Observable recentUpdatesObservable = Observable.create(new Observable.OnSubscribeFunc() {
            @Override public Subscription onSubscribe(Observer observer) {
                try {
                    URL url = new URL("https://www.goodreads.com/updates/friends.xml");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    OAuthConsumer consumer = sessionStore.getSession().getConsumer();
                    consumer.sign(connection);
                    connection.connect();

                    List<String> updates = new ArrayList<String>();

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = factory.newDocumentBuilder();
                    Document doc = documentBuilder.parse(connection.getInputStream());

                    NodeList nodeList = doc.getElementsByTagName("updates").item(0).getChildNodes();
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node updateElement = nodeList.item(i);
                        NodeList updateElementChildren = updateElement.getChildNodes();
                        for (int j = 0; j < updateElementChildren.getLength(); j++) {
                            Node updateChild = updateElementChildren.item(j);
                            if (updateChild.getNodeName().equals("link")) {
                                String link = updateChild.getLastChild().getTextContent().trim();
                                updates.add(link);
                            }
                        }
                    }

                    observer.onNext(updates);
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        });

        Observer<List<String>> recentUpdatesObserver = new Observer<List<String>>() {
            @Override public void onNext(List<String> updates) {
                Timber.d("onNext called");
                recentUpdatesFragment.displayUpdates(updates);
            }

            @Override public void onCompleted() {
                Timber.d("onComplete called");
            }

            @Override public void onError(Throwable throwable) {
                Timber.e("onError called", throwable);
            }
        };

        recentUpdatesObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(recentUpdatesObserver);
    }
}
