package com.example.narrator.navigationscreens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.narrator.R;
import com.example.narrator.classes.Service;
import com.example.narrator.classes.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Register extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText password;
    EditText password2;
    ImageView profileImage;
    TextView loginClick,textView2;
    Button register;
    int CAMERA=2,FILE = 1;
    Bitmap bitmap;
    String PATH = "http://10.0.2.2:8080/narrator/user/write";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        profileImage = findViewById(R.id.profileImage);
        register = findViewById(R.id.register);
        loginClick = findViewById(R.id.loginClick);
        textView2 = findViewById(R.id.textView2);



        loginClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(password2.getText().toString()) && !password.getText().toString().equals("") && !password2.getText().toString().equals("")){
                    Toast.makeText(Register.this, "Passwords match", Toast.LENGTH_SHORT).show();

                    String username = name.getText().toString();
                    String eId = email.getText().toString();
                    String pwd = password.getText().toString();
                    String b = convertToString(bitmap);
                    User user = new User(username,eId,pwd,b);
                    // Create URL
                    Service service = new Service();
                    service.post(PATH,user,User.class);
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(Register.this, "Please check passwords", Toast.LENGTH_SHORT).show();
                }
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
                Drawable d = new BitmapDrawable(getResources(),bmp);
                profileImage.setImageDrawable(d);
                textView2.setVisibility(View.GONE);
                bitmap = bmp;

            }

            else if(requestCode==FILE){
                System.out.println("media opened");
                try {
                    Uri uri = data.getData();
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                    Drawable d = new BitmapDrawable(getResources(),bmp);
                    profileImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    profileImage.setImageDrawable(d);
                    textView2.setVisibility(View.GONE);

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
            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
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
}
