package com.ayamit92.kbc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


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

        //default color in list view for text is black, changing it to white
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,MainActivity.episodeList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };;

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
