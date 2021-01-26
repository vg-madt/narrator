package com.example.narrator.comicacctivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.narrator.R;

public class CharacterGridView extends AppCompatActivity {
    GridView gridView;
    int CSID,CCID,CID,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.characters);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        CSID = bundle.getInt("CSID");
        CCID = bundle.getInt("CCID");
        CID = bundle.getInt("CID");
        p = bundle.getInt("VPOSITION");

        gridView = findViewById(R.id.characterGrid);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), ComicCreator.class);
                Bundle display = new Bundle();
                display.putInt("ISNEW",0);
                display.putInt("CHARACTER",position);
                display.putInt("CSID",CSID);
                display.putInt("CCID",CCID);
                display.putInt("CID",CID);
                display.putInt("VPOSITION",p);
                intent.putExtras(display);
                startActivity(intent);
            }
        });
    }
}
