package com.ayamit92.kbc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class YearListActivity extends AppCompatActivity {
    //<!--android:background="@drawable/kbc3"-->
//    <!--android:backgroundTint="#80FFFFFF"-->
//    <!--android:backgroundTintMode="src_over"-->
    static ArrayList<String> yearList = new ArrayList<String>();
    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    public void selectyear(View view) {

        switch (view.getId()) {

            case R.id.year_view1: {
                Intent intent = new Intent(getApplicationContext(), EpisodeListActivity.class);
                editor.putInt("year", 2018).apply();
                editor.commit();
                startActivity(intent);
            }
            break;

            case R.id.year_view2:
                Toast.makeText(getApplicationContext(), "Coming soon, 2017", Toast.LENGTH_SHORT).show();
                break;

            case R.id.year_view3:
                Toast.makeText(getApplicationContext(), "Coming soon, 2014", Toast.LENGTH_SHORT).show();
                break;

            case R.id.year_view4:
                Toast.makeText(getApplicationContext(), "Coming soon, 2013", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_year_list);

            prefs = getSharedPreferences(
                    "abc", Context.MODE_PRIVATE);
            editor = prefs.edit();
        }
    }
