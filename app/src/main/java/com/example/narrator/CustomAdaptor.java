package com.example.narrator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.narrator.classes.ComicStory;
import com.example.narrator.classes.TextStory;
import com.example.narrator.classes.Type;
import com.example.narrator.comicacctivity.AddNewBook;
import com.example.narrator.textactivity.AddNewTextBook;

import java.util.ArrayList;
import java.util.List;

public class CustomAdaptor<T extends Type> extends RecyclerView.Adapter{

    private
    Context context;
    private ArrayList<T> storyList;


    public CustomAdaptor(Context context, ArrayList<T> storyList) {
        this.context = context;
        this.storyList = storyList;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == Type.TEXT_TYPE) {
            View v = LayoutInflater.from(context).inflate(R.layout.list_of_stories,parent,false);
            return new TextViewHolder(v);
        } else if (viewType == Type.COMIC_TYPE) {
            View v = LayoutInflater.from(context).inflate(R.layout.list_of_stories,parent,false);
            return new ComicViewHolder(v);
        } else {
            return null;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (storyList.get(position) instanceof TextStory) {
            return Type.TEXT_TYPE;
        } else if (storyList.get(position) instanceof ComicStory) {
            return Type.COMIC_TYPE;
        } else {
            return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof TextViewHolder) {
            TextStory textStory = (TextStory) storyList.get(position);
            ((TextViewHolder) holder).title.setText(textStory.getTitle());
            ((TextViewHolder) holder).description.setText(textStory.getDescription());
            String cover = textStory.getCoverImage();
            Bitmap cI = stringToBitMap(cover);
            Drawable drawable = new BitmapDrawable(cI);
            ((TextViewHolder) holder).coverImage.setImageDrawable(drawable);
            ((TextViewHolder) holder).coverImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AddNewTextBook.class);
                    Bundle display = new Bundle();
                    display.putInt("POSITION",position);
                    intent.putExtras(display);
                    v.getContext().startActivity(intent);
                }
            });
        }else if(holder instanceof ComicViewHolder){
            ComicStory textStory = (ComicStory) storyList.get(position);
            ((ComicViewHolder) holder).title.setText(textStory.getTitle());
            ((ComicViewHolder) holder).description.setText(textStory.getDescription());
            String cover = textStory.getCoverImage();
            Bitmap cI = stringToBitMap(cover);
            Drawable drawable = new BitmapDrawable(cI);
            ((ComicViewHolder) holder).coverImage.setImageDrawable(drawable);
            ((ComicViewHolder) holder).coverImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AddNewBook.class);
                    Bundle display = new Bundle();
                    display.putInt("VPOSITION",position);
                    intent.putExtras(display);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {

        TextView title,description;
        ImageView coverImage;
        public TextViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            coverImage = itemView.findViewById(R.id.image);
        }
    }

    public static class ComicViewHolder extends RecyclerView.ViewHolder {

        TextView title,description;
        ImageView coverImage;
        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            coverImage = itemView.findViewById(R.id.image);
        }
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


}
