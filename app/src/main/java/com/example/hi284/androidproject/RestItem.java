package com.example.hi284.androidproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by jkm21 on 2017-11-16.
 */

public class RestItem {
    String resPicPath;
    String resName;
    String resAddr;
    String resNum;
    Bitmap resPic;

    public RestItem(String resPicPath, String resName, String resAddr, String resNum){
        this.resPicPath = resPicPath;
        this.resName = resName;
        this.resAddr = resAddr;
        this.resNum = resNum;
        resPic = BitmapFactory.decodeFile(resPicPath.toString());
    }
}
