package com.balli.kbcreturns;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AccountActivity extends AppCompatActivity {

    TextView textname, textunique, score, percentage, city, ag;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        prefs = getSharedPreferences(
                "abc", Context.MODE_PRIVATE);

        editor = prefs.edit();

        mAdView = (AdView) findViewById(R.id.adProfile);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
//        production ad
//        ads:adUnitId="ca-app-pub-9621990942730139/1059126223"
//        test ad
//        ads:adUnitId="ca-app-pub-3940256099942544/6300978111"

        textname = (TextView) findViewById(R.id.firstLine1);
        textunique = (TextView) findViewById(R.id.secondLine2);
        score = (TextView) findViewById(R.id.thirdLine3);
        percentage = (TextView) findViewById(R.id.fourthLine4);
        city = (TextView) findViewById(R.id.fifthLine5);
        ag = (TextView) findViewById(R.id.sixthLine6);


        String profileName=prefs.getString("profileName", "Invalid");
        String profileAge=prefs.getString("profileAge", "Invalid");
        String profileCity=prefs.getString("profileCity", "Invalid");
        String profileGender=prefs.getString("profileGender", "Invalid");
        String profileCorrect=prefs.getString("profileCorrect", "Invalid");
        String profileAttempted=prefs.getString("profileAttempted", "Invalid");
        String profileUniqueId=prefs.getString("uniqueId", "Invalid");
        String profilePercentage=prefs.getString("profilePercentage", "Invalid");

        if (profileName.length()>15)
            textname.setText(profileName.substring(0,15)+".");
        else
            textname.setText(profileName);

        if (profileUniqueId.length()>15)
            textunique.setText("("+profileUniqueId.substring(0,15)+"."+")");
        else
            textunique.setText("("+profileUniqueId+")");

        if (profileCity.length()>15)
            city.setText(profileCity.substring(0,15)+".");
        else
            city.setText(profileCity);

        score.setText(profileCorrect+"/"+profileAttempted);
        percentage.setText(profilePercentage+"%");
        ag.setText(profileAge+", "+profileGender);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        return;
    }
}
