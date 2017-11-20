package com.example.hi284.androidproject;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ManuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu);


<<<<<<< HEAD
        //레스토랑 액티비티에서 값을 받아 메뉴 프레그먼트 실행한다
        ManuFragment manus = new ManuFragment();
        manus.setpic(getIntent().getIntExtra("img",0));
        manus.setname(getIntent().getStringExtra("name"));
        manus.setprice(getIntent().getStringExtra("price"));
        getSupportFragmentManager().beginTransaction().replace(R.id.manu, manus).commit();
=======
        Intent intent = getIntent(); // 보내온 Intent를 얻는다

        String imgUri = "";
        imgUri = intent.getStringExtra("img");
        Uri imageUri = Uri.parse(imgUri);
        File file = new File(imageUri.getPath());
        try {
            InputStream ims = new FileInputStream(file);
            ImageView imageView = (ImageView) findViewById(R.id.imageView1);
            imageView.setImageBitmap(BitmapFactory.decodeStream(ims));
        } catch (FileNotFoundException e) {
            return;
        }

        tvTitle.setText(intent.getStringExtra("name"));
        tvArtist.setText(intent.getStringExtra("price"));
>>>>>>> e83a8865f32bc4ad886a1e64a3183e19a24ebb9e

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            Drawable drawable = getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp);
            if(drawable != null) {
                drawable.setTint(Color.WHITE);
                actionBar.setHomeAsUpIndicator(drawable);
            }
        }

    }
}
