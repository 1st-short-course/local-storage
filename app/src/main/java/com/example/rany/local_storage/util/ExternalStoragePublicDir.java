package com.example.rany.local_storage.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

public class ExternalStoragePublicDir {

    public File getPublicDirectoryMusic(Context context,String albumName){
        File file=new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
                albumName
        );

        if(!file.mkdirs()){
            Toast.makeText(context, albumName+" created", Toast.LENGTH_SHORT).show();
        }

        return file;
    }
}
