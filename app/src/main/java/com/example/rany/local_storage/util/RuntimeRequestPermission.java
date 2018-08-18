package com.example.rany.local_storage.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class RuntimeRequestPermission {

    public static boolean isGrantPermission(Context context,String permission){
        if(ContextCompat.checkSelfPermission(context,permission)!=
                PackageManager.PERMISSION_GRANTED){
            return false;
        }else{
            return true;
        }
    }

    public static void grantPermission(AppCompatActivity activity, String permission){
        ActivityCompat.requestPermissions(activity,
                new String[]{permission} ,1);
    }
}
