package com.example.narrator.comicreader;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.narrator.DbHelper;
import com.example.narrator.R;
import com.example.narrator.classes.ComicScene;
import com.example.narrator.classes.DialogViewClass;
import com.example.narrator.classes.ImageViewClass;
import com.example.narrator.classes.Service;
import com.example.narrator.classes.TextPage;
import com.example.narrator.comicacctivity.ComicCreator;
import com.example.narrator.textreader.ShowTextChapter;

import java.util.ArrayList;

public class ShowComicScene extends AppCompatActivity {

    int CCID,CID,UID;
    TextView pageNumber;
    Button next,previous;
    ImageView imageView;
    TextView textView;
    RelativeLayout layout;
    int index = 0;
    ArrayList<ComicScene> comicScenes = new ArrayList<>();
    ArrayList<ImageViewClass> imageViews = new ArrayList<>();
    ArrayList<DialogViewClass> dialogViews = new ArrayList<>();
    ArrayList<ArrayList<ImageViewClass>> arrayListOfImageView = new ArrayList<>();
    ArrayList<ArrayList<DialogViewClass>> arrayListOfDialogView = new ArrayList<>();
    DbHelper db;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comic_scene);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        CCID = bundle.getInt("CCID");
        CID = bundle.getInt("CID");
        UID = bundle.getInt("UID");

        db = new DbHelper(this);

        pageNumber = findViewById(R.id.pagenumber);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        layout = findViewById(R.id.layout);

        System.out.println("Comic Scene ID in show comic scene "+CCID);

        Service service = new Service();
        comicScenes = service.getList("http://10.0.2.2:8080/narrator/user/"+UID+"/comicStory/"+CID+"/chapter/"+CCID+"/scenes", ComicScene.class);
        for(int i = 0;i<comicScenes.size();i++) {
            int csid = comicScenes.get(i).getCSID();
            imageViews = service.getList("http://10.0.2.2:8080/narrator/user/"+UID+"/comicStory/"+CID+"/chapter/"+CCID+"/scene/"+csid+"/images",ImageViewClass.class);
            arrayListOfImageView.add(imageViews);

            dialogViews = service.getList("http://10.0.2.2:8080/narrator/user/"+UID+"/comicStory/"+CID+"/chapter/"+CCID+"/scene/"+csid+"/dialogs",DialogViewClass.class);
            arrayListOfDialogView.add(dialogViews);
        }

        pageNumber.setText((index + 1) + " of " + comicScenes.size());
        imageViews = arrayListOfImageView.get(index);
        for(int i = 0;i<imageViews.size();i++){
            int leftM = imageViews.get(i).getLeftM();
            int topM = imageViews.get(i).getTopM();
            int tag = imageViews.get(i).getTag();
            imageView = new ImageView(ShowComicScene.this);
            System.out.println(imageViews.get(i).getImg());
            Bitmap bm = stringToBitMap(imageViews.get(i).getImg());
            Drawable d = new BitmapDrawable(bm);
            imageView.setImageBitmap(bm);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            addOld(imageView,200,200,leftM, topM);

        }

        dialogViews = arrayListOfDialogView.get(index);
        for(int i = 0;i<dialogViews.size();i++){
            int leftM = dialogViews.get(i).getLeftM();
            int topM = dialogViews.get(i).getTopM();
            int tag = dialogViews.get(i).getTag();
            textView = new TextView(ShowComicScene.this);
            System.out.println(imageViews.get(i).getImg());
            textView.setText(dialogViews.get(i).getImg());
            textView.setTextSize(30);
            addOld(textView,200,200,leftM, topM);
        }


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeAllViews();
                index = index + 1;
                if (index < comicScenes.size()) {
                    imageViews = arrayListOfImageView.get(index);
                    dialogViews = arrayListOfDialogView.get(index);
                    pageNumber.setText((index + 1) + " of " + comicScenes.size());
                    if (index == comicScenes.size() - 1) {
                        next.setText("END");
                    }
                    if (imageViews.size() != 0) {
                        for (int i = 0; i < imageViews.size(); i++) {
                            int leftM = imageViews.get(i).getLeftM();
                            int topM = imageViews.get(i).getTopM();
                            int tag = imageViews.get(i).getTag();
                            imageView = new ImageView(ShowComicScene.this);
                            System.out.println(imageViews.get(i).getImg());
                            Bitmap bm = stringToBitMap(imageViews.get(i).getImg());
                            Drawable d = new BitmapDrawable(bm);
                            imageView.setImageBitmap(bm);
                            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            addOld(imageView, 200, 200, leftM, topM);
                        }
                    }
                    if (dialogViews.size() != 0) {
                        dialogViews = arrayListOfDialogView.get(index);
                        for (int i = 0; i < dialogViews.size(); i++) {
                            int leftM = dialogViews.get(i).getLeftM();
                            int topM = dialogViews.get(i).getTopM();
                            int tag = dialogViews.get(i).getTag();
                            textView = new TextView(ShowComicScene.this);
                            textView.setText(dialogViews.get(i).getImg());
                            textView.setTextSize(30);
                            System.out.println(imageViews.get(i).getImg());
                            addOld(textView, 200, 200, leftM, topM);
                        }
                    }
                }else{
                    Intent intent = new Intent(getBaseContext(), ShowComicChapter.class);
                    Bundle display = new Bundle();
                    display.putInt("CID",CID);
                    display.putInt("UID",UID);
                    intent.putExtras(display);
                    startActivity(intent);
                }
            }
        });



    }



    public void addOld(ImageView imageView, int w, int h, int l, int t){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w,h);
        layoutParams.setMargins(l,t,0,0);
        imageView.setLayoutParams(layoutParams);
        layout.addView(imageView);
    }
    public void addOld(TextView imageView, int w, int h, int l, int t){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w,h);
        layoutParams.setMargins(l,t,0,0);
        imageView.setLayoutParams(layoutParams);
        layout.addView(imageView);
    }

    public Bitmap stringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), ShowComicChapter.class);
        Bundle display = new Bundle();
        display.putInt("CID",CID);
        display.putInt("UID",UID);
        intent.putExtras(display);
        startActivity(intent);
    }
}
