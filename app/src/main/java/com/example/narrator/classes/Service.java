package com.example.narrator.classes;

import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Service {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public <T> T post(String path, T entity, Class <T> type){
        T saved = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        System.out.println("Entering post method");
        try {
            URL githubEndpoint = new URL(path);
            HttpURLConnection myConnection = (HttpURLConnection) githubEndpoint.openConnection();
            System.out.println("connection opened");
            myConnection.setRequestMethod("POST");
            myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            myConnection.setRequestProperty("Accept", "application/json");
            myConnection.setDoOutput(true);
            Gson gson = new Gson();
            String json = gson.toJson(entity);
            System.out.println("Json string "+json);
            try(OutputStream os = myConnection.getOutputStream()) {
                System.out.println("Outputstream");
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(myConnection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                saved = gson.fromJson(response.toString(),type);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return saved;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public <T> T get(String path, Class <T> type){
        T saved = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL githubEndpoint = new URL(path);
            HttpURLConnection myConnection = (HttpURLConnection) githubEndpoint.openConnection();
            myConnection.setRequestMethod("GET");
            myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            myConnection.setRequestProperty("Accept", "application/json");
            myConnection.setDoOutput(true);
            Gson gson = new Gson();

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(myConnection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("response from get "+response.toString());
                saved = gson.fromJson(response.toString(),type);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return saved;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public <T> ArrayList<T> getList(String path, Class <T> type){
        ArrayList<T> saved = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL githubEndpoint = new URL(path);
            HttpURLConnection myConnection = (HttpURLConnection) githubEndpoint.openConnection();
            myConnection.setRequestMethod("GET");
            myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            myConnection.setRequestProperty("Accept", "application/json");
            myConnection.setDoOutput(true);
            Gson gson = new Gson();

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(myConnection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                java.lang.reflect.Type listOfObjects = TypeToken.getParameterized(ArrayList.class,type).getType();

                saved = gson.fromJson(response.toString(),listOfObjects);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return saved;
    }
}
