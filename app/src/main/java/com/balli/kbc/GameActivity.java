package com.balli.kbc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Long.valueOf;

//just after initiating game activity we can load all historical score data in a map <score, frequency>, using this we can remove handler
public class GameActivity extends AppCompatActivity {

    Button next, optiona, optionb, optionc, optiond;
    LinearLayout ques;
    TextView question, questionNumber;
    int count = 0;
    int correct = 0;
    int episodelistid;
    String episodeName = "";
    boolean flag = false;
    boolean clicked = false;
    double percentage;
    private DatabaseReference mDatabase, epref;
    long currentCount = 0;
    ArrayList<Long> submissions = new ArrayList<Long>();
    static HashMap<Integer, Long> submissionsMap = new HashMap<Integer, Long>();
    long score = 1;
    long scorePlus = 0;
    private static DecimalFormat df2 = new DecimalFormat(".##");
    private static boolean interstitialad;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    String year;

    ArrayList<String> epsLstDynamic = new ArrayList<String>();
    static HashMap<String, ArrayList<Question>> episodeQuestionMapDynamic = new HashMap<String, ArrayList<Question>>();

    public void nextfun(View view) {

        if (count < episodeQuestionMapDynamic.get(episodeName).size() - 1) {
            if (clicked) {
                setValues(++count);
                clicked = false;
                flag = false;
            } else {
                Toast.makeText(getApplicationContext(), "Please select an option!", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (interstitialad == false) {
                interstitialad = true;
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.i("TAG", "The interstitial wasn't loaded yet.");
                }

                editor.putString("Correct", String.valueOf(correct)).apply();
                editor.putString("Total", String.valueOf(count + 1)).apply();
                editor.commit();

                if (submissionsMap.get(correct) != null)
                    currentCount = submissionsMap.get(correct);
                else
                    currentCount = 0;

                for (Map.Entry<Integer, Long> entry : submissionsMap.entrySet()) {
                    score = score + entry.getValue();
                    if (entry.getKey() < correct)
                        scorePlus = scorePlus + entry.getValue();
                }


                Log.i("score", String.valueOf(score));
                Log.i("scorePlus", String.valueOf(scorePlus));

                percentage = ((double) scorePlus / score) * 100;
                Log.i("percentage1", String.valueOf(df2.format(percentage)));
                if (correct == 0)
                    editor.putString("Percent", "0").apply();
                else
                    editor.putString("Percent", String.valueOf(df2.format(percentage))).apply();
                editor.putString("Attempts", String.valueOf(score)).apply();
                editor.commit();

                Log.i("currentCount", String.valueOf(currentCount));
                mDatabase.child("submissions").child(year).child(episodeName).child(String.valueOf(correct)).setValue(currentCount + 1);
            }
// Not starting the activity directly and putting a hold of 2sec so that interstitial ad is visible, otherwise the ad will come on
// game screen and we would have switched to score screen
//            Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
//            startActivity(intent);

            else {
                Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
                startActivity(intent);
            }

        }
    }

    //      multiple buttons calling same function
//    identifying them by their id, and doing customized operations
    public void response(View view) {
        if (!clicked) {

            switch (view.getId()) {

                case R.id.button4:
                    if (((String) optiona.getText()).equals(episodeQuestionMapDynamic.get(episodeName).get(count).getAnswer())) {
                        optiona.setBackgroundResource(R.drawable.angrytoolbackground4);
                        flag = true;
                    } else {
                        optiona.setBackgroundResource(R.drawable.angrytoolbackground3);
                    }
                    highlightcorrect();
                    break;

                case R.id.button5:
                    if (((String) optionb.getText()).equals(episodeQuestionMapDynamic.get(episodeName).get(count).getAnswer())) {
                        optionb.setBackgroundResource(R.drawable.angrytoolbackground4);
                        flag = true;
                    } else {
                        optionb.setBackgroundResource(R.drawable.angrytoolbackground3);
                    }
                    highlightcorrect();
                    break;

                case R.id.button6:
                    if (((String) optionc.getText()).equals(episodeQuestionMapDynamic.get(episodeName).get(count).getAnswer())) {
                        optionc.setBackgroundResource(R.drawable.angrytoolbackground4);
                        flag = true;
                    } else {
                        optionc.setBackgroundResource(R.drawable.angrytoolbackground3);
                    }
                    highlightcorrect();
                    break;

                case R.id.button7:
                    if (((String) optiond.getText()).equals(episodeQuestionMapDynamic.get(episodeName).get(count).getAnswer())) {
                        optiond.setBackgroundResource(R.drawable.angrytoolbackground4);
                        flag = true;
                    } else {
                        optiond.setBackgroundResource(R.drawable.angrytoolbackground3);
                    }
                    highlightcorrect();
                    break;

                default:
                    break;
            }

            clicked = true;

            if (flag == true) {
//                answer.setText("Right Answer");
                correct++;
            } else {
//                answer.setText("Wrong Answer");
            }

//            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.nextanim);
//            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
//            myAnim.setInterpolator(interpolator);
//            next.startAnimation(myAnim);
            next.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        }

    }

    public void setValues(int count) {
        optiona.setText(episodeQuestionMapDynamic.get(episodeName).get(count).getOptionA());
        optionb.setText(episodeQuestionMapDynamic.get(episodeName).get(count).getOptionB());
        optionc.setText(episodeQuestionMapDynamic.get(episodeName).get(count).getOptionC());
        optiond.setText(episodeQuestionMapDynamic.get(episodeName).get(count).getOptionD());
        question.setText(Integer.toString(count + 1) + ". " + episodeQuestionMapDynamic.get(episodeName).get(count).getQuestion());
        questionNumber.setText("[" + Integer.toString(count + 1) + "/" + episodeQuestionMapDynamic.get(episodeName).size() + "]");

        optiona.setBackgroundResource(R.drawable.toolbarpurpround40);
        optionb.setBackgroundResource(R.drawable.toolbarpurpround40);
        optionc.setBackgroundResource(R.drawable.toolbarpurpround40);
        optiond.setBackgroundResource(R.drawable.toolbarpurpround40);

        next.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

//        final Animation q = AnimationUtils.loadAnimation(this, R.anim.buttonanim);
        final Animation op1 = AnimationUtils.loadAnimation(this, R.anim.buttonanim);
        final Animation op2 = AnimationUtils.loadAnimation(this, R.anim.buttonanim2);
        final Animation op3 = AnimationUtils.loadAnimation(this, R.anim.buttonanim);
        final Animation op4 = AnimationUtils.loadAnimation(this, R.anim.buttonanim2);

        op1.setStartOffset(500);
        op2.setStartOffset(500);
        op3.setStartOffset(500);
        op4.setStartOffset(500);

        //ques.startAnimation(q);
        optiona.startAnimation(op1);
        optionb.startAnimation(op2);
        optionc.startAnimation(op3);
        optiond.startAnimation(op4);

//        myAnim.setAnimationListener(anim(myAnim,optionb));
    }

    public void highlightcorrect() {
        if (((String) optiona.getText()).equals(episodeQuestionMapDynamic.get(episodeName).get(count).getAnswer()))
            optiona.setBackgroundResource(R.drawable.angrytoolbackground4);
        if (((String) optionb.getText()).equals(episodeQuestionMapDynamic.get(episodeName).get(count).getAnswer()))
            optionb.setBackgroundResource(R.drawable.angrytoolbackground4);
        if (((String) optionc.getText()).equals(episodeQuestionMapDynamic.get(episodeName).get(count).getAnswer()))
            optionc.setBackgroundResource(R.drawable.angrytoolbackground4);
        if (((String) optiond.getText()).equals(episodeQuestionMapDynamic.get(episodeName).get(count).getAnswer()))
            optiond.setBackgroundResource(R.drawable.angrytoolbackground4);
    }

    public ValueEventListener scoreList() {
        ValueEventListener nextEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    submissionsMap.put(Integer.parseInt(uniqueUserSnapshot.getKey()), (Long) uniqueUserSnapshot.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        return nextEventListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        interstitialad = false;

        mAdView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
//        production ad
//        ads:adUnitId="ca-app-pub-9621990942730139/5905488750"
//        test ad
//        ads:adUnitId="ca-app-pub-3940256099942544/6300978111"

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//        production ad
//        mInterstitialAd.setAdUnitId("ca-app-pub-9621990942730139/5144352680");
//        test add
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");


        next = (Button) findViewById(R.id.button9);
        optiona = (Button) findViewById(R.id.button4);
        optionb = (Button) findViewById(R.id.button5);
        optionc = (Button) findViewById(R.id.button6);
        optiond = (Button) findViewById(R.id.button7);

        question = (TextView) findViewById(R.id.textView);
        questionNumber = (TextView) findViewById(R.id.textQuestionNumber);
        ques = findViewById(R.id.kb);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        prefs = getSharedPreferences(
                "abc", Context.MODE_PRIVATE);
        editor = prefs.edit();

        submissionsMap.clear();

        episodelistid = prefs.getInt("episodeNumber", -1);
        year = Integer.toString(prefs.getInt("year", -1));

        if (year.equals("2018")) {
            epsLstDynamic = MainActivity.episodeList2018;
            episodeQuestionMapDynamic=MainActivity.episodeQuestionMap2018;
        }

        if (year.equals("2017")) {
            epsLstDynamic = MainActivity.episodeList2017;
            episodeQuestionMapDynamic=MainActivity.episodeQuestionMap2017;
        }

        if (year.equals("2014")) {
            epsLstDynamic = MainActivity.episodeList2014;
            episodeQuestionMapDynamic=MainActivity.episodeQuestionMap2014;
        }

        if (year.equals("2013")) {
            epsLstDynamic = MainActivity.episodeList2013;
            episodeQuestionMapDynamic=MainActivity.episodeQuestionMap2013;
        }

        if (episodelistid != -1) {
            Log.i("habibi2","habibi2");
            episodeName = epsLstDynamic.get(episodelistid);
            editor.putString("episodeName", episodeName).apply();
            editor.commit();
        }

        epref = mDatabase.child("submissions").child(year).child(episodeName);
        epref.addListenerForSingleValueEvent(scoreList());

        if (episodeQuestionMapDynamic.get(episodeName).size() == 0) {
            Toast.makeText(getApplicationContext(), "Try reloading the episode", Toast.LENGTH_LONG).show();
        } else {
            setValues(count);
        }


    }
}

//      making a function, so could be called from anywhere
//      everytime function is called, value is retrieved from firebase, thus making switching between questions a bit slow (especially loading the first question),
//      as an alternative all questions are now downloaded at beginning only
//    public ValueEventListener initial_set()
//    {
//        ValueEventListener nextEventListener=new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Question qq = dataSnapshot.getValue(Question.class);
//                question.setText(qq.getQuestion());
//                answer.setText("??");
//                optiona.setText(qq.getOptionA());
//                optionb.setText(qq.getOptionB());
//                optionc.setText(qq.getOptionC());
//                optiond.setText(qq.getOptionD());
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        };
//        return nextEventListener;
//    }


// retrieving single value like string/long corresponding to a particular node
//    public ValueEventListener getCurrentCount() {
//        ValueEventListener nextEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                currentCount = (long) dataSnapshot.getValue();
//                Log.i("snapshot",dataSnapshot.toString());
//                Log.i("snapshot1", String.valueOf(currentCount));
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        };
//        return nextEventListener;
//    }


//    public Animation.AnimationListener anim(final Animation myAnim, final Button button) {
//        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                button.startAnimation(myAnim);
//            }
//        };
//        return animationListener;
//    }



