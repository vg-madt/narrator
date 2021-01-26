package com.example.narrator.navigationscreens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.narrator.R;
import com.example.narrator.classes.Service;
import com.example.narrator.classes.User;

public class Login extends AppCompatActivity {

    EditText email;
    EditText password;
    Button login;
    TextView registerClick;
    String eId,pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        registerClick = findViewById(R.id.registerClick);


        registerClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                eId = email.getText().toString();
                pwd = password.getText().toString();
                checkInfo();

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void checkInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", eId);
        editor.commit();

        Service service = new Service();
        User user = service.get("http://10.0.2.2:8080/narrator/user/email/"+eId+"/"+pwd, User.class);
        if(user!=null){

            editor.putInt("UID",user.getUID());
            editor.putString("USERNAME",user.getUsername());
            editor.commit();
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
        }

    }
}
