package com.balli.kbcreturns;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class RegistrationActivity extends AppCompatActivity {

    ArrayList<String> ageLst = new ArrayList<String>();
    EditText nameView;
    EditText cityView;
    Spinner ageSpinner;
    Spinner genderSpinner;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String registeredUsers;
    String uniqueId;

    private DatabaseReference mDatabase;


    public void regsubmit(View view) {

        String name = nameView.getText().toString().trim();
        String age = ageSpinner.getSelectedItem().toString();
        String city = cityView.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();

        //check name and city to be not null

        String correct="0";
        String attempted="0";
        String percentage="0";

        registeredUsers = prefs.getString("registeredUsers", "9999");

        if ((name.length()!=0) && (city.length()!=0)){
            Random rand = new Random();
            int  n = rand.nextInt(999) + 1;
            uniqueId=name+(Integer.toString(n))+"A"+(Integer.toString(Integer.parseInt(registeredUsers)+1));
            mDatabase.child("registeredUsersCount").setValue(Integer.toString(Integer.parseInt(registeredUsers)+1));

            editor.putString("profileName", name).apply();
            editor.putString("profileAge", age).apply();
            editor.putString("profileCity", city).apply();
            editor.putString("profileGender", gender).apply();
            editor.putString("profileCorrect", correct).apply();
            editor.putString("profileAttempted", percentage).apply();
            editor.putString("profilePercentage", attempted).apply();

            editor.putString("uniqueId", uniqueId).apply();
            editor.putString("existingUser", "true").apply();
            editor.commit();

            Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
            startActivity(intent);
        }

        else{
            Toast.makeText(getApplicationContext(), "Name and City cannot be empty !!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        prefs = getSharedPreferences(
                "abc", Context.MODE_PRIVATE);
        editor = prefs.edit();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        nameView=(EditText) findViewById(R.id.editname);
        cityView=(EditText) findViewById(R.id.editcity);
        ageSpinner = (Spinner) findViewById(R.id.spinnerage);
        genderSpinner = (Spinner) findViewById(R.id.spinnergender);

        for (int i=1;i<100;i++)
            ageLst.add(Integer.toString(i));

        Spinner dropdown = findViewById(R.id.spinnerage);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ageLst);
        dropdown.setAdapter(adapter);

        Spinner dropdown1 = findViewById(R.id.spinnergender);
        String[] items = new String[]{"Male", "Female"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown1.setAdapter(adapter1);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), EpisodeListActivity.class);
        startActivity(intent);
        return;
    }

}
