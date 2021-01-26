package com.example.narrator.textreader;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.narrator.DbHelper;
import com.example.narrator.R;
import com.example.narrator.classes.Service;
import com.example.narrator.classes.TextPage;
import com.example.narrator.classes.TextStory;
import com.example.narrator.comicacctivity.AddNewChapter;

import java.util.ArrayList;

public class ShowTextPage extends AppCompatActivity {

    TextView pageNumber,story;
    Button next,previous;
    int index = 0;
    int TCID,TID,UID;

    ArrayList<TextPage> textpages = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DbHelper db = new DbHelper(this);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        TCID = bundle.getInt("TCID");
        TID = bundle.getInt("TID");
        UID = bundle.getInt("UID");

        pageNumber = findViewById(R.id.pagenumber);
        story = findViewById(R.id.story);

        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);

        //get your arraylist of pages here
        Service service = new Service();
        textpages = service.getList("http://10.0.2.2:8080/narrator/user/"+UID+"/textStory/"+TID+"/chapter/"+TCID+"/pages", TextPage.class);

        if(textpages.size()!=0){
            story.setText(textpages.get(index).getStory());
            pageNumber.setText((index+1)+" of "+textpages.size());
        }else{
            Toast.makeText(getBaseContext(),"No Pages in the chapter",Toast.LENGTH_LONG);
            Intent intent = new Intent(getBaseContext(), ShowTextChapter.class);
            Bundle display = new Bundle();
            display.putInt("TID",TID);
            display.putInt("UID",UID);
            intent.putExtras(display);
            System.out.println("TID before sending "+TID);
            startActivity(intent);

        }



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = index + 1;
                if(textpages.size()!=0) {
                    if (index <= textpages.size()-1) {
                        story.setText(textpages.get(index).getStory());
                        pageNumber.setText((index+1) + " of " + textpages.size());
                        if(index == textpages.size()-1){
                            next.setText("END");
                        }

                    }else{
                        Intent intent = new Intent(getBaseContext(), ShowTextChapter.class);
                        Bundle display = new Bundle();
                        display.putInt("TID",TID);
                        display.putInt("UID",UID);
                        intent.putExtras(display);
                        System.out.println("TID before sending "+TID);
                        startActivity(intent);
                    }
                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = index - 1;
                if(textpages.size()!=0){
                    if(index > 0 && index<textpages.size()-1){
                        story.setText(textpages.get(index).getStory());
                        pageNumber.setText((index-1)+" of "+textpages.size());

                    }else{
                        previous.setEnabled(false);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), ShowTextChapter.class);
        Bundle display = new Bundle();
        display.putInt("TID",TID);
        display.putInt("UID",UID);
        intent.putExtras(display);
        System.out.println("TID before sending "+TID);
        startActivity(intent);
    }

}
