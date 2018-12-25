package com.example.liuyang.shesaid_finalproject;

import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.RandomAccessFile;


//这个类用于判断文件是否存在，创建文件夹、创建文件
//示例：
//        String filePath = "/sdcard/Shesaid/";
//        String fileName = "log.txt";
//        writeTxtToFile("txt content", filePath, fileName);
public class FileOperator {

    //创建默认文件夹
    public void createDefaultFilePath(){
        File file = null;
        String filePath = "/sdcard/Shesaid/";
        makeRootDirectory(filePath);
    }



    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            //Toast.makeText(this,"找不到文件",Toast.LENGTH_SHORT).show();
            Log.i("error:", e+"");
        }
    }

    // 将字符串写入到文本文件中
    public void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹    //之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath+fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();

            //Toast.makeText(this,"创建文件成功",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }
}
