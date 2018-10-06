package com.ayamit92.kbc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class EpisodeListActivity extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_list);
        ListView listView=(ListView) findViewById(R.id.episodelist);

        prefs = getSharedPreferences(
                "abc", Context.MODE_PRIVATE);
        editor = prefs.edit();

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,MainActivity.episodeList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Intent intent=new Intent (getApplicationContext(),GameActivity.class);
                //intent.putExtra("episodeNumber",i);
                editor.putInt("episodeNumber", i).apply();
                editor.commit();
                startActivity(intent);
            }
        });

    }
}
