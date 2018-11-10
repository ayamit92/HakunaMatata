package com.balli.kbcreturns;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    static ArrayList<Registration> leadersListTopFifty = new ArrayList<Registration>();
    TextView note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        note = (TextView) findViewById(R.id.textView11);

        leadersListTopFifty.clear();

        if (MainActivity.leadersList.size() != 0) {
            Collections.sort(MainActivity.leadersList, Registration.CorrectComparator);
        }
        else{
            note.setText("Internet seems to be slow or off, try coming back!");
        }

        for (int t = 0; t < MainActivity.leadersList.size(); t++) {
            if (t < 50) {
                leadersListTopFifty.add(MainActivity.leadersList.get(t));
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

// set the adapter
        recyclerView.setAdapter(mAdapter);
//        List<String> input = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            input.add("Test" + i);
//        }// define an adapter
        mAdapter = new MyAdapter(leadersListTopFifty);
        recyclerView.setAdapter(mAdapter);
    }
}
