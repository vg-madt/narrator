package com.example.narrator.comicacctivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.narrator.R;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Integer> imageList = new ArrayList<>();

    public ImageAdapter(Context context) {
        this.context = context;
        setImageList(imageList);
    }

    public void setImageList(ArrayList<Integer> imageList) {
        imageList.add(R.drawable.ch1);
        imageList.add(R.drawable.ch2);
        imageList.add(R.drawable.ch3);
        imageList.add(R.drawable.ch4);
        imageList.add(R.drawable.ch5);
        imageList.add(R.drawable.ch6);
        imageList.add(R.drawable.ch7);
        imageList.add(R.drawable.ch8);
        this.imageList = imageList;
    }


    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageList.get(position));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridLayout.LayoutParams());
        return imageView;
    }
}
