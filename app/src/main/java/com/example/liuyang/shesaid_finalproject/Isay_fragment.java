package com.example.liuyang.shesaid_finalproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.example.liuyang.shesaid_finalproject.Tool.Filter;
import com.example.liuyang.shesaid_finalproject.Tool.Utils;
import com.example.liuyang.shesaid_finalproject.Tool.getPhotoFromPhotoAlbum;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import pub.devrel.easypermissions.EasyPermissions;

import android.os.Message;
import android.os.Handler;

import static android.app.Activity.RESULT_OK;

public class Isay_fragment extends Fragment implements EasyPermissions.PermissionCallbacks , AMapLocationListener {

    private Button takephoto;
    private Button album;
    private Button location;
    private Button send;

    private Button grey;
    private Button wumo;
    private Button fanzhuan;
    private Button sumiao;
    private Button xiangsu;


    private ImageView picture;
    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private TextView textView;
    private String[] strMsg;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.isay_layout,
                container, false);
        Bmob.initialize(view.getContext(), "22dfe54dd26d5e6d6be9a1251adf95f9");
        getPermission();

        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");

        picture = (ImageView) view.findViewById(R.id.img_picture);
        textView = (TextView) view.findViewById(R.id.text_map);

        takephoto = (Button) view.findViewById(R.id.isay_takephoto);
        album = (Button) view.findViewById(R.id.isay_album);
        location = (Button) view.findViewById(R.id.isay_location);
        send = (Button) view.findViewById(R.id.isay_send);
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍摄照片

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(view.getContext(), "com.example.liuyang.shesaid_finalproject.fileprovider", cameraSavePath);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    uri = Uri.fromFile(cameraSavePath);
                }
                final String photo = String.valueOf(cameraSavePath);
                System.out.println(photo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 1);
            }
        });
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从相册中选取照片
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //自己位置
                Location();
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
                        if (e == null) {
                            //bmobFile.getFileUrl()--返回的上传文件的完整地址
                            Toast.makeText(view.getContext(), "上传文件成功:" + bmobFile.getFileUrl(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view.getContext(), "上传文件失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        if (e == null) {
                            Toast.makeText(view.getContext(), "添加数据成功，返回objectId为：" + s, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view.getContext(), "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        grey = (Button) view.findViewById(R.id.filter_one);
        wumo = (Button) view.findViewById(R.id.filter_two);
        fanzhuan = (Button) view.findViewById(R.id.filter_three);
        sumiao = (Button) view.findViewById(R.id.filter_four);
        xiangsu = (Button) view.findViewById(R.id.filter_five);

//        grey.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void
// onClick(View v) {
//                picture = Filter.ImageOperation(view.getContext(),photo , 1);
//            }
//        });
//        wumo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println(photo);
//                picture = Filter.ImageOperation(view.getContext(),photo , 2);
//            }
//        });
//        fanzhuan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                picture = Filter.ImageOperation(view.getContext(),photo , 3);
//            }
//        });
//        sumiao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                picture = Filter.ImageOperation(view.getContext(),photo , 4);
//            }
//        });
//        xiangsu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                picture = Filter.ImageOperation(view.getContext(),photo , 5);
//            }
//        });

        return view;
    }

    //获取权限
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this.getActivity(), permissions)) {
            //已经打开权限
            Toast.makeText(this.getActivity(), "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath;
        if (requestCode == 1 && resultCode == RESULT_OK) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }
            Log.d("拍照返回图片路径:", photoPath);
            Glide.with(this).load(photoPath).into(picture);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this.getActivity(), data.getData());
            Glide.with(this).load(photoPath).into(picture);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //成功打开权限
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this.getActivity(), "相关权限获取成功", Toast.LENGTH_SHORT).show();
    }

    //用户未同意权限
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this.getActivity(), "请同意相关权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //框架要求必须这么写
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



    //map *********************  获取位置
    @Override
    public void onLocationChanged(AMapLocation loc) {
        if (null != loc) {
            Message msg = mHandler.obtainMessage();
            msg.obj = loc;
            msg.what = Utils.MSG_LOCATION_FINISH;
            mHandler.sendMessage(msg);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                //定位完成
                case Utils.MSG_LOCATION_FINISH:
                    String result = "";
                    try {
                        AMapLocation loc = (AMapLocation) msg.obj;
                        result = Utils.getLocationStr(loc, 1);
                        strMsg = result.split(",");
                        Toast.makeText(getActivity(), "定位成功", Toast.LENGTH_LONG).show();
                        textView.setText("地址：" + strMsg[0] + "\n" + "经    度：" + strMsg[1] + "\n" + "纬    度：" + strMsg[1]);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "定位失败", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        };

    };

    public void Location() {
        // TODO Auto-generated method stub
        try {
            locationClient = new AMapLocationClient(textView.getContext());
            locationOption = new AMapLocationClientOption();
            // 设置定位模式为低功耗模式
            locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            // 设置定位监听
            locationClient.setLocationListener(this);
            locationOption.setOnceLocation(true);//设置为单次定位
            locationClient.setLocationOption(locationOption);// 设置定位参数
            // 启动定位
            locationClient.startLocation();
            mHandler.sendEmptyMessage(Utils.MSG_LOCATION_START);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "定位失败", Toast.LENGTH_LONG).show();
        }
    }
}
