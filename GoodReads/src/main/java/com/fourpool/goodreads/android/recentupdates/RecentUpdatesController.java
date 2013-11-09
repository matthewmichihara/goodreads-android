package com.fourpool.goodreads.android.recentupdates;

import com.fourpool.goodreads.android.model.Actor;
import com.fourpool.goodreads.android.model.SessionStore;
import com.fourpool.goodreads.android.model.Update;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
    private final SessionStore sessionStore;

    @Inject RecentUpdatesController(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    public void onCreateView(final RecentUpdatesFragment recentUpdatesFragment) {
        Observable recentUpdatesObservable = Observable.create(new Observable.OnSubscribeFunc() {
            @Override public Subscription onSubscribe(Observer observer) {
                try {
                    URL url = new URL("https://www.goodreads.com/updates/friends.xml");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    OAuthConsumer consumer = sessionStore.getSession().getConsumer();
                    consumer.sign(connection);
                    connection.connect();

                    List<Update> updates = new ArrayList<Update>();

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = factory.newDocumentBuilder();
                    Document doc = documentBuilder.parse(connection.getInputStream());

                    NodeList nodeList = doc.getElementsByTagName("updates").item(0).getChildNodes();
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node updateNode = nodeList.item(i);

                        if (updateNode.getNodeType() != Node.ELEMENT_NODE) {
                            continue;
                        }

                        String updateType = ((Element) updateNode).getAttribute("type");
                        String imageUrl = null;
                        Actor actor = null;

                        NodeList updateElementChildren = updateNode.getChildNodes();
                        for (int j = 0; j < updateElementChildren.getLength(); j++) {
                            Node updateChild = updateElementChildren.item(j);

                            if (updateChild.getNodeName().equals("image_url")) {
                                imageUrl = updateChild.getLastChild().getTextContent().trim();
                            } else if (updateChild.getNodeName().equals("actor")) {
                                NodeList actorChildNodes = updateChild.getChildNodes();

                                int id = -1;
                                String name = null;
                                String actorImageUrl = null;

                                for (int k = 0; k < actorChildNodes.getLength(); k++) {
                                    Node actorProperty = actorChildNodes.item(k);

                                    if (actorProperty.getNodeName().equals("id")) {
                                        id = Integer.parseInt(actorProperty.getLastChild().getTextContent());
                                    } else if (actorProperty.getNodeName().equals("name")) {
                                        name = actorProperty.getLastChild().getTextContent();
                                    } else if (actorProperty.getNodeName().equals("image_url")) {
                                        actorImageUrl = actorProperty.getLastChild().getTextContent();
                                    }
                                }

                                actor = new Actor(id, name, actorImageUrl);
                            }
                        }

                        Update update = new Update(updateType, imageUrl, actor);
                        updates.add(update);
                    }

                    observer.onNext(updates);
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        });

        Observer<List<Update>> recentUpdatesObserver = new Observer<List<Update>>() {
            @Override public void onNext(List<Update> updates) {
                Timber.d("onNext called");
                recentUpdatesFragment.displayUpdates(updates);
            }

            @Override public void onCompleted() {
                Timber.d("onComplete called");
            }

            @Override public void onError(Throwable throwable) {
                Timber.e(throwable, "onError called");
            }
        };

        recentUpdatesObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(recentUpdatesObserver);
    }
}
