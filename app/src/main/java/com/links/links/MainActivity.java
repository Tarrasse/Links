package com.links.links;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.links.links.data.LinksContract;
import com.links.links.modules.News;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;


    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ValueEventListener mValueEventListener;


    public static String LAST_ITEM_SELECTED = "lis";
    private long mLastItemSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //for the toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        //for the floating action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null)
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactUsActivity.class);
                startActivity(intent);
            }
        });


        //attach the Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.inflateMenu(R.menu.activity_main_hero_menu);

        createValueEventListner();

        //firebase need a user to allow him override the database
        signInAsDefaultUser();

        //start the default fragment (NewsFragment)
        startLastFragmet(savedInstanceState);

        //download data from firebase and add listners
        downloadFormFireBase();

        String token = FirebaseInstanceId.getInstance().getToken();

    }

    private void createValueEventListner() {
        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children =  dataSnapshot.getChildren();
                getContentResolver().delete(LinksContract.NewsTable.CONTENT_URI, null, null);
                ArrayList<News> news = new ArrayList<>();
                for (DataSnapshot snapshot:
                        children) {
                    News temp ;
                    temp = snapshot.getValue(News.class);
                    news.add(temp);
                    Utility.insertNews(getApplicationContext(), temp);
                }
                Log.i("FireBase","length = " + news.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FireBase", "loadPost:onCancelled"+  databaseError.toString());
            }
        };

    }
    private void signInAsDefaultUser(){
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    mAuth.createUserWithEmailAndPassword(
                            BuildConfig.accountName,
                            BuildConfig.accountPassword
                    )
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(LOG_TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                    if (!task.isSuccessful()) {
                                        Log.i(LOG_TAG, "Could not connect");
                                        mAuth.signInWithEmailAndPassword(
                                                BuildConfig.accountName,
                                                BuildConfig.accountPassword)
                                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        Log.d(LOG_TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                                        if (!task.isSuccessful()) {
                                                            Log.w(LOG_TAG, "signInWithEmail:failed", task.getException());

                                                        }
                                                    }
                                                });
                                    }
                                }
                            });

                } else {
                    Log.d(LOG_TAG, "onAuthStateChanged:signed in");
                }
            }
        };
    }

    private void startLastFragmet(Bundle savedInstanceState){

        if (savedInstanceState == null) {
            mLastItemSelected = R.id.nav_home;
            NewsFragment newsFragment = new NewsFragment();
            mCollapsingToolbarLayout.setTitle("NEWS");
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(
                            R.id.main_activity_fragment_place_holder,
                            newsFragment
                    )
                    .commit();
        } else {
            mLastItemSelected = savedInstanceState.getLong(LAST_ITEM_SELECTED);
            if (mLastItemSelected == R.id.nav_home) {
                mCollapsingToolbarLayout.setTitle("NEWS");
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(
                                R.id.main_activity_fragment_place_holder,
                                new NewsFragment()
                        ).addToBackStack(null).commit();

            } else if (mLastItemSelected == R.id.nav_portfolio) {
                mCollapsingToolbarLayout.setTitle("PORTFOLIO");
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(
                                R.id.main_activity_fragment_place_holder,
                                new PortfoloFragment()
                        ).addToBackStack(null).commit();

            }
        }
    }

    private void downloadFormFireBase(){
        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference newsReference = mDatabase.child("news");

        FirebaseDatabase.getInstance().getReference("news").keepSynced(true);

        if (mValueEventListener != null)
            newsReference.addValueEventListener(mValueEventListener);
        else{
            createValueEventListner();
            newsReference.addValueEventListener(mValueEventListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        if (mValueEventListener != null){
            mDatabase.addValueEventListener(mValueEventListener);
        }else {
            createValueEventListner();
            mDatabase.addValueEventListener(mValueEventListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        mDatabase.addValueEventListener(mValueEventListener);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id != R.id.nav_share)
            mLastItemSelected = id;
        if (id == R.id.nav_home) {
            mCollapsingToolbarLayout.setTitle("NEWS");
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(
                            R.id.main_activity_fragment_place_holder,
                            new NewsFragment()
                    ).addToBackStack(null).commit();

        } else if (id == R.id.nav_services) {
            startActivity(new Intent(this, ServicesActivity.class));
        } else if (id == R.id.nav_portfolio) {
            mCollapsingToolbarLayout.setTitle("PORTFOLIO");
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(
                            R.id.main_activity_fragment_place_holder,
                            new PortfoloFragment()
                    ).addToBackStack(null).commit();

//        } else if (id == R.id.nav_WS_registration) {
//        } else if (id == R.id.nav_courses_registration) {
//
        } else if (id == R.id.nav_contact_us) {

            startActivity(new Intent(this, ContactUsActivity.class));

        } else if (id == R.id.nav_share) {
            Intent shareintent = new Intent(Intent.ACTION_SEND);
            shareintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareintent.setType("text/plain");
            shareintent.putExtra(
                    Intent.EXTRA_TEXT,
                    "visit links website \n" +
                            "www.links.com"
            );

            startActivity(shareintent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(LAST_ITEM_SELECTED, mLastItemSelected);
    }
    //
//    private boolean checkPlayServices() {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (apiAvailability.isUserResolvableError(resultCode)) {
//                Snackbar.make(mToolbar, "PLEASE UPDATE GOOGLE PLAY SERVICES", Snackbar.LENGTH_LONG)
//                        .setAction("UPDATE", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(Intent.ACTION_VIEW);
//                                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.gms&hl=en"));
//                                startActivity(intent);
//
//                            }
//                        }).show();
//            } else {
//                Log.i(LOG_TAG, "This device is not supported.");
//                finish();
//            }
//            return false;
//        }
//        return true;
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            android.os.Process.killProcess(android.os.Process.myPid());
            super.onBackPressed();
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Snackbar.make(mToolbar, "PLEASE UPDATE GOOGLE PLAY SERVICES", Snackbar.LENGTH_LONG)
                        .setAction("UPDATE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.gms&hl=en"));
                                startActivity(intent);

                            }
                        }).show();
            } else {
                Log.i(LOG_TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    public static class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {
        private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
        private boolean mIsAnimatingOut = false;

        public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
            super();
        }

        @Override
        public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                           final View directTargetChild, final View target, final int nestedScrollAxes) {
            // Ensure we react to vertical scrolling
            return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                    || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        }

        @Override
        public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                   final View target, final int dxConsumed, final int dyConsumed,
                                   final int dxUnconsumed, final int dyUnconsumed) {
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

            if (dyConsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
                // User scrolled down and the FAB is currently visible -> hide the FAB
                animateOut(child);
            } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
                // User scrolled up and the FAB is currently not visible -> show the FAB
                animateIn(child);
            }
        }

        // Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
        private void animateOut(final FloatingActionButton button) {
            if (Build.VERSION.SDK_INT >= 14) {
                ViewCompat.animate(button).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setInterpolator(INTERPOLATOR).withLayer()
                        .setListener(new ViewPropertyAnimatorListener() {
                            public void onAnimationStart(View view) {
                                ScrollAwareFABBehavior.this.mIsAnimatingOut = true;
                            }

                            public void onAnimationCancel(View view) {
                                ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                            }

                            public void onAnimationEnd(View view) {
                                ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                                view.setVisibility(View.GONE);
                            }
                        }).start();
            } else {
                Animation anim = AnimationUtils.loadAnimation(button.getContext(), R.anim.fab_out);
                anim.setInterpolator(INTERPOLATOR);
                anim.setDuration(200L);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut = true;
                    }

                    public void onAnimationEnd(Animation animation) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                        button.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(final Animation animation) {
                    }
                });
                button.startAnimation(anim);
            }
        }

        // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
        private void animateIn(FloatingActionButton button) {
            button.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= 14) {
                ViewCompat.animate(button).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                        .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                        .start();
            } else {
                Animation anim = AnimationUtils.loadAnimation(button.getContext(), R.anim.fab_in);
                anim.setDuration(200L);
                anim.setInterpolator(INTERPOLATOR);
                button.startAnimation(anim);
            }
        }
    }

}
