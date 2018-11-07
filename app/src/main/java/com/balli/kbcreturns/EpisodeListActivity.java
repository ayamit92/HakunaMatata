package com.balli.kbcreturns;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;


public class EpisodeListActivity extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private DatabaseReference mDatabase;

    ArrayList<String> epsLst = new ArrayList<String>();

    int year;

    boolean emptylist = false;

//    private AdView mAdView;

    public void token() {
        String tkn = "aaa";

        if (FirebaseInstanceId.getInstance().getToken() != null) {
            tkn = FirebaseInstanceId.getInstance().getToken();
            FirebaseMessaging.getInstance().subscribeToTopic("allDevices");
        }
//        Toast.makeText(MainActivity.this, "Current token ["+tkn+"]",
//        Toast.LENGTH_LONG).show();
        Log.i("zoobie", "Token [" + tkn + "]");

        //below step is not mandate, just kept to keep track of devices opening app
        //topic is automatically created when subscribed to it,
        // topic is not a part of database, so can't be seen there
        mDatabase.child("token").child("allDevices").child(tkn).setValue(tkn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_list);

//        mAdView = (AdView) findViewById(R.id.adEpisodeList);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        production ad
//        ads:adUnitId="ca-app-pub-9621990942730139/9096307038"
//        test ad
//        ads:adUnitId="ca-app-pub-3940256099942544/6300978111"

        mDatabase = FirebaseDatabase.getInstance().getReference();
        epsLst.clear();
        token();
        ListView listView = (ListView) findViewById(R.id.episodelist);

        prefs = getSharedPreferences(
                "abc", Context.MODE_PRIVATE);
        editor = prefs.edit();

        year = prefs.getInt("year", -1);

        if (year == 2018) {
            epsLst = MainActivity.episodeList2018;
            Log.i("episodeact2018",epsLst.get(0));
        }

        if (year == 2017) {
            epsLst = MainActivity.episodeList2017;
            Log.i("episodeact2017",epsLst.get(0));
        }

        if (year == 2014) {
            epsLst = MainActivity.episodeList2014;
            Log.i("episodeact2014",epsLst.get(0));
        }

        if (year == 2013) {
            epsLst = MainActivity.episodeList2013;
            Log.i("episodeact2013",epsLst.get(0));
        }

        if (epsLst.size() == 0) {
            epsLst.add("Internet seems to be slow or off, try coming back!");
            emptylist = true;
        }

        //default color in list view for text is black, changing it to white
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, epsLst) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };
        ;

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if (!emptylist) {
                    Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                    //intent.putExtra("episodeNumber",i);
                    editor.putInt("episodeNumber", i).apply();
                    editor.commit();
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        return;
    }
}
