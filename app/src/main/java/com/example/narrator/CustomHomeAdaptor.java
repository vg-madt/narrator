package com.example.narrator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.narrator.classes.ComicFavourites;
import com.example.narrator.classes.ComicStory;
import com.example.narrator.classes.Service;
import com.example.narrator.classes.TextFavourites;
import com.example.narrator.classes.TextStory;
import com.example.narrator.classes.Type;
import com.example.narrator.comicreader.ShowComicChapter;
import com.example.narrator.textreader.ShowTextChapter;

import java.util.ArrayList;
import java.util.List;

public class CustomHomeAdaptor<T extends Type> extends RecyclerView.Adapter{

    private
    Context context;
    private ArrayList<T> storyList;
    int UID;

    public CustomHomeAdaptor(Context context, ArrayList<T> storyList,Integer UID) {
        this.context = context;
        this.storyList = storyList;
        this.UID = UID;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof TextViewHolder) {
            final TextStory textStory = (TextStory) storyList.get(position);
            ((TextViewHolder) holder).title.setText(textStory.getTitle());
            ((TextViewHolder) holder).description.setText(textStory.getDescription());
            String cover = textStory.getCoverImage();
            Bitmap cI = stringToBitMap(cover);
            Drawable drawable = new BitmapDrawable(cI);
            ((TextViewHolder) holder).coverImage.setImageDrawable(drawable);
            ((TextViewHolder) holder).coverImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ShowTextChapter.class);
                    Bundle display = new Bundle();
                    display.putInt("TID",textStory.getTID());
                    display.putInt("UID",textStory.getUID());
                    intent.putExtras(display);
                    v.getContext().startActivity(intent);
                }
            });

            ((TextViewHolder) holder).coverImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final TextStory textStory = (TextStory) storyList.get(position);
                    final int TID = textStory.getTID();

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                    builder.setTitle("Add to Favourite");
                    builder.setMessage("Do you want to add to Favourites");
                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        public void onClick(DialogInterface dialog, int which) {
                            //add text story and user ID to fav

                            TextFavourites textFavourites = new TextFavourites(TID,UID);
                            Service service = new Service();
                            service.post("http://10.0.2.2:8080/narrator/textFavourites/write",textFavourites,TextFavourites.class);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;
                }
            });
        }else if(holder instanceof ComicViewHolder){
            final ComicStory textStory = (ComicStory) storyList.get(position);
            ((ComicViewHolder) holder).title.setText(textStory.getTitle());
            ((ComicViewHolder) holder).description.setText(textStory.getDescription());
            String cover = textStory.getCoverImage();
            Bitmap cI = stringToBitMap(cover);
            Drawable drawable = new BitmapDrawable(cI);
            ((ComicViewHolder) holder).coverImage.setImageDrawable(drawable);
            ((ComicViewHolder) holder).coverImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ShowComicChapter.class);
                    Bundle display = new Bundle();
                    display.putInt("CID",textStory.getCID());
                    display.putInt("UID",textStory.getUID());
                    intent.putExtras(display);
                    v.getContext().startActivity(intent);
                }
            });

            ((ComicViewHolder) holder).coverImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final ComicStory textStory = (ComicStory) storyList.get(position);
                    final int CID = textStory.getCID();

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                    builder.setTitle("Add to Favourite");
                    builder.setMessage("Do you want to add to Favourites");
                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        public void onClick(DialogInterface dialog, int which) {
                            //add text story and user ID to fav

                            ComicFavourites comicFavourites = new ComicFavourites(CID,UID);
                            Service service = new Service();
                            service.post("http://10.0.2.2:8080/narrator/comicFavourites/write",comicFavourites,ComicFavourites.class);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;
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
        public TextViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            coverImage = itemView.findViewById(R.id.image);
        }
    }

    public static class ComicViewHolder extends RecyclerView.ViewHolder {

        TextView title,description;
        ImageView coverImage;
        public ComicViewHolder( View itemView) {
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
