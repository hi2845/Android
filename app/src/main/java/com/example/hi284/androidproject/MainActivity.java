package com.example.hi284.androidproject;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText mName;
    EditText mPhone;
    EditText mAddress;
    String mCurrentPath;

    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = (EditText)findViewById(R.id.edit_name);
        mPhone = (EditText)findViewById(R.id.edit_phone);
        mAddress= (EditText)findViewById(R.id.edit_address);

        mDbHelper = new DBHelper(this);

        checkDangerousPermissions();

        Button imageCaptureBtn = (Button) findViewById(R.id.main_imageCaptureBtn);
        imageCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        Button insertBtn = (Button)findViewById(R.id.insert);
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRest();
                viewAllToListView();
            }
        });

        Button deleteBtn = (Button)findViewById(R.id.delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRest();
                viewAllToListView();
            }
        });

        viewAllToListView();
    }

    // 접근권한 설정
    final int  REQUEST_EXTERNAL_STORAGE_FOR_MULTIMEDIA=1;
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_EXTERNAL_STORAGE_FOR_MULTIMEDIA);

        }

    }

    private void viewAllToListView() {

        Cursor cursor = mDbHelper.getAllRest();

        android.widget.SimpleCursorAdapter adapter = new android.widget.SimpleCursorAdapter(getApplicationContext(),
                R.layout.item2, cursor, new String[]{
                ResContract.Rests.REST_NAME,
                ResContract.Rests.REST_NUM,},
                new int[]{R.id.item2_name, R.id.item2_number}, 0);


        ListView listv = (ListView)findViewById(R.id.listview);
        listv.setAdapter(adapter);

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();

                mName.setText(((Cursor)adapter.getItem(i)).getString(1));
                mPhone.setText(((Cursor)adapter.getItem(i)).getString(2));
                mAddress.setText(((Cursor)adapter.getItem(i)).getString(3));

                Intent intent = new Intent(
                        getApplicationContext(), // 현재화면의 제어권자
                        Restaurant.class); // 다음넘어갈 화면
                intent.putExtra("name",((Cursor)adapter.getItem(i)).getString(1));
                intent.putExtra("phone",((Cursor)adapter.getItem(i)).getString(2));
                intent.putExtra("addr",((Cursor)adapter.getItem(i)).getString(3));
                startActivity(intent);
            }
        });
        listv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    // 식당 삭제 버튼 눌렸을 시 삭제
    private void deleteRest() {
        EditText _name = (EditText)findViewById(R.id.edit_name);
        EditText _num = (EditText)findViewById(R.id.edit_phone);

        long nOfRows = mDbHelper.deleteRest(_name.getText().toString(), _num.getText().toString());
        if (nOfRows >0)
            Toast.makeText(this,"Record Deleted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"No Record Deleted", Toast.LENGTH_SHORT).show();
    }

    // 식당 추가 버튼 눌렸을 시 추가
    private void insertRest() {
        EditText name = (EditText)findViewById(R.id.edit_name);
        EditText phone = (EditText)findViewById(R.id.edit_phone);
        EditText address= (EditText)findViewById(R.id.edit_address);

        long nOfRows = mDbHelper.insertRest(name.getText().toString(),phone.getText().toString(),address.getText().toString(),mCurrentPath);
        if (nOfRows >0)
            Toast.makeText(this,nOfRows+" Record Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"No Record Inserted", Toast.LENGTH_SHORT).show();
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
                ImageView imageView = (ImageView) findViewById(R.id.main_restimg);
                imageView.setImageBitmap(BitmapFactory.decodeStream(ims));
            } catch (FileNotFoundException e) {
                return;
            }
        }
    }
}