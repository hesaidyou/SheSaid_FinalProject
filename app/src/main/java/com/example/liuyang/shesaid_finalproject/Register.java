package com.example.liuyang.shesaid_finalproject;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.liuyang.shesaid_finalproject.Tool.getPhotoFromPhotoAlbum;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


public class Register extends AppCompatActivity {

    private TextView register;
    private EditText user;
    private EditText password;
    private EditText passwordconf;
    private RadioGroup sex;
    private RadioButton male;
    private RadioButton female;
    private ImageView icon;
    private Button select;
    private UserInformation userInfo;

    private String localIconPath;
    private String iconPath = "";
    private String userSex;
    private boolean isUploaded = false;
    //touxiang
    private BmobFile bmobFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userInfo = new UserInformation();

        //如果默认头像不存在，则创建
        String dffp = "/sdcard/Shesaid/default_icon.png";
        localIconPath = "/sdcard/Shesaid/default_icon.png";

        File file = new File(dffp);
        if(!file.exists()){
            //生成默认头像
            Resources res = this.getResources();
            BitmapDrawable d = (BitmapDrawable) res.getDrawable(R.drawable.default_card);
            Bitmap img = d.getBitmap();
            String default_icon_name = "default_icon.png";
            localIconPath = "/sdcard/Shesaid/" + default_icon_name;
            try{
                OutputStream os = new FileOutputStream(localIconPath);
                img.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.close();
            }catch(Exception e){
                Log.e("TAG", "", e);
            }
        }

        Bmob.initialize(this,"22dfe54dd26d5e6d6be9a1251adf95f9");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }
        }
        register = (TextView)findViewById(R.id.register_register);
        user = (EditText)findViewById(R.id.register_user);
        password = (EditText)findViewById(R.id.register_password);
        passwordconf = (EditText)findViewById(R.id.register_passwordconf);
        sex = (RadioGroup)findViewById(R.id.register_sex);
        male = (RadioButton)findViewById(R.id.register_sex_M);
        female =(RadioButton)findViewById(R.id.register_sex_F);
        icon = (ImageView)findViewById(R.id.register_icon);
        select = (Button) findViewById(R.id.register_select);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(isUploaded){

                //用户名唯一逻辑未写
                if(TextUtils.isEmpty(user.getText()) || TextUtils.isEmpty(password.getText()) ||
                        TextUtils.isEmpty(passwordconf.getText()) ||
                        (sex.getCheckedRadioButtonId()!=male.getId() && sex.getCheckedRadioButtonId()!=female.getId())){
                    Toast.makeText(v.getContext(),"请填写完整信息",Toast.LENGTH_SHORT).show();
                }else if(!password.getText().toString().equals(passwordconf.getText().toString())){
                    Toast.makeText(v.getContext(),"请两次输入相同密码",Toast.LENGTH_SHORT).show();
                } else{
                    iconPath = bmobFile.getFileUrl();
                    createUser(v);
                }
            }else{
                Toast.makeText(v.getContext(),"请您等待头像上传完成",Toast.LENGTH_SHORT).show();
            }

            }
        });

        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == male.getId()){
                userSex = "男";
            }else if(checkedId == female.getId()){
                userSex = "女";
            }
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
            //选择相册图片
            //在（ImageView）icon上展示图片，把路径储存到iconPath中
            localIconPath = "";
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 2);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath = "";
        if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            Glide.with(this).load(photoPath).into(icon);
        }

        localIconPath = photoPath;
        super.onActivityResult(requestCode, resultCode, data);
        final Context context = this;
        //上传图片
        bmobFile = new BmobFile(new File(localIconPath));
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    isUploaded = true;
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Toast.makeText(context,"上传头像成功啦！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"上传头像失败了sad",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onProgress(Integer value) {
                //super.onProgress(value);
            }
        });
    }

    //BMOB添加数据
    private void createUser(final View view){
        userInfo.setUser(user.getText().toString());
        userInfo.setPassword(password.getText().toString());
        userInfo.setSex(userSex);
        userInfo.setIcon(iconPath);
        userInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(view.getContext(),"注册成功，返回objectId为："+ s,Toast.LENGTH_SHORT).show();

                    Intent intent1 =new Intent(Register.this,Login.class);
                    startActivity(intent1);
                }else{
                    Toast.makeText(view.getContext(),"注册失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getResourcesUri(@DrawableRes int id) {
        Resources resources = getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }
}
