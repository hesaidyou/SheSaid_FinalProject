package com.example.liuyang.shesaid_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private Button login;
    private Button register;
    private EditText user;
    private EditText password;
    public static UserInformation correntUser = new UserInformation();

    public UserInformation getCorrentUser() {
        return correntUser;
    }

    private Button login_qq;

    private static final String TAG = "MainActivity";
    private static final String APP_ID = "1105602574";
    //官方获取的APPID    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    Tencent mTencent = null;

    public Login() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(Login.this, "22dfe54dd26d5e6d6be9a1251adf95f9");
        login = (Button)findViewById(R.id.login_login);
        register = (Button)findViewById(R.id.login_register);
        user = (EditText)findViewById(R.id.login_email_text);
        password = (EditText)findViewById(R.id.login_password_text);
        login_qq = (Button)findViewById(R.id.login_qq);
        mTencent = Tencent.createInstance(APP_ID,Login.this.getApplicationContext());


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText().toString().length()!=0 && password.getText().toString().length()!=0){
                    BmobQuery<UserInformation> query = new BmobQuery<UserInformation>();
                    query.addWhereEqualTo("user",user.getText().toString());

                    query.findObjects(new FindListener<UserInformation>() {

                        @Override
                        public void done(List<UserInformation> list, BmobException e) {
                            if(list.isEmpty()){
                                Toast.makeText(Login.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                            }else{
                                if(e==null){
                                    UserInformation userinfo = list.get(0);
                                    //储存用户信息
                                    correntUser = userinfo;
                                    //for(UserInfo userinfo:list){}
                                    if(userinfo.getPassword().equals(password.getText().toString())){
                                        Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                                        Intent intent =new Intent(Login.this,Main.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(Login.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(Login.this,"网络错误："+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }else{
                    Toast.makeText(Login.this,"请输入用户名和密码",Toast.LENGTH_SHORT).show();
                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });


        login_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIUiListener = new BaseUiListener();
                //all表示获取所有权限
                mTencent.login(Login.this,"all", mIUiListener);
            }
        });

    }


    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Toast.makeText(Login.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;

            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");

                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {

                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG,"登录成功"+response.toString());
                        Intent intent2 =new Intent(Login.this,Main.class);
                        startActivity(intent2);
                    }
                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(Login.this, "授权失败", Toast.LENGTH_SHORT).show();
        }
        @Override

        public void onCancel() {
            Toast.makeText(Login.this, "授权取消", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }        super.onActivityResult(requestCode, resultCode, data);
    }
}
