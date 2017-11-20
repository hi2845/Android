package com.example.hi284.androidproject;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManuFragment extends Fragment {

    int pic=0;
    String name;
    String price;


    public ManuFragment() {
        // Required empty public constructor
    }

    public void setpic(int i){pic =i;}
    public void setname(String i){name =i;}
    public void setprice(String i){price =i;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manu, container, false);
        ImageView iv = (ImageView)view.findViewById(R.id.imageView1);
        TextView tvTitle = (TextView)view.findViewById(R.id.textView1);
        TextView tvArtist = (TextView)view.findViewById(R.id.textView2);


        iv.setImageResource(pic);
        tvTitle.setText(name);
        tvArtist.setText(price);


        return view;
    }

}
