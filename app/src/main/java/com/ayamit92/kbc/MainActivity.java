package com.ayamit92.kbc;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button qBankButton;
    private DatabaseReference mDatabase, epref, yref;
    String question = "";
    String optionA = "";
    String optionB = "";
    String optionC = "";
    String optionD = "";
    String answer = "";
    String episodeDate = "";
    static ArrayList<Question> questionListDownload = new ArrayList<Question>();

    static ArrayList<String> episodeList = new ArrayList<String>();
    static HashMap<String, ArrayList<Question>> episodeQuestionMap = new HashMap<String, ArrayList<Question>>();

    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

//               Reading the webpage line by line make the retrieval much much faster than reading it by char by char !!
                BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                StringBuffer result = new StringBuffer();
                //for pBlockQuestion, by doing view-source it is seen as <span>, but while printing in logs it comes out as <strong>
                Pattern pBlockQuestion = Pattern.compile("</strong>(.*?)\\?");
                Pattern pBlock1 = Pattern.compile("<span class=\"alpha text-orange\">\\[A\\] </span> (.*?)</div>");
                Pattern pBlock2 = Pattern.compile("<span class=\"alpha text-orange\">\\[B\\] </span> (.*?)</div>");
                Pattern pBlock3 = Pattern.compile("<span class=\"alpha text-orange\">\\[C\\] </span> (.*?)</div>");
                Pattern pBlock4 = Pattern.compile("<span class=\"alpha text-orange\">\\[D\\] </span> (.*?)</div>");
                Pattern pBlock5 = Pattern.compile("datetime(.*)>(.*)</time>");

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                    Log.i("ozark", line);
                    Matcher mBlockQuestion = pBlockQuestion.matcher(line);
                    Matcher mBlock1 = pBlock1.matcher(line);
                    Matcher mBlock2 = pBlock2.matcher(line);
                    Matcher mBlock3 = pBlock3.matcher(line);
                    Matcher mBlock4 = pBlock4.matcher(line);
                    Matcher mBlock5 = pBlock5.matcher(line);

                    while (mBlockQuestion.find()) {
                        question = mBlockQuestion.group(1) + "?";
                        Log.i("qoqo", question);
                    }
                    while (mBlock1.find()) {
                        Log.i("yoyo", mBlock1.group(1));
                        optionA = mBlock1.group(1);
                    }
                    while (mBlock2.find()) {
                        Log.i("koko", mBlock2.group(1));
                        optionB = mBlock2.group(1);
                    }
                    while (mBlock3.find()) {
                        Log.i("jojo", mBlock3.group(1));
                        optionC = mBlock3.group(1);
                    }
                    while (mBlock4.find()) {
                        Log.i("toto", mBlock4.group(1));
                        optionD = mBlock4.group(1);
                        questionListDownload.add(new Question(question, optionA, optionB, optionC, optionD, answer));
                    }
                    if (mBlock5.find()) {
                        Log.i("zozo", mBlock5.group(2));
                        episodeDate = mBlock5.group(2);
                    }

                }

//                uploadData(episodeDate,questionListDownload);
//                Log.i("Contents Of URL", result.toString());

                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }
        }
    }

    public void download(View view) {

//        HashMap<String, Integer> yList = new HashMap<String, Integer>();
//        yList.put("2018",20);
//        yList.put("2017",45);
//        yList.put("2014",33);
//        yList.put("2013",24);
//
//        for (Map.Entry<String, Integer> entry : yList.entrySet()) {
//
//            String seasonSeq="10";
//            switch (entry.getKey()){
//                case "2018":
//                    seasonSeq="10";
//                    break;
//                case "2017":
//                    seasonSeq="9";
//                    break;
//                case "2014":
//                    seasonSeq="8";
//                    break;
//                case "2013":
//                    seasonSeq="7";
//                    break;
//            }
//
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-1st-episode-16th-september-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-2nd-episode.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-3rd-episode-8th-september-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-4th-episode-13th-september-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-5th-episode-14th-september-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-6th-episode-15th-september-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-7th-episode-20-september-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-episode-8th-21st-september-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-episode-9th-22nd-september-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-10th-episode-27th-september-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-11th-episode-28th-september-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-12th-episode-4-october-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-13th-episode-5-october-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-14th-episode-6-october-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-15th-episode-11-october-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-16th-episode-12-october-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-17th-episode-13-october-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-18th-episode-18-october-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-19th-episode-19-october-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-20th-episode-20-october-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-21st-episode-25-october-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-22nd-episode-26-october-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-23rd-episode-1-november-2013.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-7-24th-episode-2nd-november-2013.html");
////
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-9-registrations.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-9-episode-46-jio-play-along.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-9-episode-47-jio-play-along.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-9-episode-48-jio-play-along.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-9-episode-49-jio-play-along.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-9-episode-50-jio-play-along.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-9-grand-finale.html");
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-9-grand-finale-7th-nov.html");
////
////        urlList.add("https://www.eduzip.com/kbc-questions/kbc-10-registration.html");
////
////        for (String urls : urlList) {
////            String url = urls;
////            String url1 = urls + "?page=2";
////            String url2 = urls + "?page=3";
////            String url3 = urls + "?page=4";
//
//            for (int i = 1; i < entry.getValue()+1; i++) {
//                String url = "https://www.eduzip.com/kbc-questions/kbc-"+seasonSeq+"-episode-" + Integer.toString(i) + ".html";
//                String url1 = "https://www.eduzip.com/kbc-questions/kbc-"+seasonSeq+"-episode-" + Integer.toString(i) + ".html?page=2";
//                String url2 = "https://www.eduzip.com/kbc-questions/kbc-"+seasonSeq+"-episode-" + Integer.toString(i) + ".html?page=3";
//                String url3 = "https://www.eduzip.com/kbc-questions/kbc-"+seasonSeq+"-episode-" + Integer.toString(i) + ".html?page=4";
//
//                //https://www.eduzip.com/kbc-questions/kbc-7-20th-episode-20-october-2013.html
//
//                questionListDownload.clear();
//                DownloadTask task = new DownloadTask();
//                DownloadTask task1 = new DownloadTask();
//                DownloadTask task2 = new DownloadTask();
//                DownloadTask task3 = new DownloadTask();
//                String result = null;
//                String result1 = null;
//                String result2 = null;
//                String result3 = null;
//
//                try {
//                    result = task.execute(url).get();
//                    result1 = task1.execute(url1).get();
//                    result2 = task2.execute(url2).get();
//                    result3 = task3.execute(url3).get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//
//                if (questionListDownload.size() != 0) {
//                    uploadData(episodeDate, questionListDownload, entry.getKey());
//                }
//            }
//        }

        Toast.makeText(getApplicationContext(), "Download Successful", Toast.LENGTH_LONG).show();
    }

    public void uploadData(String episodeDate, ArrayList<Question> questionListDownload, String year) {
        for (int i = 0; i < questionListDownload.size(); i++) {
            mDatabase.child(year).child(episodeDate).child(Integer.toString(i + 1)).setValue(questionListDownload.get(i));
        }
    }

    public ValueEventListener episodeList() {
        ValueEventListener nextEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String episodeName = "";
                episodeList.clear();
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    episodeName = String.valueOf(uniqueUserSnapshot.getKey());
                    episodeList.add(episodeName);
                    Log.i("episodeName", episodeName);
                    //above, key is retrieved and not value
                    yref = mDatabase.child("2018").child(episodeName);
                    yref.addListenerForSingleValueEvent(questionList(episodeName));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        return nextEventListener;
    }

    public ValueEventListener questionList(final String episodeName) {
        ValueEventListener nextEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Question> questionList = new ArrayList<Question>();
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    questionList.add(uniqueUserSnapshot.getValue(Question.class));
                }
                episodeQuestionMap.put(episodeName, questionList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        return nextEventListener;
    }

    public void questionbank(View view) {
        Intent intent = new Intent(getApplicationContext(), YearListActivity.class);
        startActivity(intent);

////        handler is required as next activity gets started even before data is populated, so 1s pause time is applied
//        final Handler mHandler = new Handler();
//        mHandler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
////                         Intent intent=new Intent (getApplicationContext(),YearListActivity.class);
//                           startActivity(intent);
//            }
//
//        }, 1000L);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qBankButton = (Button) findViewById(R.id.button3);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        epref = mDatabase.child("2018");
        epref.addListenerForSingleValueEvent(episodeList());

//        MediaPlayer ring = MediaPlayer.create(MainActivity.this, R.raw.ring);
//        ring.start();

    }
}
