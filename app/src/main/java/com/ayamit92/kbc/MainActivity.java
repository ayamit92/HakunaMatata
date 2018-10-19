package com.ayamit92.kbc;

import android.app.ActionBar;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
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
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    Button qBankButton;
    private DatabaseReference mDatabase, epref, yref;

    static ArrayList<String> episodeList = new ArrayList<String>();
    static HashMap<String, ArrayList<Question>> episodeQuestionMap = new HashMap<String, ArrayList<Question>>();

    public ValueEventListener episodeList() {
        ValueEventListener nextEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String episodeName = "";
                episodeList.clear();
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    episodeName = String.valueOf(uniqueUserSnapshot.getKey());
                    episodeList.add(episodeName);
                    Log.i("episodeName", episodeName);
                    //above, key is retrieved and not value
                    yref = mDatabase.child("2018").child(episodeName);
                    yref.addListenerForSingleValueEvent(questionList(episodeName));
                }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        qBankButton = (Button) findViewById(R.id.button3);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        epref = mDatabase.child("2018");
        epref.addListenerForSingleValueEvent(episodeList());

//        MediaPlayer ring = MediaPlayer.create(MainActivity.this, R.raw.ring);
//        ring.start();

//        Navigation drawer

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarbuttons, menu);
        return true;
    }

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

}
