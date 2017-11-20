package com.example.hi284.androidproject;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

<<<<<<< HEAD
public class Restaurant extends AppCompatActivity implements AdapterView.OnItemClickListener, RestFragment.OnTitleSelectedListener {
=======
public class Restaurant extends AppCompatActivity {
>>>>>>> e83a8865f32bc4ad886a1e64a3183e19a24ebb9e
    private DBHelper mDbHelper;
    String rest_name;
    ArrayList<MyItem> data = new ArrayList<>();

<<<<<<< HEAD
    int id;


    String rName;
    String rPhone;
    String rAddr;
    String pic;

    String svname;
    String svphone;
    String svaddr;

=======
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

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
>>>>>>> e83a8865f32bc4ad886a1e64a3183e19a24ebb9e

    // 옵션메뉴 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rest_add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

<<<<<<< HEAD
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        /*mDbHelper = new DBHelper(this);

        //정보를 받아온후 저장
        id=getIntent().getIntExtra("KEY",0);

        if(getIntent().getStringExtra("name")!=null){
            rName=getIntent().getStringExtra("name");}
        if(getIntent().getStringExtra("phone")!=null){
            rPhone=getIntent().getStringExtra("phone");}
        if(getIntent().getStringExtra("addr")!=null){
            rAddr=getIntent().getStringExtra("addr");}

        //insertRest();



        //레스토랑 프래그먼트 실행
        RestFragment rest= new RestFragment();
        if(rName!=null){
        rest.setrName(rName);}
        if(rPhone!=null){
        rest.setrPhone(rPhone);}
        if(rAddr!=null){
        rest.setrAddr(rAddr);}
        getSupportFragmentManager().beginTransaction().replace(R.id.title, rest).commit();*/

    }


     // end of onCreate

    @Override
    public void onTitleSelected(int pic,String name, String price,String rName, String rPhone ,String rAddr) {
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {

            //landscape 상태이면 fragment_restaurant(land)의 container에 메뉴프래그먼트가 들어간다

            ManuFragment manus = new ManuFragment();
            manus.setpic(pic);
            manus.setname(name);
            manus.setprice(price);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, manus).commit();
        } else {
            Intent intent = new Intent(
                    getApplicationContext(), // 현재화면의 제어권자
                    ManuActivity.class); // 다음넘어갈 화면

            // intent 객체에 데이터를 실어서 보내기
            // 리스트뷰 클릭시 인텐트 (Intent) 생성하고 position 값을 이용하여 인텐트로 넘길값들을 넘긴다
            intent.putExtra("img",pic);
            intent.putExtra("name", name);
            intent.putExtra("price", price);
            startActivity(intent);
        }
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

=======
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
>>>>>>> e83a8865f32bc4ad886a1e64a3183e19a24ebb9e
    }

    // 전화걸기 Intent
    public void callButtonClicked(View v) {
        TextView textv = (TextView)findViewById(R.id.res_num);
        String call = textv.getText().toString();
        Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+call));
        startActivity(intent1);
    }
<<<<<<< HEAD

    private void insertRest() {
        String name=rName;
        String phone=rPhone;
        String addr=rAddr;
        String img=pic;


        long nOfRows = mDbHelper.insertRest(name,phone,addr,pic);
        if (nOfRows >0)
            Toast.makeText(this,nOfRows+" Record Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"No Record Inserted", Toast.LENGTH_SHORT).show();
    }

    @Override


    protected void onSaveInstanceState(Bundle outState) {

        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);


    }


}
=======
}
>>>>>>> e83a8865f32bc4ad886a1e64a3183e19a24ebb9e
