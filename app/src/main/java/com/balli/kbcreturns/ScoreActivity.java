package com.balli.kbcreturns;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.DecimalFormat;

import static java.lang.Long.valueOf;

public class ScoreActivity extends AppCompatActivity {

    TextView scoreView, percentageView, attemptView;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private DatabaseReference mDatabase, epref, yref;
    Long correct;
    Long total;
    String percentage;
    String attempts;
    AlertDialog.Builder builder;
    Boolean flag = false;
//    private AdView mAdView;

    String episodeAttempt;
    private static DecimalFormat df2 = new DecimalFormat(".##");


    public void retouch(View view) {
        if (flag == false) {
            flag = true;
            customDialogBuilder(view.getId());
        } else
            switchafterrate(view.getId());
    }

    public void customDialogBuilder(final int funcid) {
        builder.setMessage(R.string.rateus)
                .setTitle(R.string.ratetitle);

        builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                rateus();
            }
        });
        builder.setNegativeButton(R.string.later, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                switchafterrate(funcid);
            }
        });

        builder.setNeutralButton(R.string.done, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                switchafterrate(funcid);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void switchafterrate(int funcid) {
        switch (funcid) {

            case R.id.buttonRetake:
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
                break;

            case R.id.buttonEpisodeList:
                Intent intent1 = new Intent(getApplicationContext(), EpisodeListActivity.class);
                startActivity(intent1);
                break;

            case R.id.buttonHome:
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
                break;

            default:
                break;
        }
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

    public void token() {
        String tkn="aaa";

        if (FirebaseInstanceId.getInstance().getToken()!=null){
            tkn = FirebaseInstanceId.getInstance().getToken();
            FirebaseMessaging.getInstance().subscribeToTopic("allDevices");
        }
//        Toast.makeText(MainActivity.this, "Current token ["+tkn+"]",
//        Toast.LENGTH_LONG).show();
        Log.i("zoobie", "Token ["+tkn+"]");

        //below step is not mandate, just kept to keep track of devices opening app
        //topic is automatically created when subscribed to it,
        // topic is not a part of database, so can't be seen there
        mDatabase.child("token").child("allDevices").child(tkn).setValue(tkn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        builder = new AlertDialog.Builder(this);
//        customDialogBuilder();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        token();

        scoreView = (TextView) findViewById(R.id.textScore);
        percentageView = (TextView) findViewById(R.id.textPercentage);
        attemptView = (TextView) findViewById(R.id.textAttempt);
        percentage = "X";

        prefs = getSharedPreferences(
                "abc", Context.MODE_PRIVATE);

        editor = prefs.edit();

        correct = Long.valueOf(prefs.getString("Correct", "Invalid"));
        total = Long.valueOf(prefs.getString("Total", "Invalid"));
        String result = correct + "/" + total;

        percentage = prefs.getString("Percent", "Invalid percent");
        attempts = prefs.getString("Attempts", "Invalid number of attempts");

        String episodeName = prefs.getString("episodeName", "Episode not found");

        episodeAttempt = prefs.getString(episodeName, "false");
        if (episodeAttempt.equals("false"))
        {
            String profileName=prefs.getString("profileName", "Invalid");
            String profileAge=prefs.getString("profileAge", "Invalid");
            String profileCity=prefs.getString("profileCity", "Invalid");
            String profileGender=prefs.getString("profileGender", "Invalid");
            String profileCorrect=prefs.getString("profileCorrect", "Invalid");
            String profileAttempted=prefs.getString("profileAttempted", "Invalid");

            profileCorrect=Long.toString((Long.parseLong(profileCorrect)+correct));
            profileAttempted=Long.toString((Long.parseLong(profileAttempted)+total));

            double profilePercentage = ((double) Long.parseLong(profileCorrect) / Long.parseLong(profileAttempted)) * 100;
            String pct="";

            if (profileCorrect.equals("0"))
                pct="0";
            else
                pct=String.valueOf(df2.format(profilePercentage));

            editor.putString("profileCorrect", profileCorrect).apply();
            editor.putString("profileAttempted", profileAttempted).apply();
            editor.putString("profilePercentage", pct).apply();

            editor.putString(episodeName, "true").apply();
            editor.commit();

            //update database with new score for that unique id
            Registration r1=new Registration(profileName,profileAge,profileCity,profileGender,profileCorrect,profileAttempted,pct);
            String uniqueId = prefs.getString("uniqueId", "false");
            mDatabase.child("users").child(uniqueId).setValue(r1);
        }


        scoreView.setText(result);
        percentageView.setText("You have performed better than " + percentage + "% of the people");
        attemptView.setText("This quiz has been attempted by " + attempts + " people");

//        mAdView = (AdView) findViewById(R.id.adView3);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        production ad
//        ads:adUnitId="ca-app-pub-9621990942730139/9048347794"
//        test ad
//        ads:adUnitId="ca-app-pub-3940256099942544/6300978111"

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), EpisodeListActivity.class);
        startActivity(intent);
        return;
    }
}
