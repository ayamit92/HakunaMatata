package com.balli.kbcreturns;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class YearListActivity extends AppCompatActivity {
    //<!--android:background="@drawable/kbc3"-->
//    <!--android:backgroundTint="#80FFFFFF"-->
//    <!--android:backgroundTintMode="src_over"-->
    static ArrayList<String> yearList = new ArrayList<String>();
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
//    private AdView mAdView;

    public void selectyear(View view) {

        switch (view.getId()) {

            case R.id.year_view1: {
                Toast.makeText(getApplicationContext(), "Downloading the list of episodes for 2018", Toast.LENGTH_SHORT).show();
                editor.putInt("year", 2018).apply();
                editor.commit();
            }
            break;

            case R.id.year_view2:
                Toast.makeText(getApplicationContext(), "Downloading the list of episodes for 2017", Toast.LENGTH_SHORT).show();
                editor.putInt("year", 2017).apply();
                editor.commit();
                break;

            case R.id.year_view3:
                Toast.makeText(getApplicationContext(), "Downloading the list of episodes for 2014", Toast.LENGTH_SHORT).show();
                editor.putInt("year", 2014).apply();
                editor.commit();
                break;

            case R.id.year_view4:
                Toast.makeText(getApplicationContext(), "Downloading the list of episodes for 2013", Toast.LENGTH_SHORT).show();
                editor.putInt("year", 2013).apply();
                editor.commit();
                break;

            default:
                break;
        }

        final Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), EpisodeListActivity.class);
                startActivity(intent);
            }

        }, 1000L);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_list);

        prefs = getSharedPreferences(
                "abc", Context.MODE_PRIVATE);
        editor = prefs.edit();

//        mAdView = (AdView) findViewById(R.id.adView1);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        production ad
//        ads:adUnitId="ca-app-pub-9621990942730139/1036305454"
//        test ad
//        ads:adUnitId="ca-app-pub-3940256099942544/6300978111"

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        return;
    }
}
