package com.balli.kbc;

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
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Long.valueOf;

public class ScoreActivity extends AppCompatActivity {

    TextView scoreView, percentageView, attemptView;
    SharedPreferences prefs;
    private DatabaseReference mDatabase, epref, yref;
    Long correct;
    Long total;
    String percentage;
    String attempts;
    AlertDialog.Builder builder;
    Boolean flag = false;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        builder = new AlertDialog.Builder(this);
//        customDialogBuilder();

        scoreView = (TextView) findViewById(R.id.textScore);
        percentageView = (TextView) findViewById(R.id.textPercentage);
        attemptView = (TextView) findViewById(R.id.textAttempt);
        percentage = "X";

        prefs = getSharedPreferences(
                "abc", Context.MODE_PRIVATE);

        correct = Long.valueOf(prefs.getString("Correct", "Invalid"));
        total = Long.valueOf(prefs.getString("Total", "Invalid"));
        String result = correct + "/" + total;

        percentage = prefs.getString("Percent", "Invalid percent");
        attempts = prefs.getString("Attempts", "Invalid number of attempts");


        String episodeName = prefs.getString("episodeName", "Episode not found");

        scoreView.setText(result);
        percentageView.setText("You have performed better than " + percentage + "% of the people");
        attemptView.setText("This quiz has been attempted by " + attempts + " people");

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
