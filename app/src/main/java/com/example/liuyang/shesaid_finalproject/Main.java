package com.example.liuyang.shesaid_finalproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity implements View.OnClickListener{
    private FragmentManager fragmentManager;
    private Newest_fragment newestFragment;
    private Isay_fragment isayFragment;
    private Shesaid_fragment shesaidFragment;
    private Myself_fragment myselfFragment;
    private Button newestButton;
    private Button isayButton;
    private Button shesaidButton;
    private Button myselfButton;
    private List<View> bottomTabs;
    private View newestView;
    private View isayView;
    private View shesaidView;
    private View myselfView;
    private ImageView mainImage;
    private ImageView coachImage;
    private ImageView worldImage;
    private ImageView myselfImage;
    private TextView mainText;
    private TextView coachText;
    private TextView worldText;
    private TextView myselfText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        initViews();
        fragmentManager = getFragmentManager();
        setSelectTab(0);
    }

    private void initViews() {
        newestView = findViewById(R.id.newestlayout);
        isayView = findViewById(R.id.isaylayout);
        shesaidView = findViewById(R.id.shesaidlayout);
        myselfView =findViewById(R.id.myselflayout);

        mainImage = (ImageView) findViewById(R.id.newest_image);
        coachImage = (ImageView) findViewById(R.id.isay_image);
        worldImage = (ImageView) findViewById(R.id.shesaid_image);
        myselfImage = (ImageView) findViewById(R.id.myself_image);

        mainText = (TextView) findViewById(R.id.newest_text);
        coachText = (TextView) findViewById(R.id.isay_text);
        worldText = (TextView) findViewById(R.id.shesaid_text);
        myselfText = (TextView) findViewById(R.id.myself_text);

        newestView.setOnClickListener(this);
        isayView.setOnClickListener(this);
        shesaidView.setOnClickListener(this);
        myselfView.setOnClickListener(this);

        bottomTabs = new ArrayList<>(4);
        bottomTabs.add(newestView);
        bottomTabs.add(isayView);
        bottomTabs.add(shesaidView);
        bottomTabs.add(myselfView);
    }

    private void setSelectTab(int index) {
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                mainImage.setImageResource(R.drawable.main_main);
                mainText.setTextColor(Color.parseColor("#00c98d"));
                if (newestFragment == null) {
                    newestFragment = new Newest_fragment();
                    transaction.add(R.id.content, newestFragment);
                } else {
                    transaction.show(newestFragment);
                }
                break;
            case 1:
                coachImage.setImageResource(R.drawable.main_coach);
                coachText.setTextColor(Color.parseColor("#00c98d"));
                if (isayFragment == null) {
                    isayFragment = new Isay_fragment();
                    transaction.add(R.id.content, isayFragment);
                } else {
                    transaction.show(isayFragment);
                }


                break;
            case 2:
                worldImage.setImageResource(R.drawable.main_world);
                worldText.setTextColor(Color.parseColor("#00c98d"));
                if (shesaidFragment == null) {
                    shesaidFragment = new Shesaid_fragment();
                    transaction.add(R.id.content, shesaidFragment);
                } else {
                    transaction.show(shesaidFragment);
                }
                break;
            case 3:
                myselfImage.setImageResource(R.drawable.main_me);
                myselfText.setTextColor(Color.parseColor("#00c98d"));
                if (myselfFragment == null) {
                    myselfFragment = new Myself_fragment();
                    transaction.add(R.id.content, myselfFragment);
                } else {
                    transaction.show(myselfFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newestlayout:
                setSelectTab(0);
                break;
            case R.id.isaylayout:
                setSelectTab(1);
                break;
            case R.id.shesaidlayout:
                setSelectTab(2);
                break;
            case R.id.myselflayout:
                setSelectTab(3);
                break;
            default:
                break;
        }
    }

    private void clearSelection() {
        mainImage.setImageResource(R.drawable.main_main);
        mainText.setTextColor(Color.parseColor("#82858b"));
        coachImage.setImageResource(R.drawable.main_coach);
        coachText.setTextColor(Color.parseColor("#82858b"));
        worldImage.setImageResource(R.drawable.main_world);
        worldText.setTextColor(Color.parseColor("#82858b"));
        myselfImage.setImageResource(R.drawable.main_me);
        myselfText.setTextColor(Color.parseColor("#82858b"));
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (newestFragment != null) {
            transaction.hide(newestFragment);
        }
        if (isayFragment != null) {
            transaction.hide(isayFragment);
        }
        if (shesaidFragment != null) {
            transaction.hide(shesaidFragment);
        }
        if (myselfFragment != null) {
            transaction.hide(myselfFragment);
        }
    }

}
