package com.example.hi284.androidproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;

public class Restaurant extends AppCompatActivity implements AdapterView.OnItemClickListener, RestFragment.OnTitleSelectedListener {
    private DBHelper mDbHelper;

    int id;


    String rName;
    String rPhone;
    String rAddr;
    String pic;

    String svname;
    String svphone;
    String svaddr;



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

    }

    // 전화걸기 Intent
    public void callButtonClicked(View v) {
        TextView textv = (TextView)findViewById(R.id.res_num);
        String call = textv.getText().toString();
        Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+call));
        startActivity(intent1);
    }

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
