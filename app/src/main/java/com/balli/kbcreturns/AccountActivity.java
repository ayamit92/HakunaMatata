package com.balli.kbcreturns;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {

    TextView textname, textunique, asl, correct, attempted, percentage;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        prefs = getSharedPreferences(
                "abc", Context.MODE_PRIVATE);

        editor = prefs.edit();

        textname = (TextView) findViewById(R.id.textname);
        textunique = (TextView) findViewById(R.id.textunique);
        asl = (TextView) findViewById(R.id.asl);
        correct = (TextView) findViewById(R.id.correct);
        attempted = (TextView) findViewById(R.id.attempted);
        percentage = (TextView) findViewById(R.id.percentage);


        String profileName=prefs.getString("profileName", "Invalid");
        String profileAge=prefs.getString("profileAge", "Invalid");
        String profileCity=prefs.getString("profileCity", "Invalid");
        String profileGender=prefs.getString("profileGender", "Invalid");
        String profileCorrect=prefs.getString("profileCorrect", "Invalid");
        String profileAttempted=prefs.getString("profileAttempted", "Invalid");
        String profileUniqueId=prefs.getString("uniqueId", "Invalid");
        String profilePercentage=prefs.getString("profilePercentage", "Invalid");

        textname.setText(profileName);
        textunique.setText("(Unique Id: "+profileUniqueId+")");
        asl.setText(profileAge+", "+profileGender+", "+profileCity);
        correct.setText("Total Correct Answers: "+profileCorrect);
        attempted.setText("Total Questions Attempted: "+profileAttempted);
        percentage.setText("Percentage of Correct: "+profilePercentage+"%");

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        return;
    }
}
