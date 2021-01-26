package com.example.narrator.navigationscreens;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.narrator.CustomAdaptorFavourite;
import com.example.narrator.CustomHomeAdaptor;
import com.example.narrator.DbHelper;
import com.example.narrator.R;
import com.example.narrator.classes.ComicStory;
import com.example.narrator.classes.Service;
import com.example.narrator.classes.TextFavourites;
import com.example.narrator.classes.TextStory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Library extends AppCompatActivity {

    RecyclerView textStory,comics;
    ArrayList<TextStory> textStoryList,textStoryList1;
    ArrayList<ComicStory> comicStoryList;
    ArrayList<TextFavourites> textFavourites;
    Button ticket;
    DbHelper db;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Integer UID = sh.getInt("UID", 0);

        textStory = findViewById(R.id.list1);
        comics = findViewById(R.id.list2);
        ticket = findViewById(R.id.ticket);
        db = new DbHelper(this);

        Service service = new Service();
        textStoryList = service.getList("http://10.0.2.2:8080/narrator/user/"+UID+"/textStory/favourites",TextStory.class);

        for(int i = 0;i<textStoryList.size();i++){
            System.out.println(textStoryList.get(i).getTID()+"     "+textStoryList.get(i).getUID());
        }

        comicStoryList = service.getList("http://10.0.2.2:8080/narrator/user/"+UID+"/comicStory/favourites",ComicStory.class);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        textStory.setLayoutManager(linearLayoutManager1);


        CustomAdaptorFavourite customAdaptor1 = new CustomAdaptorFavourite(this, textStoryList);
        textStory.setAdapter(customAdaptor1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        comics.setLayoutManager(linearLayoutManager2);

        CustomAdaptorFavourite customAdaptor2 = new CustomAdaptorFavourite(this,comicStoryList);
        comics.setAdapter(customAdaptor2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.library);

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
                        startActivity(new Intent(getApplicationContext(),Library.class));
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
