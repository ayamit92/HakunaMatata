package com.ayamit92.kbc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Long.valueOf;


public class GameActivity extends AppCompatActivity {

    Button next, optiona, optionb, optionc, optiond;
    TextView question, answer;
    int count = 0;
    int correct = 0;
    int episodelistid;
    String episodeName = "";
    boolean flag = false;
    boolean clicked = false;
    double percentage;
    private DatabaseReference mDatabase, epref, yref;
    long currentCount=0;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    public void nextfun(View view) {

        if (count < MainActivity.episodeQuestionMap.get(episodeName).size() - 1) {
//          epref.child(Integer.toString(++count)).addListenerForSingleValueEvent(initial_set());
            if (clicked) {
                setValues(++count);
                clicked = false;
                flag = false;
            } else {
                Toast.makeText(getApplicationContext(), "Please select an option!", Toast.LENGTH_SHORT).show();
            }
        } else {
            editor.putString("Correct", String.valueOf(correct)).apply();
            editor.putString("Total", String.valueOf(count + 1)).apply();
            editor.commit();

            yref= mDatabase.child("submissions").child("2018").child(episodeName).child(String.valueOf(correct));
            yref.addListenerForSingleValueEvent(getCurrentCount());
            epref.addListenerForSingleValueEvent(scoreList());

            final Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Log.i("currentCount", String.valueOf(currentCount));
                    mDatabase.child("submissions").child("2018").child(episodeName).child(String.valueOf(correct)).setValue(currentCount+1);
                    Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
                    startActivity(intent);
                }

            }, 3000L);

        }
    }

    //    multiple buttons calling same function
//    identifying them by their id, and doing customized operations
    public void response(View view) {
        if (!clicked) {

            switch (view.getId()) {

                case R.id.button4:
                    if (((String) optiona.getText()).equals(MainActivity.episodeQuestionMap.get(episodeName).get(count).getAnswer())) {
                        optiona.setBackgroundResource(R.drawable.angrytoolbackground4);
                        flag = true;
                    } else {
                        optiona.setBackgroundResource(R.drawable.angrytoolbackground3);
                    }
                    highlightcorrect();
                    break;

                case R.id.button5:
                    if (((String) optionb.getText()).equals(MainActivity.episodeQuestionMap.get(episodeName).get(count).getAnswer())) {
                        optionb.setBackgroundResource(R.drawable.angrytoolbackground4);
                        flag = true;
                    } else {
                        optionb.setBackgroundResource(R.drawable.angrytoolbackground3);
                    }
                    highlightcorrect();
                    break;

                case R.id.button6:
                    if (((String) optionc.getText()).equals(MainActivity.episodeQuestionMap.get(episodeName).get(count).getAnswer())) {
                        optionc.setBackgroundResource(R.drawable.angrytoolbackground4);
                        flag = true;
                    } else {
                        optionc.setBackgroundResource(R.drawable.angrytoolbackground3);
                    }
                    highlightcorrect();
                    break;

                case R.id.button7:
                    if (((String) optiond.getText()).equals(MainActivity.episodeQuestionMap.get(episodeName).get(count).getAnswer())) {
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
                answer.setText("Right Answer");
                correct++;
            } else {
                answer.setText("Wrong Answer");
            }
        }

    }

    public void setValues(int count) {
        optiona.setText(MainActivity.episodeQuestionMap.get(episodeName).get(count).getOptionA());
        optionb.setText(MainActivity.episodeQuestionMap.get(episodeName).get(count).getOptionB());
        optionc.setText(MainActivity.episodeQuestionMap.get(episodeName).get(count).getOptionC());
        optiond.setText(MainActivity.episodeQuestionMap.get(episodeName).get(count).getOptionD());
        question.setText(Integer.toString(count + 1) + ". " + MainActivity.episodeQuestionMap.get(episodeName).get(count).getQuestion());
        answer.setText("");

        //optiona.setBackgroundColor(0xFFD3D3D3);
        optiona.setBackgroundResource(R.drawable.angrytoolbackground2);
        optionb.setBackgroundResource(R.drawable.angrytoolbackground2);
        optionc.setBackgroundResource(R.drawable.angrytoolbackground2);
        optiond.setBackgroundResource(R.drawable.angrytoolbackground2);
    }

    public void highlightcorrect() {
        if (((String) optiona.getText()).equals(MainActivity.episodeQuestionMap.get(episodeName).get(count).getAnswer()))
            optiona.setBackgroundResource(R.drawable.angrytoolbackground4);
        if (((String) optionb.getText()).equals(MainActivity.episodeQuestionMap.get(episodeName).get(count).getAnswer()))
            optionb.setBackgroundResource(R.drawable.angrytoolbackground4);
        if (((String) optionc.getText()).equals(MainActivity.episodeQuestionMap.get(episodeName).get(count).getAnswer()))
            optionc.setBackgroundResource(R.drawable.angrytoolbackground4);
        if (((String) optiond.getText()).equals(MainActivity.episodeQuestionMap.get(episodeName).get(count).getAnswer()))
            optiond.setBackgroundResource(R.drawable.angrytoolbackground4);
    }

    public ValueEventListener scoreList() {
        ValueEventListener nextEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long score = 0;
                long scorePlus=0;
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    score = score+valueOf((Long)uniqueUserSnapshot.getValue());
                    Log.i("scoreKey", String.valueOf(Long.valueOf(uniqueUserSnapshot.getKey())));
                    Log.i("scoreValue", String.valueOf(Long.valueOf((Long) uniqueUserSnapshot.getValue())));
                    Log.i("score", String.valueOf(score));
                    Log.i("scorePlus", String.valueOf(scorePlus));
                    if (Long.valueOf(uniqueUserSnapshot.getKey())<correct) {
                        scorePlus = scorePlus + Long.valueOf((Long) uniqueUserSnapshot.getValue());
                    }
                }

                percentage=(scorePlus*100)/score;
                Log.i("percentage1", String.valueOf(percentage));
                editor.putString("Percent", String.valueOf(percentage)).apply();
                editor.commit();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        return nextEventListener;
    }

    public ValueEventListener getCurrentCount() {
        ValueEventListener nextEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentCount = (long) dataSnapshot.getValue();
                Log.i("snapshot",dataSnapshot.toString());
                Log.i("snapshot1", String.valueOf(currentCount));
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

        next = (Button) findViewById(R.id.button9);
        optiona = (Button) findViewById(R.id.button4);
        optionb = (Button) findViewById(R.id.button5);
        optionc = (Button) findViewById(R.id.button6);
        optiond = (Button) findViewById(R.id.button7);

        question = (TextView) findViewById(R.id.textView);
        answer = (TextView) findViewById(R.id.textView3);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        prefs = getSharedPreferences(
                "abc", Context.MODE_PRIVATE);
        editor = prefs.edit();

//        Intent intent = getIntent();
//        episodelistid = intent.getIntExtra("episodeNumber", -1);

        episodelistid = prefs.getInt("episodeNumber", -1);

        if (episodelistid != -1) {
            episodeName = MainActivity.episodeList.get(episodelistid);
            editor.putString("episodeName", episodeName).apply();
            editor.commit();
        }

        epref = mDatabase.child("submissions").child("2018").child(episodeName);

        if (MainActivity.episodeQuestionMap.get(episodeName).size() == 0) {
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


