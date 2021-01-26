package com.example.narrator.comicacctivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.narrator.DbHelper;
import com.example.narrator.R;
import com.example.narrator.classes.ComicChapter;
import com.example.narrator.classes.ComicScene;
import com.example.narrator.classes.ComicStory;
import com.example.narrator.classes.DialogViewClass;
import com.example.narrator.classes.ImageViewClass;
import com.example.narrator.classes.Service;
import com.example.narrator.classes.TextChapter;
import com.example.narrator.classes.TextPage;
import com.example.narrator.classes.TextStory;
import com.example.narrator.navigationscreens.Edit;
import com.example.narrator.navigationscreens.Home;
import com.example.narrator.textactivity.AddNewTextBook;
import com.example.narrator.textactivity.AddNewTextChapter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddNewBook extends AppCompatActivity {

    EditText title,description,genre;
    ImageView storyImage;
    Button publish,draft;
    int CAMERA=2,FILE = 1;
    Bitmap bitmap;
    DbHelper db;
    int p;
    Integer CID,UID;
    ArrayList<ComicStory> comicStories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("email", "");
        UID = sh.getInt("UID",0);
        System.out.println(s1);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        p = bundle.getInt("VPOSITION");

        title = findViewById(R.id.name);
        description = findViewById(R.id.description);
        genre = findViewById(R.id.password);

        storyImage = findViewById(R.id.storyImage);
        publish = findViewById(R.id.register);
        draft = findViewById(R.id.draft);
        db = new DbHelper(this);

        if(p >= 0){
            comicStories = db.getAllComicStory(UID);
            ComicStory comicStory = comicStories.get(p);
            title.setText(comicStory.getTitle());
            description.setText(comicStory.getDescription());
            genre.setText(comicStory.getGenre());
            bitmap = stringToBitMap(comicStory.getCoverImage());
            storyImage.setImageBitmap(bitmap);
            CID = db.getComicId(comicStory.getTitle(),comicStory.getDescription());

        }

        publish.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                ComicStory comicStory = db.getComic(CID);
                Service service = new Service();
                ComicStory comicStory1 = service.post("http://10.0.2.2:8080/narrator/comicStory/write",comicStory,ComicStory.class);
                int cid = comicStory1.getCID();

                ArrayList<ComicChapter> comicChapters = db.getAllComicChapter(CID);
                for(int i = 0;i<comicChapters.size();i++){
                    ComicChapter comicChapter = comicChapters.get(i);
                    comicChapter.setCID(cid);
                    ComicChapter comicChapter1 = service.post("http://10.0.2.2:8080/narrator/comicChapter/write",comicChapter,ComicChapter.class);
                    int ccid = comicChapter1.getCCID();
                    int CCID = comicChapter.getCCID();
                    ArrayList<ComicScene> comicScenes = db.getAllComicPage(CCID);
                    for(int j = 0;j<comicScenes.size();j++){
                        ComicScene comicScene = comicScenes.get(j);
                        comicScene.setCCID(ccid);
                        ComicScene comicScene1 = service.post("http://10.0.2.2:8080/narrator/comicScene/write",comicScene,ComicScene.class);
                        int csid = comicScene1.getCSID();
                        int CSID = comicScene.getCSID();
                        ArrayList<ImageViewClass> imageViewClasses = db.getAllImages(CSID);
                        ArrayList<DialogViewClass> dialogViewClasses = db.getAllDialog(CSID);

                        for(int k = 0;k<imageViewClasses.size();k++){
                            ImageViewClass imageViewClass = imageViewClasses.get(k);
                            imageViewClass.setCSID(csid);
                            System.out.println("Left margin "+imageViewClass.getLeftM());
                            service.post("http://10.0.2.2:8080/narrator/comicImage/write",imageViewClass,ImageViewClass.class);
                        }

                        for(int k = 0;k<dialogViewClasses.size();k++){
                            DialogViewClass dialogViewClass = dialogViewClasses.get(k);
                            dialogViewClass.setCSID(csid);
                            service.post("http://10.0.2.2:8080/narrator/comicDialog/write",dialogViewClass,DialogViewClass.class);
                        }
                    }

                }
                Intent intent = new Intent(getBaseContext(), Home.class);
                startActivity(intent);
            }
        });


        draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComicStory comicStory = new ComicStory();
                comicStory.setTitle(title.getText().toString());
                comicStory.setDescription(description.getText().toString());
                comicStory.setGenre(genre.getText().toString());
                String image = convertToString(bitmap);
                comicStory.setCoverImage(image);
                comicStory.setUID(UID);
                if(p >= 0){
                    db.updateComicStory(comicStory,CID);
                }else{

                    db.addComicStory(comicStory);
                    CID = db.getComicId(comicStory.getTitle(),comicStory.getDescription());
                }



                Intent intent = new Intent(getBaseContext(),AddNewChapter.class);
                Bundle display = new Bundle();
                display.putInt("CID",CID);
                display.putInt("VPOSITION",p);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(AddNewBook.this);
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
        display.putInt("VPOSITION",p);

        intent.putExtras(display);
        startActivity(intent);
    }
}
