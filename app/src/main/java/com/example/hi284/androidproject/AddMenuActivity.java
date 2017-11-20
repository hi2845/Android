package com.example.hi284.androidproject;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMenuActivity extends AppCompatActivity {
    EditText MenuName;
    EditText MenuPrice;
    String mCurrentPath;
    String rest_name;

    DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        MenuName = (EditText)findViewById(R.id.edit_menu_name);
        MenuPrice = (EditText)findViewById(R.id.edit_menu_price);
        Intent intent= getIntent();
        rest_name = intent.getStringExtra("rest_name");

        mDbHelper = new DBHelper(this);

        Button imageCaptureBtn = (Button) findViewById(R.id.menu_imageCaptureBtn);
        imageCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        Button insertBtn = (Button)findViewById(R.id.menu_insert);
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertMenu();
            }
        });
    }

    // 식당 추가 버튼 눌렸을 시 추가
    private void insertMenu() {
        EditText mName = (EditText)findViewById(R.id.edit_menu_name);
        EditText mPrice = (EditText)findViewById(R.id.edit_menu_price);
        ImageView imageView = (ImageView) findViewById(R.id.menu_img);

        mDbHelper.insertMenu(rest_name, mName.getText().toString(), mPrice.getText().toString(), mCurrentPath);

        Toast.makeText(this,"Menu Inserted", Toast.LENGTH_SHORT).show();

        mName.setText(null);
        mPrice.setText(null);
        imageView.setImageBitmap(null);
    }

    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mPhotoFileName;
    File mPhotoFile;

    // 찍은 사진 카메라 앱으로 전달 및 저장
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //1. 카메라 앱으로 찍은 이미지를 저장할 파일 객체 생성
            mPhotoFileName = "IMG" + currentDateFormat() + ".jpg";
            mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);
            mCurrentPath = "file:" + mPhotoFile.getAbsolutePath();

            if (mPhotoFile !=null) {
                //2. 생성된 파일 객체에 대한 Uri 객체를 얻기
                Uri imageUri = FileProvider.getUriForFile(this, "com.example.hi284.androidproject", mPhotoFile);
                //3. Uri 객체를 Extras를 통해 카메라 앱으로 전달
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else
                Toast.makeText(getApplicationContext(), "file null", Toast.LENGTH_SHORT).show();
        }
    }

    // Intent 결과값을 받아 처리(사진 띄우기 or 비디오 재생)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView
            Uri imageUri = Uri.parse(mCurrentPath);
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
                ImageView imageView = (ImageView) findViewById(R.id.menu_img);
                imageView.setImageBitmap(BitmapFactory.decodeStream(ims));
            } catch (FileNotFoundException e) {
                return;
            }
        }
    }
}
