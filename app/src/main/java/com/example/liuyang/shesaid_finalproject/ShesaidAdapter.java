package com.example.liuyang.shesaid_finalproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ShesaidAdapter extends RecyclerView.Adapter<ShesaidAdapter.ViewHolder> {

    private List<Say> mSay;
    static class ViewHolder extends RecyclerView.ViewHolder{
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

    public ShesaidAdapter(List<Say> sayList){
        mSay = sayList;
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
        holder.Icon.setImageResource(say.getId());
        holder.userIcon.setImageResource(say.getId());
        holder.userName.setText(say.getUserName());
        holder.time.setText(say.getTime());
        holder.content.setText(say.getContent());
    }

    @Override
    public int getItemCount() {
        return mSay.size();
    }
}
