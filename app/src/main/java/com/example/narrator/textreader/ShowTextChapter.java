package com.example.narrator.textreader;

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
import com.example.narrator.classes.Service;
import com.example.narrator.classes.TextChapter;
import com.example.narrator.classes.TextStory;
import com.example.narrator.navigationscreens.Home;
import com.example.narrator.textactivity.AddNewTextChapter;
import com.example.narrator.textactivity.AddNewTextPage;

import java.util.ArrayList;

public class ShowTextChapter extends AppCompatActivity {

    ArrayList<String> chapters;
    ArrayList<TextChapter> textChapters = new ArrayList<>();
    ListView chapterlist;
    int TID,UID;
    int TCID;
    DbHelper db;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text_chapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        System.out.println("TID in text CHapter "+bundle.getInt("TID") );
        //Extract the data…
        TID = bundle.getInt("TID");
        UID = bundle.getInt("UID");
        chapters = new ArrayList<String>();
        System.out.println("Text story ID after received"+TID);

        db = new DbHelper(this);

        chapterlist = findViewById(R.id.chapterList);

        //get your chapters here from server
        Service service = new Service();
        textChapters = service.getList("http://10.0.2.2:8080/narrator/user/"+UID+"/textStory/"+TID+"/chapters", TextChapter.class);
        chapters = getAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowTextChapter.this, android.R.layout.simple_list_item_1, chapters);
        chapterlist.setAdapter(adapter);

        chapterlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TCID = textChapters.get(position).getTCID();
                //TID = textChapters.get(position).getTID();
                Intent intent = new Intent(getBaseContext(), ShowTextPage.class);
                Bundle display = new Bundle();
                display.putInt("TCID",TCID);
                display.putInt("TID",TID);
                display.putInt("UID",UID);
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
        Intent intent = new Intent(getBaseContext(), Home.class);
        Bundle display = new Bundle();
        display.putInt("TID",TID);
        display.putInt("UID",UID);
        intent.putExtras(display);
        System.out.println("TID before sending "+TID);
        startActivity(intent);
    }
}
