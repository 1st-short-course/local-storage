package com.example.rany.local_storage;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.rany.local_storage.util.ExternalStoragePublicDir;
import com.example.rany.local_storage.util.RuntimeRequestPermission;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private Button btnInternalWrite,btnInternalRead, btnInternalDelete;
    private Button btnExternalWritePublic,btnExternalReadPublic, btnExternalDeletePublic;

    public  static final String FILE_NAME="text.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //request permission
        if(!RuntimeRequestPermission.isGrantPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            RuntimeRequestPermission.grantPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        btnInternalDelete=findViewById(R.id.internalDeleteFile);
        btnInternalRead=findViewById(R.id.internalreadFile);
        btnInternalWrite=findViewById(R.id.internalWriteFile);
        btnExternalWritePublic=findViewById(R.id.externalWriteFilePublic);
        btnExternalReadPublic=findViewById(R.id.externalreadFilePublic);
        btnExternalDeletePublic=findViewById(R.id.externalDeleteFilePublic);

        btnInternalWrite.setOnClickListener(v->{
            String content=getString(R.string.content);
            writeToInternalStorage(FILE_NAME,content);
        });

        btnInternalRead.setOnClickListener(v->{
            readInternalStorage(FILE_NAME);
        });

        btnInternalDelete.setOnClickListener(v->{
            deleteFile(FILE_NAME);
            showMessage("delete success");
        });


        btnExternalWritePublic.setOnClickListener(v->{
            String content=getString(R.string.content);

            if(isExternalStorageWritable()){
                showMessage("get File object");
                File file=new ExternalStoragePublicDir()
                        .getPublicDirectoryMusic(MainActivity.this,
                                "local-storage");
                FileWriter fileWriter=null;
                File file1=new File(file,"content.txt");
                try{
                    //fileWriter=new FileWriter(file);
                    //fileWriter.write(content);
                    BufferedWriter writer=new BufferedWriter(new FileWriter(file1));
                    writer.write(content);
                    writer.flush();
                    showMessage("write success");
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                   /* try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }

            }
        });
    }

    private void writeToInternalStorage(String fileName,String content){
        //File file= getFilesDir();
        OutputStream outPutStream=null;
        try {
            outPutStream=openFileOutput(fileName, Context.MODE_PRIVATE);
            outPutStream.write(content.getBytes());
            showMessage("write success");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                outPutStream.close();
            }catch (Exception e){e.printStackTrace();}
        }
    }

    public void showMessage(String smg){
        Toast.makeText(this, smg, Toast.LENGTH_SHORT).show();
    }

    public void readInternalStorage(String fileName){
        InputStream inputStream=null;
        try {
            inputStream=openFileInput(fileName);
            int i =0;
            String content="";
            while ((i =inputStream.read()) !=-1){
                content+=(char)i;
            }
            Log.e(TAG, "readInternalStorage: "+content );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static final String TAG = "MainActivity";

    public boolean isExternalStorageWritable(){
        String state= Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }else
            return false;
    }

    public boolean isExternalStorageReadable(){
        String state= Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }else
            return false;
    }


}
