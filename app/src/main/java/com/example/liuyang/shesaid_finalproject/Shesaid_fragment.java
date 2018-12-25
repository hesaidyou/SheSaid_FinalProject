package com.example.liuyang.shesaid_finalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class Shesaid_fragment extends Fragment {

    private List<Say> sayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    //private InAdapter adapter;
    private ShesaidAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shesaid_layout,
                container, false);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(view.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }
        }
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }

        //test
//        List<Say> list = new ArrayList<Say>();
//        Say default_say = new Say();
//        default_say.setIcon("http://bmob-cdn-22994.b0.upaiyun.com/2018/12/25/282dd1254058e5e0805ebc329b00a4ce.png");
//        default_say.setUserIcon("http://bmob-cdn-22994.b0.upaiyun.com/2018/12/25/282dd1254058e5e0805ebc329b00a4ce.png");
//        default_say.setContent("暂无");
//        default_say.setTime("1970年1月1日  8:00:00");
//        default_say.setContent("暂无");
//        for(int i=0;i<10;i++){
//            list.add(default_say);
//        }
        recyclerView = (RecyclerView)view.findViewById(R.id.shesaid_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        initSays();


        return view;
    }

    public void upload(){

    }
    public void initSays(){
        if(sayList.size()!=0){
            sayList.clear();
        }
        BmobQuery<Say> query = new BmobQuery<Say>();
        //查询不是自己的Say
        //query.addWhereNotEqualTo("userName",Login.correntUser.getUser());
        query.addWhereExists("userName");
        query.findObjects(new FindListener<Say>() {
            @Override
            public void done(List<Say> list, BmobException e) {
            if(e==null){
                for(int i = list.size()-1;i>=0;i--){
                    sayList.add(list.get(i));
                    adapter = new ShesaidAdapter(sayList);//sayList
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
//                for(Say say:list){
//                    sayList.add(say);
//                    adapter = new ShesaidAdapter(sayList);//sayList
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                }
            }else{
                //Toast.makeText(view.getContext(),"网络错误："+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
            }
        });

//        Bmob.initialize(view.getContext(), "22dfe54dd26d5e6d6be9a1251adf95f9");
//        new AsyncTask<Void, Void, List<Say>>() {
//            @Override
//            protected List<Say> doInBackground(Void... params) {
//
//                BmobQuery<Say> query = new BmobQuery<Say>();
//                query.addWhereExists("userName");
//
//                query.findObjects(new FindListener<Say>() {
//
//                    @Override
//                    public void done(List<Say> list, BmobException e) {
//                        if(e==null){
//                            for(Say say:list){
//                                sayList.add(say);
//                            }
//                        }else{
//                            //Toast.makeText(view.getContext(),"网络错误："+e.getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                return sayList;
//            }
//
//            @Override
//            protected void onPostExecute(List<Say> list) {
//                //这里的list、应该就是返回的sayList
//                super.onPostExecute(list);
////                adapter = new ShesaidAdapter(list);//sayList
////                recyclerView.setAdapter(adapter);
//                adapter.addAll(list);
//                adapter.notifyDataSetChanged();
//            }
//        }.execute();

    }

//    private class InAdapter extends BaseAdapter {
//        ArrayList<Say> mSay;
//        public InAdapter(){
//            mSay = new ArrayList<>();
//        }
//
//        public void addAll(Collection<Say> collection) {
//            if (isEmpty()) {
//                mSay.addAll(collection);
//                notifyDataSetChanged();
//            } else {
//                mSay.addAll(collection);
//            }
//        }
//        public boolean isEmpty() {
//            return mSay.isEmpty();
//        }
//
//        @Override
//        public int getCount() {
//            return mSay.size();
//        }
//
//        @Override
//        public Say getItem(int position) {
//            if(mSay==null || mSay.size()==0){
//                return null;
//            }
//            return mSay.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            Say say = getItem(position);
//            if (convertView == null) {
//                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_new_item, parent, false);
//                holder = new ViewHolder();
//                convertView.setTag(holder);
//                //convertView.getLayoutParams().width = cardWidth;
//                holder.Icon = (ImageView)convertView.findViewById(R.id.portrait);
//                holder.userIcon = (ImageView)convertView.findViewById(R.id.userIcon);
//                holder.userName = (TextView)convertView.findViewById(R.id.userName);
//                holder.time = (TextView)convertView.findViewById(R.id.timeofD);
//                holder.content = (TextView)convertView.findViewById(R.id.contentS);
//            }else {
//                //Log.e("tag", "recycler convertView");
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//            Glide.with(parent.getContext()).load(say.getIcon())
//                    .centerCrop().placeholder(R.drawable.default_card)
//                    .into(holder.Icon);
//            Glide.with(parent.getContext()).load(say.getUserIcon())
//                    .centerCrop().placeholder(R.drawable.default_card)
//                    .into(holder.userIcon);
//            holder.userName.setText(say.getUserName());
//            holder.time.setText(say.getTime());
//            holder.content.setText(say.getContent());
//
//            return convertView;
//        }
//    }
//
//    private static class ViewHolder {
//        ImageView Icon;
//        ImageView userIcon;
//        TextView userName;
//        TextView time;
//        TextView content;
//    }

}


//        BmobQuery<Say> query = new BmobQuery<Say>();
//        query.addWhereExists("userName");
//
//        query.findObjects(new FindListener<Say>() {
//
//            @Override
//            public void done(List<Say> list, BmobException e) {
//                if(e==null){
//                    for(Say say:list){
//                        sayList.add(say);
//                    }
//                }else{
//                    //Toast.makeText(view.getContext(),"网络错误："+e.getMessage(),Toast.LENGTH_SHORT).show();
//                }
//            }
//        });