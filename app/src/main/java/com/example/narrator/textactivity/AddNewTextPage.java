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
import com.example.narrator.classes.TextPage;

import java.util.ArrayList;


public class AddNewTextPage extends AppCompatActivity {
    EditText story;
    Button add;
    ArrayList<Integer> pages = new ArrayList<>();
    ListView pagelist;
    int TCID,position,TID;
    DbHelper db;
    ArrayList<TextPage> textPages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        story = findViewById(R.id.storyText);
        pagelist = findViewById(R.id.pageList);
        add = findViewById(R.id.addPage);

        db = new DbHelper(this);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        TCID = bundle.getInt("TCID");
        TID = bundle.getInt("TID");
        position = bundle.getInt("POSITION");

        System.out.println("text chapter ID on create" +TCID);

        textPages = db.getAllTextPage(TCID);




        pages = getAll();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(AddNewTextPage.this, android.R.layout.simple_list_item_1, pages);
        pagelist.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), PageEdit.class);
                Bundle display = new Bundle();
                display.putInt("TCID",TCID);
                display.putInt("TID",TID);
                display.putInt("ISNEW",1);
                intent.putExtras(display);
                startActivity(intent);
            }
        });

        pagelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextPage textPage = textPages.get(position);
                Intent intent = new Intent(getBaseContext(), PageEdit.class);
                Bundle display = new Bundle();
                display.putInt("TCID",TCID);
                display.putInt("TID",TID);
                display.putInt("ISNEW",0);
                display.putInt("TPID",textPage.getTPID());
                intent.putExtras(display);
                startActivity(intent);
            }
        });
    }

    public ArrayList<Integer> getAll(){
        pages.removeAll(pages);
        for(int i = 0;i<textPages.size();i++){
            pages.add(i+1);
        }
        return pages;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), AddNewTextChapter.class);
        Bundle display = new Bundle();
        display.putInt("TID",TID);
        display.putInt("POSITION",position);
        intent.putExtras(display);
        startActivity(intent);
    }

}
