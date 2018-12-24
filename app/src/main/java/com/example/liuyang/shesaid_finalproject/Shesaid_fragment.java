package com.example.liuyang.shesaid_finalproject;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class Shesaid_fragment extends Fragment {

    private List<Say> sayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shesaid_layout,
                container, false);
        initSays(view);
        RecyclerView re = (RecyclerView)view.findViewById(R.id.shesaid_recyclerview);
        LinearLayoutManager li = new LinearLayoutManager(view.getContext());
        re.setLayoutManager(li);

        ShesaidAdapter adapter = new ShesaidAdapter(sayList);
        re.setAdapter(adapter);

        return view;
    }

    public void initSays(View view){
        Bmob.initialize(view.getContext(), "22dfe54dd26d5e6d6be9a1251adf95f9");


    }
}
