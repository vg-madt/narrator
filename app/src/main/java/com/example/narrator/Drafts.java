package com.example.narrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.narrator.classes.ComicStory;
import com.example.narrator.classes.TextStory;
import com.example.narrator.navigationscreens.Edit;
import com.example.narrator.navigationscreens.Home;
import com.example.narrator.navigationscreens.Library;
import com.example.narrator.navigationscreens.Notifications;
import com.example.narrator.navigationscreens.Settings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Drafts extends AppCompatActivity {

    RecyclerView textStory,comics;
    ArrayList<TextStory> textStoryList;
    ArrayList<ComicStory> comicStoryList;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drafts);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("email", "");
        int UID = sh.getInt("UID",0);
        System.out.println(s1);

        textStory = findViewById(R.id.list1);
        comics = findViewById(R.id.list2);
        db = new DbHelper(this);

        textStoryList = db.getAllTextStory(UID);
        System.out.println("User ID in drafts "+UID);
        comicStoryList = db.getAllComicStory(UID);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        textStory.setLayoutManager(linearLayoutManager1);


        CustomAdaptor customAdaptor1 = new CustomAdaptor(this, textStoryList);
        textStory.setAdapter(customAdaptor1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        comics.setLayoutManager(linearLayoutManager2);

        CustomAdaptor customAdaptor2 = new CustomAdaptor(this,comicStoryList);
        comics.setAdapter(customAdaptor2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.edit:
                        startActivity(new Intent(getApplicationContext(), Edit.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.library:
                        startActivity(new Intent(getApplicationContext(), Library.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.notification:
                        startActivity(new Intent(getApplicationContext(), Notifications.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }
}
