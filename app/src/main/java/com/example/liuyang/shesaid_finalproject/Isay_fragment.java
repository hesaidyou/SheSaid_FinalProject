package com.example.liuyang.shesaid_finalproject;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class Isay_fragment extends Fragment {

    private Button takephoto;
    private Button album;
    private Button location;
    private Button send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.isay_layout,
                container, false);
        Bmob.initialize(view.getContext(), "22dfe54dd26d5e6d6be9a1251adf95f9");

        takephoto = (Button)view.findViewById(R.id.isay_takephoto);
        album = (Button)view.findViewById(R.id.isay_album);
        location = (Button)view.findViewById(R.id.isay_location);
        send = (Button)view.findViewById(R.id.isay_send);
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍摄照片
            }
        });
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从相册中选取照片
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //自己位置
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String picPath = "http://photo.l99.com/bigger/21/1415193165405_4sg3ds.jpg";
                final BmobFile bmobFile = new BmobFile(new File(picPath));
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            //bmobFile.getFileUrl()--返回的上传文件的完整地址
                            Toast.makeText(view.getContext(),"上传文件成功:" + bmobFile.getFileUrl(),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(view.getContext(),"上传文件失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onProgress(Integer value) {
                        // 返回的上传进度（百分比）
                    }
                });

                Say say = new Say();
                say.setIcon("http://photo.l99.com/bigger/21/1415193165405_4sg3ds.jpg");
                say.setUserIcon("http://photo.l99.com/bigger/21/1415193165405_4sg3ds.jpg");
                say.setUserName("tom");
                say.setTime("2018年12月23日 10:50:01");
                say.setContent("hao123");
                say.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(view.getContext(),"添加数据成功，返回objectId为："+ s,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(view.getContext(),"创建数据失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}
