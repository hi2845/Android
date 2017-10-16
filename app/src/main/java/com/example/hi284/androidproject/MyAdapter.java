package com.example.hi284.androidproject;



    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.ImageView;
    import android.widget.TextView;


    import java.util.ArrayList;


   public class MyAdapter extends BaseAdapter {
   private Context mContext;
   private int mResource;
   private ArrayList<MyItem> mItems = new ArrayList<MyItem>();


            public MyAdapter(Context context, int resource, ArrayList<MyItem> items) {
                mContext = context;
                mItems = items;
               mResource = resource;
           }


            @Override
     public int getCount() {
                 return mItems.size();
             }


             @Override
     public Object getItem(int position) {
               return mItems.get(position);
           }


             @Override
     public long getItemId(int position) {
                 return position;
             }


             @Override
     public View getView(int position, View convertView, ViewGroup parent) {


                 if (convertView == null) {
                         LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                         convertView = inflater.inflate(mResource, parent,false);
                     }
                 // Set Icon
                 ImageView icon = (ImageView) convertView.findViewById(R.id.imageView1);
                 icon.setImageResource(mItems.get(position).mIcon);
                 icon.setScaleType(ImageView.ScaleType.FIT_XY);


                 // Set Text 01
                 TextView name = (TextView) convertView.findViewById(R.id.textView1);
                 name.setText(mItems.get(position).nName);


                 // Set Text 02
                 TextView age = (TextView) convertView.findViewById(R.id.textView2);
                 age.setText(mItems.get(position).nprice);


                 return convertView;
             }
 }


class MyItem {
     int mIcon; // image resource
     String nName; // text
     String nprice;  // text


     MyItem(int aIcon, String aName, String price) {
                 mIcon = aIcon;
                 nName = aName;
                 nprice = price;
            }
 }
