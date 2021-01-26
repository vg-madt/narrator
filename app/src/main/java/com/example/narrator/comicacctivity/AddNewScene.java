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
import com.example.narrator.classes.ComicScene;

import java.util.ArrayList;

public class AddNewScene extends AppCompatActivity {

    Button add;
    ArrayList<String> scenes;
    ArrayList<ComicScene> comicScenes = new ArrayList<>();
    ListView scenelist;
    int CCID,CSID,CID,p;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_scene);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("email", "");
        System.out.println(s1);
        db = new DbHelper(this);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        CCID = bundle.getInt("CCID");
        CID = bundle.getInt("CID");
        p = bundle.getInt("VPOSITION");


        System.out.println("CCID in New Scene "+CCID);

        add = findViewById(R.id.addScene);
        scenelist = findViewById(R.id.sceneList);
        scenes = new ArrayList<>();

        comicScenes = db.getAllComicPage(CCID);
        scenes = getAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewScene.this, android.R.layout.simple_list_item_1, scenes);
        scenelist.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder addAlert = new AlertDialog.Builder(AddNewScene.this);
                View alertView = getLayoutInflater().inflate(R.layout.alert_layout_scene, null);
                final EditText title = alertView.findViewById(R.id.scene);
                Button addscene = alertView.findViewById(R.id.addbtn);
                addAlert.setView(alertView);
                final AlertDialog dialog = addAlert.create();
                dialog.show();
                addscene.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!title.getText().toString().isEmpty()) {
                            Toast.makeText(AddNewScene.this, "Scene added", Toast.LENGTH_SHORT).show();
                            scenes.add(title.getText().toString());

                            dialog.cancel();
                            ComicScene comicScene = new ComicScene(CCID,title.getText().toString());
                            db.addComicScene(comicScene);
                            comicScenes = db.getAllComicPage(CCID);
                            CSID = db.getComicSceneId(CCID,title.getText().toString());
                            scenes = getAll();
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewScene.this, android.R.layout.simple_list_item_1, scenes);
                            scenelist.setAdapter(adapter);

                        }
                        else

                        {
                            Toast.makeText(AddNewScene.this, "Please enter Scene", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        scenelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer CSID = db.getComicSceneId(CCID,scenes.get(position));
                Intent intent = new Intent(getBaseContext(), ComicCreator.class);
                Bundle display = new Bundle();
                display.putInt("ISNEW",1);
                display.putInt("CSID",CSID);
                display.putInt("CCID",CCID);
                display.putInt("CID",CID);
                display.putInt("VPOSITION",p);
                intent.putExtras(display);
                startActivity(intent);
            }
        });


    }

    public ArrayList<String> getAll(){
        scenes.removeAll(scenes);
        for(int i = 0;i<comicScenes.size();i++){
            scenes.add(comicScenes.get(i).getTitle());
        }
        return scenes;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(),AddNewChapter.class);
        Bundle display = new Bundle();
        display.putInt("CID",CID);
        display.putInt("VPOSITION",p);
        intent.putExtras(display);
        startActivity(intent);
    }
}
