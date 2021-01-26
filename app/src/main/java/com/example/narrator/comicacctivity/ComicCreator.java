package com.example.narrator.comicacctivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.narrator.DbHelper;
import com.example.narrator.R;
import com.example.narrator.classes.DialogViewClass;
import com.example.narrator.classes.ImageViewClass;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ComicCreator extends AppCompatActivity implements View.OnClickListener {

    Button avatar;
    Button dialog;
    Button bubble;
    Button text;
    Button save;
    Button close;
    RelativeLayout layout;
    int xDelta, yDelta;
    ScaleGestureDetector scale;
    ImageView imageView;
    TextView textView;
    int oldX,newX;
    int leftMargin, topMargin;
    ImageViewClass imageViewClass;
    DialogViewClass dialogViewClass;
    String image;
    int CSID,CCID,CID,p;
    ArrayList<ImageViewClass> imageViews = new ArrayList<>();
    ArrayList<DialogViewClass> dialogViews = new ArrayList<>();
    HashMap<Integer,ImageViewClass> map = new HashMap<>();
    HashMap<Integer,DialogViewClass> map2 = new HashMap<>();
    ArrayList<ImageView> curimageViews = new ArrayList<>();
    ArrayList<Integer> imageList = new ArrayList<>(Arrays.asList(R.drawable.ch1,R.drawable.ch2,R.drawable.ch3,R.drawable.ch4,R.drawable.ch5,
            R.drawable.ch6,
            R.drawable.ch7,
            R.drawable.ch8));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comic_creator);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        avatar = findViewById(R.id.avatar);
        dialog = findViewById(R.id.dialog);
        layout = findViewById(R.id.layout);
        bubble = findViewById(R.id.bubble);
        text = findViewById(R.id.text);
        save = findViewById(R.id.save);
        close = findViewById(R.id.close);
        final DbHelper db = new DbHelper(this);

        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        int isNew = bundle.getInt("ISNEW");
        CSID = bundle.getInt("CSID");
        CCID = bundle.getInt("CCID");
        CID = bundle.getInt("CID");
        p = bundle.getInt("VPOSITION");
        System.out.println("comic creator Scene ID "+CSID);

        if(isNew == 1) {
            imageViews = db.getAllImages(CSID);
            dialogViews = db.getAllDialog(CSID);
            if(imageViews.size()!=0){
                for (int i = 0; i < imageViews.size(); i++) {
                    int leftM = imageViews.get(i).getLeftM();
                    int topM = imageViews.get(i).getTopM();
                    int tag = imageViews.get(i).getTag();
                    imageView = new ImageView(ComicCreator.this);
                    Bitmap bm = stringToBitMap(imageViews.get(i).getImg());
                    Drawable d = new BitmapDrawable(bm);
                    imageView.setImageDrawable(d);
                    imageView.setTag(tag);
                    curimageViews.add(imageView);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    addOld(imageView,200,200,leftM, topM);
                    ImageViewClass imageViewClass2 = new ImageViewClass(imageViews.get(i).getImg(),leftM,topM,tag);
                    imageViewClass2.setCSID(CSID);
                    imageViewClass2.setSaved(true);
                    map.put(tag,imageViewClass2);
                    imageView.setOnTouchListener(OnTouchListener());
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                }
            }

            if(dialogViews.size()!=0){
                for (int i = 0; i < dialogViews.size(); i++) {
                    //System.out.println("dialog "+dialogViews.get(i).get);
                    int leftM = dialogViews.get(i).getLeftM();
                    int topM = dialogViews.get(i).getTopM();
                    int tag = dialogViews.get(i).getTag();
                    textView = new TextView(ComicCreator.this);
                    textView.setTag(tag);
                    textView.setText(dialogViews.get(i).getImg());
                    addOld(textView,200,200,leftM, topM);
                    DialogViewClass dialogViewClass2 = new DialogViewClass(CSID,dialogViews.get(i).getImg(),leftM,topM,tag);
                    dialogViewClass2.setSaved(true);
                    dialogViewClass2.setCSID(CSID);
                    map2.put(tag,dialogViewClass2);
                    textView.setOnTouchListener(OnTouchListener());
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                }
            }

        }else if(isNew == 0) {
            imageViews = db.getAllImages(CSID);
            dialogViews = db.getAllDialog(CSID);

            System.out.println("size of dialogs "+dialogViews.size());
            System.out.println("size of images "+imageViews.size());

            if(imageViews.size()!=0){
                for (int i = 0; i < imageViews.size(); i++) {
                    int leftM = imageViews.get(i).getLeftM();
                    int topM = imageViews.get(i).getTopM();
                    int tag = imageViews.get(i).getTag();
                    imageView = new ImageView(ComicCreator.this);
                    Bitmap bm = stringToBitMap(imageViews.get(i).getImg());
                    Drawable d = new BitmapDrawable(bm);
                    imageView.setImageDrawable(d);
                    imageView.setTag(tag);
                    curimageViews.add(imageView);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    addOld(imageView,200,200,leftM, topM);
                    ImageViewClass imageViewClass2 = new ImageViewClass(imageViews.get(i).getImg(),leftM,topM,tag);
                    imageViewClass2.setCSID(CSID);
                    imageViewClass2.setSaved(true);
                    map.put(tag,imageViewClass2);
                    imageView.setOnTouchListener(OnTouchListener());
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                }
            }

            if(dialogViews.size()!=0){
                for (int i = 0; i < dialogViews.size(); i++) {
                    int leftM = dialogViews.get(i).getLeftM();
                    int topM = dialogViews.get(i).getTopM();
                    int tag = dialogViews.get(i).getTag();
                    textView = new TextView(ComicCreator.this);
                    textView.setTag(tag);
                    textView.setText(dialogViews.get(i).getImg());
                    addOld(textView,200,200,leftM, topM);
                    DialogViewClass dialogViewClass2 = new DialogViewClass(CSID,dialogViews.get(i).getImg(),leftM,topM,tag);
                    dialogViewClass2.setCSID(CSID);
                    dialogViewClass2.setSaved(true);
                    map2.put(tag,dialogViewClass2);
                    textView.setOnTouchListener(OnTouchListener());
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                }
            }

            int charPosition = bundle.getInt("CHARACTER");
            imageView = new ImageView(ComicCreator.this);
            Integer d = imageList.get(charPosition);
            imageView.setImageResource(d);
            imageView.setTag(imageViews.size()+1);
            Integer key = (Integer) imageView.getTag();

            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            System.out.println("key  "+key);


            addView(imageView, 200, 200);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),d);
            image = convertToString(bmp);
            imageViewClass = new ImageViewClass(image,leftMargin,topMargin, (Integer) imageView.getTag());
            imageViewClass.setCSID(CSID);
            map.put(key,imageViewClass);
            System.out.println("imageView  "+imageViewClass);


            imageView.setOnTouchListener(OnTouchListener());

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(imageView.getTag().toString());
                }
            });
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Top margin "+topMargin);
                System.out.println("Left margin "+leftMargin);
                for (Map.Entry<Integer,ImageViewClass> me : map.entrySet()) {
                    System.out.println("Key: "+me.getKey() + " & Value: " + me.getValue());
                    if(!me.getValue().getSaved()) {
                        db.addImage(me.getValue());
                    }else{
                        db.updateImage(me.getValue(),me.getKey());
                    }
                }
                for (Map.Entry<Integer,DialogViewClass> de : map2.entrySet()) {
                    System.out.println("Key: "+de.getKey() + " & Value: " + de.getValue());
                    if(!de.getValue().getSaved()) {
                        db.addDialog(de.getValue());
                    }else{
                        db.updateDialog(de.getValue(),de.getKey());
                    }
                }

                System.out.println("CSID while saving "+CSID);

            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CharacterGridView.class);
                Bundle display = new Bundle();
                display.putInt("CSID",CSID);
                display.putInt("CCID",CCID);
                display.putInt("CID",CID);
                display.putInt("VPOSITION",p);
                intent.putExtras(display);
                startActivity(intent);


            }
        });

        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageView = new ImageView(ComicCreator.this);
                imageView.setImageResource(R.drawable.dialog);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setTag(map.size()+1);
                int d = R.drawable.dialog;
                Integer key = (Integer) imageView.getTag();
                addView(imageView,200,200);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                Bitmap bmp = BitmapFactory.decodeResource(getResources(),d);
                image = convertToString(bmp);
                imageViewClass = new ImageViewClass(image,leftMargin,topMargin, (Integer) imageView.getTag());
                imageViewClass.setCSID(CSID);
                map.put(key,imageViewClass);
                imageView.setOnTouchListener(OnTouchListener());


                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(imageView.getTag().toString());
                    }
                });
            }
        });

        bubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView = new ImageView(ComicCreator.this);
                imageView.setImageResource(R.drawable.bubble);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setTag(map.size()+1);
                int d = R.drawable.bubble;
                Integer key = (Integer) imageView.getTag();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                Bitmap bmp = BitmapFactory.decodeResource(getResources(),d);
                image = convertToString(bmp);
                imageViewClass = new ImageViewClass(image,leftMargin,topMargin, (Integer) imageView.getTag());
                imageViewClass.setCSID(CSID);
                map.put(key,imageViewClass);
                addView(imageView,200,200);
                imageView.setOnTouchListener(OnTouchListener());

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(imageView.getTag().toString());
                    }
                });
            }
        });




        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder addAlert = new AlertDialog.Builder(ComicCreator.this);
                View alertView = getLayoutInflater().inflate(R.layout.alert_add_dialogue, null);
                final EditText dialogue = alertView.findViewById(R.id.dialogue);
                Button addDialogue = alertView.findViewById(R.id.addbtn);
                addAlert.setView(alertView);
                final AlertDialog dialog = addAlert.create();
                dialog.show();

                addDialogue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        textView = new TextView(ComicCreator.this);
                        textView.setText(dialogue.getText().toString());
                        String text = textView.getText().toString();
                        textView.setTag(map2.size()+1000);
                        textView.setTextSize(30.0f);
                        Integer key = (Integer) textView.getTag();
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                        dialogViewClass = new DialogViewClass(CSID,text,leftMargin,topMargin, (Integer) textView.getTag());
                        map2.put(key,dialogViewClass);
                        addView(textView,200,200);
                        dialog.cancel();

                        textView.setOnTouchListener(OnTouchListener());
                        
                        textView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                return false;
                            }
                        });
                    }
                });



            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),AddNewScene.class);
                Bundle display = new Bundle();
                display.putInt("CCID",CCID);
                display.putInt("CID",CID);
                display.putInt("VPOSITION",p);
                System.out.println("CCID in Comic creator "+CCID);
                intent.putExtras(display);
                startActivity(intent);
            }
        });

    }



    private View.OnTouchListener OnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();


                switch(event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();


                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = -40;
                        layoutParams.bottomMargin = -40;
                        v.setLayoutParams(layoutParams);
                        leftMargin = layoutParams.leftMargin;
                        topMargin = layoutParams.topMargin;

                        System.out.println("final param x " +layoutParams.leftMargin);
                        System.out.println("final param y " +layoutParams.topMargin);
                        oldX = layoutParams.leftMargin;
                        System.out.println("tag "+v.getTag());
                        System.out.println("final tag "+map.get(v.getTag()));
                        int check = (int) v.getTag();
                        if(!(check >=1000)) {
                            map.get(v.getTag()).setLeftM(layoutParams.leftMargin);
                            map.get(v.getTag()).setTopM(layoutParams.topMargin);
                        }else if(check>=1000){
                            map2.get(v.getTag()).setLeftM(layoutParams.leftMargin);
                            map2.get(v.getTag()).setTopM(layoutParams.topMargin);
                        }




                        break;

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                        case MotionEvent.ACTION_UP:
                            RelativeLayout.LayoutParams nparams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            leftMargin = nparams.leftMargin;
                            topMargin = nparams.topMargin;

                            System.out.println("Iam touched");


                }
                layout.invalidate();
                return false;

            }
        };


    }

    public void addView(ImageView imageView,int w,int h){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w,h);
        layoutParams.setMargins(60,60,0,0);
        imageView.setLayoutParams(layoutParams);
        layout.addView(imageView);
    }

    public void addView(TextView textView,int w,int h){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w,h);
        layoutParams.setMargins(60,60,0,0);
        textView.setLayoutParams(layoutParams);
        layout.addView(textView);
    }

    public void addOld(ImageView imageView,int w, int h, int l,int t){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w,h);
        layoutParams.setMargins(l,t,0,0);
        imageView.setLayoutParams(layoutParams);
        layout.addView(imageView);
    }
    public void addOld(TextView textView,int w, int h, int l,int t){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w,h);
        layoutParams.setMargins(l,t,0,0);
        textView.setLayoutParams(layoutParams);
        layout.addView(textView);
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

    public String convertToString(Bitmap bitmap){
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,b);
        byte[] bytes = b.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }

    public void setMargins(int leftMargin,int topMargin,int tag){

    }


    @Override
    public void onClick(View v) {

    }


}
