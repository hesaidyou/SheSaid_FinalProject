package com.example.liuyang.shesaid_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Welcome extends AppCompatActivity {

    private TextView enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //文件处理自定义工具
        FileOperator fileOperator = new FileOperator();
        //生成默认路径
        fileOperator.createDefaultFilePath();

        enter = (TextView) findViewById(R.id.welcome_txt_enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(Welcome.this,Login.class);
                startActivity(intent1);
            }
        });

        final Intent it = new Intent(this, Login.class); //你要转向的Activity
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
            }
        };
        timer.schedule(task, 1000 * 3); //3秒后
    }
}
