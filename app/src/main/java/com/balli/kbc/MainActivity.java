package com.balli.kbc;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    Button qBankButton;
    private DatabaseReference mDatabase, epref, yref;
    private static boolean RUN_ONCE = true;

    static ArrayList<String> episodeList = new ArrayList<String>();
    static HashMap<String, ArrayList<Question>> episodeQuestionMap = new HashMap<String, ArrayList<Question>>();

    static ArrayList<String> episodeList2018 = new ArrayList<String>();
    static HashMap<String, ArrayList<Question>> episodeQuestionMap2018 = new HashMap<String, ArrayList<Question>>();
    static ArrayList<String> episodeList2017 = new ArrayList<String>();
    static HashMap<String, ArrayList<Question>> episodeQuestionMap2017 = new HashMap<String, ArrayList<Question>>();
    static ArrayList<String> episodeList2014 = new ArrayList<String>();
    static HashMap<String, ArrayList<Question>> episodeQuestionMap2014 = new HashMap<String, ArrayList<Question>>();
    static ArrayList<String> episodeList2013 = new ArrayList<String>();
    static HashMap<String, ArrayList<Question>> episodeQuestionMap2013 = new HashMap<String, ArrayList<Question>>();

    public ValueEventListener episodeList(final String year) {
        ValueEventListener nextEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String episodeName = "";
                episodeList.clear();
                episodeQuestionMap.clear();
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    episodeName = String.valueOf(uniqueUserSnapshot.getKey());
                    episodeList.add(episodeName);
                    Log.i("episodeName", episodeName);
                    //above, key is retrieved and not value
                    yref = mDatabase.child(year).child(episodeName);
                    yref.addListenerForSingleValueEvent(questionList(episodeName));
                }

                if (year.equals("2018")){
                    episodeList2018=episodeList;
                    episodeQuestionMap2018=episodeQuestionMap;}

                if (year.equals("2017")){
                    episodeList2017=episodeList;
                    episodeQuestionMap2017=episodeQuestionMap;}

                if (year.equals("2014")){
                    episodeList2014=episodeList;
                    episodeQuestionMap2014=episodeQuestionMap;}

                if (year.equals("2013")){
                    episodeList2013=episodeList;
                    episodeQuestionMap2013=episodeQuestionMap;}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        return nextEventListener;
    }

    public ValueEventListener questionList(final String episodeName) {
        ValueEventListener nextEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Question> questionList = new ArrayList<Question>();
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    questionList.add(uniqueUserSnapshot.getValue(Question.class));
                }
                episodeQuestionMap.put(episodeName, questionList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        return nextEventListener;
    }

    public void questionbank(View view) {
        Intent intent = new Intent(getApplicationContext(), YearListActivity.class);
        startActivity(intent);

////        handler is required as next activity gets started even before data is populated, so 1s pause time is applied
//        final Handler mHandler = new Handler();
//        mHandler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
////                         Intent intent=new Intent (getApplicationContext(),YearListActivity.class);
//                           startActivity(intent);
//            }
//
//        }, 1000L);
    }

    public void motivate(View view) {
        Intent intent = new Intent(getApplicationContext(), MotivationalNonCloudActivity.class);
        startActivity(intent);
    }

    public void rateus() {
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        setNavigationViewListener();
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        MobileAds.initialize(this, "ca-app-pub-9621990942730139~3204585053");
        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        runOnce();
        //test
    }


    private void runOnce() {
        if (RUN_ONCE) {
            RUN_ONCE = false;

            episodeList2018.clear();
            episodeQuestionMap2018.clear();
            episodeList2017.clear();
            episodeQuestionMap2017.clear();
            episodeList2014.clear();
            episodeQuestionMap2014.clear();
            episodeList2013.clear();
            episodeQuestionMap2013.clear();

            epref = mDatabase.child("2018");
            epref.addListenerForSingleValueEvent(episodeList("2018"));

            epref = mDatabase.child("2017");
            epref.addListenerForSingleValueEvent(episodeList("2017"));

            epref = mDatabase.child("2014");
            epref.addListenerForSingleValueEvent(episodeList("2014"));

            epref = mDatabase.child("2013");
            epref.addListenerForSingleValueEvent(episodeList("2013"));

            MediaPlayer ring = MediaPlayer.create(MainActivity.this, R.raw.ring);
            ring.start();
        }
    }

    //    populating app bar with different options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarbuttons, menu);
        return true;
    }

    //    onClickListener for app bar options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_button:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
    }

    //    onClickListener for navigation drawer items
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.qb: {
                Intent intent = new Intent(getApplicationContext(), YearListActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.ms: {
                Intent intent = new Intent(getApplicationContext(), MotivationalNonCloudActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.ru: {
                rateus();
                break;
            }
        }
        //close navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}
