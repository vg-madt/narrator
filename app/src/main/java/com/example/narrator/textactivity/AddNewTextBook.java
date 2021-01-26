package com.example.narrator.textactivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.narrator.DbHelper;
import com.example.narrator.R;
import com.example.narrator.classes.ComicStory;
import com.example.narrator.classes.Service;
import com.example.narrator.classes.TextChapter;
import com.example.narrator.classes.TextPage;
import com.example.narrator.classes.TextStory;
import com.example.narrator.comicacctivity.AddNewChapter;
import com.example.narrator.navigationscreens.Edit;
import com.example.narrator.navigationscreens.Home;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddNewTextBook extends AppCompatActivity {

    EditText title,description,genre;
    ImageView storyImage;
    Button publish,draft;
    int CAMERA=2,FILE = 1;
    Bitmap bitmap;
    DbHelper db;
    int TID,UID;
    int position;
    ArrayList<TextStory> textStories = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        UID = sh.getInt("UID",0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        position = bundle.getInt("POSITION");

        title = findViewById(R.id.name);
        description = findViewById(R.id.description);
        genre = findViewById(R.id.password);

        storyImage = findViewById(R.id.storyImage);
        publish = findViewById(R.id.register);
        draft = findViewById(R.id.draft);
        db = new DbHelper(this);

        if(position >= 0){
            textStories = db.getAllTextStory(UID);
            TextStory textStory = textStories.get(position);
            title.setText(textStory.getTitle());
            description.setText(textStory.getDescription());
            genre.setText(textStory.getGenre());
            bitmap = stringToBitMap(textStory.getCoverImage());
            storyImage.setImageBitmap(bitmap);
            TID = db.getTextId(textStory.getTitle(),textStory.getDescription());

        }

        publish.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                TextStory textStory = db.getStory(TID);
                Service service = new Service();
                TextStory textStory1 = service.post("http://10.0.2.2:8080/narrator/textStory/write",textStory,TextStory.class);
                int tid = textStory1.getTID();

                ArrayList<TextChapter> textChapters = db.getAllTextChapter(TID);
                for(int i = 0;i<textChapters.size();i++){
                    TextChapter textChapter = textChapters.get(i);
                    textChapter.setTID(tid);
                    TextChapter textChapter1 = service.post("http://10.0.2.2:8080/narrator/textChapter/write",textChapter,TextChapter.class);
                    int tcid = textChapter1.getTCID();
                    int TCID = textChapter.getTCID();
                    ArrayList<TextPage> textPages = db.getAllTextPage(TCID);
                    for(int j = 0;j<textPages.size();j++){
                        TextPage textPage = textPages.get(j);
                        textPage.setTCID(tcid);
                        service.post("http://10.0.2.2:8080/narrator/chapterPage/write",textPage,TextPage.class);
                    }

                }


                Intent intent = new Intent(getBaseContext(), Home.class);
                startActivity(intent);


            }
        });



        draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextStory textStory = new TextStory();
                textStory.setTitle(title.getText().toString());
                textStory.setDescription(description.getText().toString());
                textStory.setGenre(genre.getText().toString());
                String image = convertToString(bitmap);
                textStory.setCoverImage(image);
                textStory.setUID(UID);
                if(position >= 0){
                    db.updateTextStory(textStory,TID);
                }else{

                    db.addTextStory(textStory);
                    TID = db.getTextId(textStory.getTitle(),textStory.getDescription());
                }



                Intent intent = new Intent(getBaseContext(), AddNewTextChapter.class);
                Bundle display = new Bundle();
                display.putInt("TID",TID);
                display.putInt("POSITION",position);
                intent.putExtras(display);
                startActivity(intent);

            }
        });

        storyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode==CAMERA){
                Bundle bundle = data.getExtras();
                Bitmap bmp = (Bitmap) bundle.get("data");
                storyImage.setImageBitmap(bmp);
                bitmap = bmp;

            }

            else if(requestCode==FILE){
                System.out.println("media opened");
                try {
                    Uri uri = data.getData();
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                    storyImage.setImageBitmap(bmp);
                    bitmap = bmp;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void selectImage(){

        PackageManager pm = getPackageManager();
        int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
        try{
            final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(AddNewTextBook.this);
            builder.setTitle("Select Option");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        dialog.dismiss();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if(intent.resolveActivity(getPackageManager())!=null) {
                            startActivityForResult(intent, CAMERA);
                        }
                    } else if (options[item].equals("Choose From Gallery")) {
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Select Picture"),FILE);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    public String convertToString(Bitmap bitmap){
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,b);
        byte[] bytes = b.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
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
        Intent intent = new Intent(getBaseContext(), Edit.class);
        Bundle display = new Bundle();
        display.putInt("POSITION",position);

        intent.putExtras(display);
        startActivity(intent);
    }
}
