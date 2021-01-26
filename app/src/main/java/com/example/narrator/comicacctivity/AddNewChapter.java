package com.example.narrator.comicacctivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.narrator.DbHelper;
import com.example.narrator.R;
import com.example.narrator.classes.ComicChapter;

import java.util.ArrayList;

public class AddNewChapter extends AppCompatActivity {

    Button add;
    ArrayList<String> chapters;
    ListView chapterlist;
    ArrayList<ComicChapter> comicChapters = new ArrayList<>();
    Integer CID,CCID,p;
    DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_chapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("email", "");
        db = new DbHelper(this);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        CID = bundle.getInt("CID");
        p = bundle.getInt("VPOSITION");
        System.out.println(s1);
        chapters = new ArrayList<String>();

        add = findViewById(R.id.addChapter);
        chapterlist = findViewById(R.id.chapterList);
        comicChapters = db.getAllComicChapter(CID);
        chapters = getAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewChapter.this, android.R.layout.simple_list_item_1, chapters);
        chapterlist.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder addAlert = new AlertDialog.Builder(AddNewChapter.this);
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
                            Toast.makeText(AddNewChapter.this, "Chapter added", Toast.LENGTH_SHORT).show();
                            chapters.add(title.getText().toString());
                            dialog.cancel();
                            ComicChapter comicChapter = new ComicChapter(CID,title.getText().toString());
                            db.addComicChapter(comicChapter);
                            comicChapters = db.getAllComicChapter(CID);
                            chapters = getAll();
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewChapter.this, android.R.layout.simple_list_item_1, chapters);
                            chapterlist.setAdapter(adapter);

                        }
                        else

                    {
                        Toast.makeText(AddNewChapter.this, "Please enter subject", Toast.LENGTH_SHORT).show();
                    }
                }
                });
            }
        });

        chapterlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CCID = db.getComicChapterId(CID,chapters.get(position));
                Intent intent = new Intent(getBaseContext(),AddNewScene.class);
                Bundle display = new Bundle();
                display.putInt("CCID",CCID);
                display.putInt("CID",CID);
                display.putInt("VPOSITION",p);
                intent.putExtras(display);
                startActivity(intent);
            }
        });
    }

    public ArrayList<String> getAll(){
        chapters.removeAll(chapters);
        for(int i = 0;i<comicChapters.size();i++){
            chapters.add(comicChapters.get(i).getTitle());
        }
        return chapters;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), AddNewBook.class);
        Bundle display = new Bundle();
        display.putInt("VPOSITION",p);
        display.putInt("CID",CID);
        intent.putExtras(display);
        startActivity(intent);
    }
}
