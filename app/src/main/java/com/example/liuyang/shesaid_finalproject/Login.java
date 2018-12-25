package com.example.liuyang.shesaid_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Login extends AppCompatActivity {

    private Button login;
    private Button register;
    private EditText user;
    private EditText password;
    public static UserInfo correntUser = new UserInfo();

    public UserInfo getCorrentUser() {
        return correntUser;
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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText().toString().length()!=0 && password.getText().toString().length()!=0){
                    BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
                    query.addWhereEqualTo("user",user.getText().toString());

                    query.findObjects(new FindListener<UserInfo>() {

                        @Override
                        public void done(List<UserInfo> list, BmobException e) {
                            if(list.isEmpty()){
                                Toast.makeText(Login.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                            }else{
                                if(e==null){
                                    UserInfo userinfo = list.get(0);
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


    }
}
