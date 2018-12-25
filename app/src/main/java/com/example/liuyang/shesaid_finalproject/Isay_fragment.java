package com.example.liuyang.shesaid_finalproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.support.v4.content.ContextCompat.checkSelfPermission;


public class Isay_fragment extends Fragment {

    private Button takephoto;
    private Button album;
    private Button location;
    private Button send;
    private EditText say_content;
    private BmobFile bmobFile;
    private boolean isUploaded = false;
    //获取bmob文件URL
    private String picURL;
    private Main parentActivity;
    //本地文件路径
    private String picPath = "null";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.isay_layout,
                container, false);
        //Bmob.initialize(view.getContext(), "22dfe54dd26d5e6d6be9a1251adf95f9");
        parentActivity = (Main)getActivity();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(view.getContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }
        }

        say_content = (EditText)view.findViewById(R.id.isay_content);
        takephoto = (Button)view.findViewById(R.id.isay_takephoto);
        album = (Button)view.findViewById(R.id.isay_album);
        location = (Button)view.findViewById(R.id.isay_location);
        send = (Button)view.findViewById(R.id.isay_send);
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍摄照片
                //未写完
                // 将本地照片路径赋值给picPath
                picPath = "/sdcard/MIUI/Gallery/cloud/owner/保存/1544520926879.jpeg";///sdcard/DCIM/Camera/IMG_20180807_201639.jpg







                //上传选择照片
                bmobFile = new BmobFile(new File(picPath));
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            picURL = bmobFile.getFileUrl();//--返回的上传文件的完整地址
                            isUploaded = true;
                            Toast.makeText(view.getContext(),"上传文件成功啦",Toast.LENGTH_SHORT).show();//
                        }else{
                            Toast.makeText(view.getContext(),"(╯﹏╰)上传文件失败了、、" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            Log.e("上传文件失败：" , e.getMessage());
                        }
                    }
                    @Override
                    public void onProgress(Integer value) {
                        // 返回的上传进度（百分比）
                        Toast.makeText(view.getContext(),"上传文件进度:"+ value.toString() + "%",Toast.LENGTH_SHORT).show();//
                    }
                });
            }
        });
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从相册中选取照片
                // 未写完
                // 将本地照片路径赋值给picPath
                picPath = "/sdcard/MIUI/Gallery/cloud/owner/保存/1544520926879.jpeg";






                //上传选择照片
                bmobFile = new BmobFile(new File(picPath));
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            picURL = bmobFile.getFileUrl();//--返回的上传文件的完整地址
                            isUploaded = true;
                            Toast.makeText(view.getContext(),"上传照片成功啦",Toast.LENGTH_SHORT).show();//
                        }else{
                            Toast.makeText(view.getContext(),"(╯﹏╰)上传文件失败了、、" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            Log.e("上传文件失败：" , e.getMessage());
                        }
                    }
                    @Override
                    public void onProgress(Integer value) {
                        // 返回的上传进度（百分比）
                        Toast.makeText(view.getContext(),"上传文件进度:"+ value.toString() + "%",Toast.LENGTH_SHORT).show();//
                    }
                });
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
                //判断是否选择照片
                if(!picPath.equals("null")){
                    //判断内容是否为空
                    if(say_content.getText().toString().length()!=0){
                        //获取之前选取的照片路径  在那两个按钮里写
                        //picPath = "/sdcard/DCIM/Camera/IMG_20180807_201639.jpg";//1545540298001_b0dc3bc3-f07b-464a-8f8c-5124bffbc363_by_camera.jpg
                        //获取当前时间
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());
                        String str = formatter.format(curDate);
                        //判断是否上传完成，否则bmobFileURI为NULL
                        if(isUploaded){
                            Say say = new Say();
                            say.setIcon(picURL);
                            say.setUserIcon(Login.correntUser.getIcon());
                            say.setUserName(Login.correntUser.getUser());
                            System.out.println("USER ICON" + Login.correntUser.getIcon());
                            System.out.println("USER NAME" + Login.correntUser.getUser());
                            say.setTime(str);
                            say.setContent(say_content.getText().toString());
                            say.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null){
                                        Toast.makeText(view.getContext(),"Say成功啦!",Toast.LENGTH_SHORT).show();
                                        Log.i("发布Say成功，返回objectId为：", s);
                                    }else{
                                        Toast.makeText(view.getContext(),"(╯﹏╰)发布失败了、、" + e.getMessage(),Toast.LENGTH_SHORT).show();// + e.getMessage()
                                        System.out.println(e.getMessage());
                                    }
                                }
                            });
                            isUploaded = false;

                            initIsay();
                        }else{
                            Toast.makeText(view.getContext(),"耐心等一下哦~照片马上就上传好啦！",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(view.getContext(),"你什么都没有说哦~",Toast.LENGTH_SHORT).show();
                    }
                }else{//还未选择照片
                    Toast.makeText(view.getContext(),"选择一张你喜欢的照片吧~",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void initIsay() {
        say_content.setText("");
        picPath = "null";
    }
}
