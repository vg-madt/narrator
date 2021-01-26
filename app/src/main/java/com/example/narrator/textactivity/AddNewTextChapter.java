package com.example.narrator.textactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.narrator.DbHelper;
import com.example.narrator.R;
import com.example.narrator.classes.TextChapter;
import com.example.narrator.textactivity.AddNewTextBook;

import java.util.ArrayList;

public class AddNewTextChapter extends AppCompatActivity {
    Button add;
    ArrayList<String> chapters;
    ArrayList<TextChapter> textChapters = new ArrayList<>();
    ListView chapterlist;
    int TID;
    int TCID,position;
    DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_chapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        TID = bundle.getInt("TID");
        position = bundle.getInt("POSITION");
        chapters = new ArrayList<String>();

        db = new DbHelper(this);

        add = findViewById(R.id.addChapter);
        chapterlist = findViewById(R.id.chapterList);
        textChapters = db.getAllTextChapter(TID);
        chapters = getAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewTextChapter.this, android.R.layout.simple_list_item_1, chapters);
        chapterlist.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder addAlert = new AlertDialog.Builder(AddNewTextChapter.this);
                View alertView = getLayoutInflater().inflate(R.layout.alert_layout_chapter, null);
                final EditText title = alertView.findViewById(R.id.chapter);
                Button addChapter = alertView.findViewById(R.id.addbtn);
                addAlert.setView(alertView);
                final AlertDialog dialog = addAlert.create();
                dialog.show();
                addChapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!title.getText().toString().isEmpty()) {
                            Toast.makeText(AddNewTextChapter.this, "Chapter added", Toast.LENGTH_SHORT).show();
                            chapters.add(title.getText().toString());
                            dialog.cancel();
                            TextChapter textChapter = new TextChapter(title.getText().toString());
                            db.addTextChapter(textChapter,TID);

                            textChapters = db.getAllTextChapter(TID);
                            chapters = getAll();
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewTextChapter.this, android.R.layout.simple_list_item_1, chapters);
                            chapterlist.setAdapter(adapter);

                            TCID = db.getTextChapterId(TID,title.getText().toString());
                            System.out.println("text chapter ID in chapter page" +TCID);

                        }
                        else

                        {
                            Toast.makeText(AddNewTextChapter.this, "Please enter subject", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        chapterlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextChapter textChapter = textChapters.get(position);
                TCID = textChapter.getTCID();
                Intent intent = new Intent(getBaseContext(),AddNewTextPage.class);
                Bundle display = new Bundle();
                display.putInt("TCID",TCID);
                display.putInt("TID",TID);
                intent.putExtras(display);
                startActivity(intent);
            }
        });


    }

    public ArrayList<String> getAll(){
        chapters.removeAll(chapters);
        for(int i = 0;i<textChapters.size();i++){
            chapters.add(textChapters.get(i).getTitle());
        }
        return chapters;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), AddNewTextBook.class);
        Bundle display = new Bundle();
        display.putInt("POSITION",position);

        intent.putExtras(display);
        startActivity(intent);
    }
}
