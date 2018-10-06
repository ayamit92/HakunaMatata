package com.ayamit92.kbc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    TextView score;

    SharedPreferences prefs;

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

        score = (TextView) findViewById(R.id.textScore);

        prefs = getSharedPreferences(
                "abc", Context.MODE_PRIVATE);

        String correct = prefs.getString("Correct", "Invalid");
        String total = prefs.getString("Total", "Invalid");
        String result=correct+"/"+total;

        score.setText(result);

        //checking git
    }
}
