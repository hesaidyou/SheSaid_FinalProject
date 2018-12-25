package com.example.liuyang.shesaid_finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShesaidAdapter extends RecyclerView.Adapter<ShesaidAdapter.ViewHolder> {

    private List<Say> mSay;

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Icon;
        ImageView userIcon;
        TextView userName;
        TextView time;
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            Icon = (ImageView)itemView.findViewById(R.id.portrait);
            userIcon = (ImageView)itemView.findViewById(R.id.userIcon);
            userName = (TextView)itemView.findViewById(R.id.userName);
            time = (TextView)itemView.findViewById(R.id.timeofD);
            content = (TextView) itemView.findViewById(R.id.contentS);
        }
    }

    public ShesaidAdapter(List<Say>sayList){
        mSay = sayList;
        //mSay = new ArrayList<Say>();
    }

    public void addAll(Collection<Say> collection) {
        if (isEmpty()) {
            mSay.addAll(collection);
            notifyDataSetChanged();
        } else {
            mSay.addAll(collection);
            notifyDataSetChanged();
        }
    }
    public boolean isEmpty() {
        return mSay.isEmpty();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_new_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Say say = mSay.get(position);
        //holder.Icon.setImageURI(Uri.parse(say.getIcon()));
        //holder.userIcon.setImageURI(Uri.parse(say.getUserIcon()));
        holder.Icon.setImageBitmap(returnBitMap(say.getIcon()));
        holder.userIcon.setImageBitmap(returnBitMap(say.getUserIcon()));
        holder.userName.setText(say.getUserName());
        holder.time.setText(say.getTime());
        holder.content.setText(say.getContent());
    }
    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public int getItemCount() {
        return mSay.size();
    }
}
