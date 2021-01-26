package com.example.narrator.textactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.narrator.DbHelper;
import com.example.narrator.R;
import com.example.narrator.classes.TextPage;
import com.example.narrator.classes.TextStory;

public class PageEdit extends AppCompatActivity {
    EditText story;
    Button add;
    int TCID,TPID,isNew,TID;
    DbHelper db;
    TextPage oldTextPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_creator);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        story = findViewById(R.id.storyText);
        add = findViewById(R.id.add);
        db = new DbHelper(this);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        TCID = bundle.getInt("TCID");
        isNew = bundle.getInt("ISNEW");
        TID = bundle.getInt("TID");

        if(isNew == 0){
            TPID = bundle.getInt("TPID");
            oldTextPage = db.getPage(TPID);
            String text = oldTextPage.getStory();
            story.setText(text);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = story.getText().toString();
                TextPage textPage = new TextPage(TCID,text);

                if(isNew == 0){
                    oldTextPage.setStory(text);
                    db.updateTextPage(oldTextPage,TPID);
                }else if(isNew == 1) {
                    db.addTextPage(textPage,TCID);
                }

                Intent intent = new Intent(getBaseContext(),AddNewTextPage.class);
                Bundle display = new Bundle();
                display.putInt("TCID",TCID);
                display.putInt("TID",TID);

                intent.putExtras(display);
                startActivity(intent);

            }
        });
    }
}
