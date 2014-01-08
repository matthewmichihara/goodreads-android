package com.fourpool.goodreads.android;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.fourpool.goodreads.android.event.OAuthVerifierFetchedEvent;
import com.fourpool.goodreads.android.event.SessionCreatedEvent;
import com.fourpool.goodreads.android.login.LogInFragment;
import com.fourpool.goodreads.android.model.SessionStore;
import com.fourpool.goodreads.android.recentupdates.RecentUpdatesFragment;
import com.fourpool.goodreads.android.shelves.ShelvesFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;
import oauth.signpost.OAuth;
import timber.log.Timber;

import static com.fourpool.goodreads.android.Constants.CALLBACK_SCHEME;

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    @Inject Bus bus;
    @Inject SessionStore sessionStore;

    @InjectView(R.id.drawer_layout) DrawerLayout drawer;

    private NavigationDrawerFragment navigationDrawerFragment;
    private CharSequence title;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject ourself into the object graph.
        ((GoodReadsApplication) getApplication()).inject(this);

        setContentView(R.layout.activity_main);
        Views.inject(this);

        navigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        navigationDrawerFragment.setUp(R.id.navigation_drawer, drawer);

        if (sessionStore.getSession() == null) {
            displayLogIn();
            return;
        }
    }

    @Override protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Timber.d("onNewIntent called");

        final Uri uri = intent.getData();
        if (uri != null
                && uri.getScheme().equals(CALLBACK_SCHEME)) {

            final String oauthVerifier = uri
                    .getQueryParameter(OAuth.OAUTH_TOKEN);

            Timber.d("Extracted OAuth verifier: %s", oauthVerifier);
            bus.post(new OAuthVerifierFetchedEvent(oauthVerifier));
        }
    }

    @Override public void onNavigationDrawerItemSelected(int position) {
        Timber.d("onNavigationDrawerItemSelected called with position: %d", position);

        if (sessionStore.getSession() == null) {
            Timber.d("Session is null, bailing early");
            return;
        }

        switch (position) {
            case 0:
                displayFragment(new RecentUpdatesFragment());
                title = getString(R.string.title_updates);
                break;
            case 1:
                displayFragment(new ShelvesFragment());
                title = getString(R.string.title_my_shelves);
                break;
            case 2:
                displayFragment(new RecentUpdatesFragment());
                title = getString(R.string.title_friends);
                break;
            default:
                Timber.e("Unknown position: %d", position);
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        if (!navigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe public void onSessionCreatedEvent(SessionCreatedEvent event) {
        getActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        displayFragment(new RecentUpdatesFragment());
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }

    private void displayFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment container with this fragment, and add the transaction to the back stack
        // so the user can navigate back.
        transaction.replace(R.id.fragment_container, fragment);

        // Commit the transaction.
        transaction.commit();
    }

    private void displayLogIn() {
        displayFragment(new LogInFragment());
        getActionBar().hide();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
}
