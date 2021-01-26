package com.example.narrator.comicreader;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
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
import com.example.narrator.classes.Service;
import com.example.narrator.classes.TextChapter;
import com.example.narrator.comicreader.ShowComicScene;
import com.example.narrator.navigationscreens.Home;

import java.util.ArrayList;

public class ShowComicChapter extends AppCompatActivity {

    ArrayList<String> chapters;
    ArrayList<ComicChapter> comicChapters = new ArrayList<>();
    ListView chapterlist;
    int CID,UID;
    int CCID;
    DbHelper db;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text_chapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        CID = bundle.getInt("CID");
        UID = bundle.getInt("UID");
        chapters = new ArrayList<String>();

        db = new DbHelper(this);

        chapterlist = findViewById(R.id.chapterList);

        //get your chapters here from server

        Service service = new Service();
        comicChapters = service.getList("http://10.0.2.2:8080/narrator/user/"+UID+"/comicStory/"+CID+"/chapters", ComicChapter.class);
        System.out.println("Getting the CID in chapter "+CID);
        chapters = getAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowComicChapter.this, android.R.layout.simple_list_item_1, chapters);
        chapterlist.setAdapter(adapter);

        chapterlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ComicChapter comicChapter = comicChapters.get(position);
                CCID = comicChapter.getCCID();
                System.out.println("Getting the CCID in chapter "+CCID);
                Intent intent = new Intent(getBaseContext(), ShowComicScene.class);
                Bundle display = new Bundle();
                display.putInt("CCID",CCID);
                display.putInt("CID",CID);
                display.putInt("UID",UID);
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
        Intent intent = new Intent(getBaseContext(), Home.class);
        Bundle display = new Bundle();
        display.putInt("CID",CID);
        display.putInt("UID",UID);
        intent.putExtras(display);
        startActivity(intent);
    }
}
