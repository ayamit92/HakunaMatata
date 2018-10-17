package com.ayamit92.kbc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Long.valueOf;

public class ScoreActivity extends AppCompatActivity {

    TextView scoreView,percentageView;
    SharedPreferences prefs;
    private DatabaseReference mDatabase, epref, yref;
    Long correct;
    Long total;
    String percentage;

    public void retakeQuiz(View view){
        Intent intent=new Intent (getApplicationContext(),GameActivity.class);
        startActivity(intent);
    }

    public void episodeList(View view){
        Intent intent = new Intent(getApplicationContext(), EpisodeListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);



        scoreView = (TextView) findViewById(R.id.textScore);
        percentageView = (TextView) findViewById(R.id.textPercentage);
        percentage="X";

        prefs = getSharedPreferences(
                "abc", Context.MODE_PRIVATE);

        correct = Long.valueOf(prefs.getString("Correct", "Invalid"));
        total = Long.valueOf(prefs.getString("Total", "Invalid"));
        String result=correct+"/"+total;

        percentage = prefs.getString("Percent", "Invalid Percent");

        String episodeName = prefs.getString("episodeName", "Episode not found");

        scoreView.setText(result);
        percentageView.setText("You are better than "+percentage+"% of the people");

    }
}
