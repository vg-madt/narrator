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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.narrator.CustomAdaptor;
import com.example.narrator.CustomHomeAdaptor;
import com.example.narrator.DbHelper;
import com.example.narrator.R;
import com.example.narrator.classes.ComicStory;
import com.example.narrator.classes.Service;
import com.example.narrator.classes.TextStory;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    RecyclerView textStory,comics;
    ArrayList<TextStory> textStoryList;
    ArrayList<ComicStory> comicStoryList;
    Button ticket;
    DbHelper db;
    RewardedAd rewardedAd;
    int point = 3;
    String Tag = "RewardedAdLog";
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int UID = sh.getInt("UID", 0);


        textStory = findViewById(R.id.list1);
        comics = findViewById(R.id.list2);
        ticket = findViewById(R.id.ticket);
        db = new DbHelper(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        Service service = new Service();
        textStoryList = service.getList("http://10.0.2.2:8080/narrator/textStory/list",TextStory.class);
        comicStoryList = service.getList("http://10.0.2.2:8080/narrator/comicStory/list",ComicStory.class);

        //textStoryList = db.getAllTextStory();
        //comicStoryList = db.getAllComicStory();

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        textStory.setLayoutManager(linearLayoutManager1);


        CustomHomeAdaptor customAdaptor1 = new CustomHomeAdaptor(this, textStoryList,UID);
        textStory.setAdapter(customAdaptor1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        comics.setLayoutManager(linearLayoutManager2);

        CustomHomeAdaptor customAdaptor2 = new CustomHomeAdaptor(this,comicStoryList,UID);
        comics.setAdapter(customAdaptor2);

        loadAd();
        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAd();
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.edit:
                        startActivity(new Intent(getApplicationContext(),Edit.class));
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

    private void loadAd(){
        this.rewardedAd = new RewardedAd(this,getString(R.string.rewarded_ad));
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback(){
            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();
                Log.i(Tag,"Ad loaded");
            }

            @Override
            public void onRewardedAdFailedToLoad(int i) {
                super.onRewardedAdFailedToLoad(i);
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError loadAdError) {
                super.onRewardedAdFailedToLoad(loadAdError);
                Log.i(Tag,"Ad Failed");
            }
        };

        this.rewardedAd.loadAd(new AdRequest.Builder().build(),adLoadCallback);
    }

    private void showAd(){
        if(this.rewardedAd.isLoaded()){
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    Log.i(Tag,"Rewarded");
                    point = point + 1;
                    String p = String.valueOf(point);
                    ticket.setText(p+" Tokens");
                }

                @Override
                public void onRewardedAdOpened() {
                    super.onRewardedAdOpened();
                    Log.i(Tag,"Reward opened");
                }

                @Override
                public void onRewardedAdClosed() {
                    super.onRewardedAdClosed();
                    Log.i(Tag,"Ad closed");
                    loadAd();
                }

                @Override
                public void onRewardedAdFailedToShow(int i) {
                    super.onRewardedAdFailedToShow(i);
                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {
                    super.onRewardedAdFailedToShow(adError);
                    Log.i(Tag,"Ad Failed to show");
                }
            };
            this.rewardedAd.show(this,adCallback);

        }
        else{
            Log.i(Tag,"Ad Load Failed");
        }
    }


}
