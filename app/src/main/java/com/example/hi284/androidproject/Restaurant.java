package com.example.hi284.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Restaurant extends AppCompatActivity implements RestFragment.OnRestaurantSelectedListener{
    public static final String PREFERENCES_GROUP = "SelectInfo";

    private DBHelper mDbHelper;
    String rest_name;
    ArrayList<MyItem> data = new ArrayList<>();
    SharedPreferences setting;
    String sharedName;
    String sharedNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        setting = getSharedPreferences(PREFERENCES_GROUP, MODE_PRIVATE);

        retreiveInfo();
        mDbHelper = new DBHelper(this);

        Intent intent= getIntent();
        rest_name = intent.getStringExtra("rest_name");
        String rest_number = intent.getStringExtra("rest_number");
        String imgUri = "";
        String rest_address = "";

        Cursor c = mDbHelper.getRest(rest_name, rest_number);
        c.moveToFirst();

        // 4번째 항목인 사진 UriPath를 불러오기
        imgUri = c.getString(c.getColumnIndex("Rest_Pic"));
       Uri imageUri = Uri.parse(imgUri);
       File file = new File(imageUri.getPath());
       try {
           InputStream ims = new FileInputStream(file);
           ImageView imageView = (ImageView) findViewById(R.id.res_img);
           imageView.setImageBitmap(BitmapFactory.decodeStream(ims));
       } catch (FileNotFoundException e) {
           return;
       }

        // 식당이름 불러와 넣기
        TextView tvName = (TextView)findViewById(R.id.res_tv);
        tvName.setText(rest_name);

        // 식당연락처 불러와 넣기
        TextView tvNum = (TextView)findViewById(R.id.res_num);
        tvNum.setText(rest_number);

        // 식당주소 불러와 넣기
        rest_address = c.getString(c.getColumnIndex("Rest_Address"));
        TextView tvAddr = (TextView)findViewById(R.id.res_addr);
        tvAddr.setText(rest_address);

        ImageButton ib = (ImageButton) findViewById(R.id.call_btn);
        ib.setImageResource(R.drawable.call);
        ib.setScaleType(ImageButton.ScaleType.FIT_XY);

        viewAllToListView();
    } // end of onCreate

    // sharedPreferences 에 저장된 값 불러오기
    private void retreiveInfo(){
        if(setting.contains("Selected_Rest_Name") && setting.contains("Selected_Rest_Num")) {
            sharedName = setting.getString("Selected_Rest_Name", "");
            sharedNum = setting.getString("Selected_Rest_Num", "");
        }
    }

    // 옵션메뉴 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rest_add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // 옵션메뉴 눌렸을 시
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_menu:
                Intent intent = new Intent(
                        getApplicationContext(), // 현재화면의 제어권자
                        AddMenuActivity.class);
                intent.putExtra("rest_name", rest_name);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 리스트뷰 설정
    private void viewAllToListView() {

        Cursor mCursor = null;
        mCursor = mDbHelper.getAllMenu(rest_name);

        while(mCursor.moveToNext()){
            data.add(new MyItem(
                    mCursor.getString(mCursor.getColumnIndex("Menu_Pic")),
                    mCursor.getString(mCursor.getColumnIndex("Rest_Menu")),
                    mCursor.getString(mCursor.getColumnIndex("Rest_Price"))));
        }

        MyAdapter adapt = new MyAdapter(this, R.layout.item, data);

        ListView listv = (ListView)findViewById(R.id.res_menu);
        listv.setAdapter(adapt);

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재화면의 제어권자
                        ManuActivity.class); // 다음넘어갈 화면

                // intent 객체에 데이터를 실어서 보내기
                // 리스트뷰 클릭시 인텐트 (Intent) 생성하고 position 값을 이용하여 인텐트로 넘길값들을 넘긴다
                intent.putExtra("img", data.get(i).nPath);
                intent.putExtra("name", data.get(i).nName);
                intent.putExtra("price", data.get(i).nprice);

                startActivity(intent);
            }
        });
        listv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    // 전화걸기 Intent
    public void callButtonClicked(View v) {
        TextView textv = (TextView)findViewById(R.id.res_num);
        String call = textv.getText().toString();
        Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+call));
        startActivity(intent1);
    }

@Override
public void onRestaurantSelected(String img, String name, String price) {
        if (getResources().getConfiguration().orientation
        == Configuration.ORIENTATION_LANDSCAPE) {
        ManuFragment manu = new ManuFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.manu, manu).commit();
        } else {
        Intent intent = new Intent(this, ManuActivity.class);
        intent.putExtra("img", img);
        intent.putExtra("name", name);
        intent.putExtra("price", price);
            startActivity(intent);

        }
        }
}
