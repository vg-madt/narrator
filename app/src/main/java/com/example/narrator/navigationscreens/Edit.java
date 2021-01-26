package com.example.narrator.navigationscreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.narrator.Drafts;
import com.example.narrator.comicacctivity.AddNewBook;
import com.example.narrator.textactivity.AddNewTextBook;
import com.example.narrator.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Edit extends AppCompatActivity {

    Button buttonText, buttonComics, buttonDraft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        buttonText = findViewById(R.id.btntext);
        buttonComics = findViewById(R.id.btncomics);
        buttonDraft = findViewById(R.id.btndraft);

        buttonComics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),AddNewBook.class);
                Bundle display = new Bundle();
                display.putInt("VPOSITION",-1);
                intent.putExtras(display);
                startActivity(intent);
            }
        });

        buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),AddNewTextBook.class);
                Bundle display = new Bundle();
                display.putInt("POSITION",-1);
                intent.putExtras(display);
                startActivity(intent);
            }
        });

        buttonDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Drafts.class));
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.edit);

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
}
