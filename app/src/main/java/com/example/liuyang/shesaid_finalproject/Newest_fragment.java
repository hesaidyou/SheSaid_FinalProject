package com.example.liuyang.shesaid_finalproject;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class Newest_fragment extends Fragment implements SwipeAdapterView.onFlingListener,
        SwipeAdapterView.OnItemClickListener{

    String [] headerIcons = {
//            "http://www.5djiaren.com/uploads/2015-04/17-115301_29.jpg",
//            "http://img1.dzwww.com:8080/tupian_pl/20160106/32/4152697013403556460.jpg",
//            "http://c.hiphotos.baidu.com/zhidao/pic/item/72f082025aafa40f191362cfad64034f79f019ce.jpg",
//            "http://img.article.pchome.net/new/w600/00/35/15/66/pic_lib/wm/122532981493137o3iegiyx.jpg",
//            "http://img0.imgtn.bdimg.com/it/u=3382799710,1639843170&fm=21&gp=0.jpg",
//            "http://i2.sinaimg.cn/travel/2014/0918/U7398P704DT20140918143217.jpg",
//            "http://photo.l99.com/bigger/21/1415193165405_4sg3ds.jpg",
//            "http://img.pconline.com.cn/images/upload/upc/tx/photoblog/1305/15/c2/20949108_20949108_1368599174341.jpg",
//            "http://pic29.nipic.com/20130501/12558275_114724775130_2.jpg",
//            "http://photo.l99.com/bigger/20/1415193157174_j2fa5b.jpg",
//            "http://bmob-cdn-22994.b0.upaiyun.com/2018/12/25/0237097e761548d9bff9c2d181cfa9c9.jpg"
            "http://bmob-cdn-22994.b0.upaiyun.com/2018/12/25/c643985332fd4f0389f2dd3962d0a9d8.jpeg"};


    String [] names = {"张三","龙淇伟","王五","小明"};

    String [] times = {"2018年12月23日 10:09:25", "2018年12月23日 10:09:25", "2018年12月23日 10:09:25", "2018年12月23日 10:09:25"};

    String [] contents = {"111111111111111111111111111111111111111111", "2222222222222222222222222222222222222222222222222222222222222",
            "3333333333333333333333333333333333333333333333333333", "44444444444444444444444444444444444444444444444444444444444"};



    Random ran = new Random();

    private int cardWidth;
    private int cardHeight;

    private SwipeAdapterView swipeView;
    private InnerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View newestLayout = inflater.inflate(R.layout.newest_layout,
                container, false);
        //在fragment中获取主程序activity
        Activity a = this.getActivity();
        //Bmob.initialize(newestLayout.getContext(), "22dfe54dd26d5e6d6be9a1251adf95f9");

        initView(newestLayout);
        loadData();

        return newestLayout;
    }


    private void initView(View newestLayout) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        cardWidth = (int) (dm.widthPixels - (2 * 18 * density));
        cardHeight = (int) (dm.heightPixels - (338 * density));

        swipeView = (SwipeAdapterView)newestLayout.findViewById(R.id.swipe_view);
//        swipeView.setIsNeedSwipe(false);
        swipeView.setFlingListener(this);
        swipeView.setOnItemClickListener(this);

        adapter = new InnerAdapter();
        swipeView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(View v, Object dataObject) {
        Log.i("tag", "click top view");
    }

    @Override
    public void removeFirstObjectInAdapter(View topView) {
        adapter.remove(0);
    }

    @Override
    public void onLeftCardExit(Object dataObject) {
        Log.i("tag", "swipe left");
    }

    @Override
    public void onRightCardExit(Object dataObject) {
        Log.i("tag", "swipe right");
    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        if (itemsInAdapter == 3) {
            loadData();
        }
    }

    @Override
    public void onScroll(float progress, float scrollXProgress) {

    }

    private ArrayList<Talent> list = new ArrayList<>(10);
    private void loadData() {
        new AsyncTask<Void, Void, List<Talent>>() {
            @Override
            protected List<Talent> doInBackground(Void... params) {

                Talent talent;
                for (int i = 0; i < 10; i++) {
                    //修改图片数量！！！！！
                    talent = new Talent();
                    talent.headerIcon = headerIcons[0];
                    talent.userIcon = headerIcons[0];
                    talent.userName = names[ran.nextInt(names.length-1)];
                    talent.timeofD = times[ran.nextInt(times.length-1)];
                    talent.contentS = contents[ran.nextInt(contents.length-1)];
                    list.add(talent);
                }

//                BmobQuery<Say> query = new BmobQuery<Say>();
//                query.addWhereExists("userName");
//                query.findObjects(new FindListener<Say>() {
//                    @Override
//                    public void done(List<Say> query_list, BmobException e) {
//                        if(e==null){
//                            for(Say say:query_list){
//                                Talent talent1 = new Talent();
//                                talent1.headerIcon = say.getIcon();
//                                talent1.userIcon = say.getUserIcon();
//                                talent1.contentS = say.getContent();
//                                talent1.timeofD = say.getTime();
//                                talent1.userName = say.getUserName();
//                                list.add(talent1);
//                            }
//                        }else{
//                            //Toast.makeText(view.getContext(),"网络错误："+e.getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });


                return list;
            }

            @Override
            protected void onPostExecute(List<Talent> list) {
                super.onPostExecute(list);
                adapter.addAll(list);
            }
        }.execute();
    }

    class InnerAdapter extends BaseAdapter implements View.OnClickListener {

        ArrayList<Talent> objs;

        public InnerAdapter() {
            objs = new ArrayList<>();
        }

        public void addAll(Collection<Talent> collection) {
            if (isEmpty()) {
                objs.addAll(collection);
                notifyDataSetChanged();
            } else {
                objs.addAll(collection);
            }
        }

        public void clear() {
            objs.clear();
            notifyDataSetChanged();
        }

        public boolean isEmpty() {
            return objs.isEmpty();
        }

        public void remove(int index) {
            if (index > -1 && index < objs.size()) {
                objs.remove(index);
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return objs.size();
        }

        @Override
        public Talent getItem(int position) {
            if(objs==null ||objs.size()==0) return null;
            return objs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            Talent talent = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_new_item, parent, false);
                holder = new ViewHolder();
                convertView.setTag(holder);
                convertView.getLayoutParams().width = cardWidth;
                holder.portraitView = (ImageView) convertView.findViewById(R.id.portrait);
                //holder.portraitView.getLayoutParams().width = cardWidth;
                holder.portraitView.getLayoutParams().height = cardHeight;
                holder.userIconView = (ImageView)convertView.findViewById(R.id.userIcon);
                holder.userNameView = (TextView) convertView.findViewById(R.id.userName);
                //parentView.getLayoutParams().width = cardWidth;
                //holder.jobView = (TextView) convertView.findViewById(R.id.job);
                //holder.companyView = (TextView) convertView.findViewById(R.id.company);
                holder.timeofDView = (TextView) convertView.findViewById(R.id.timeofD);
                holder.contentSView = (TextView) convertView.findViewById(R.id.contentS);
            } else {
                //Log.e("tag", "recycler convertView");
                holder = (ViewHolder) convertView.getTag();
            }

            Glide.with(parent.getContext()).load(talent.headerIcon)
                    .centerCrop().placeholder(R.drawable.default_card)
                    .into(holder.portraitView);
            Glide.with(parent.getContext()).load(talent.userIcon)
                    .centerCrop().placeholder(R.drawable.default_card)
                    .into(holder.userIconView);
            holder.userNameView.setText(String.format("%s", talent.userName));

            final CharSequence no = "暂无";

            holder.timeofDView.setHint(no);
            holder.timeofDView.setText(talent.timeofD);
            //holder.cityView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_location,0,0);

            holder.contentSView.setHint(no);
            holder.contentSView.setText(talent.contentS);
            //holder.eduView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_edu,0,0);

            //holder.workView.setHint(no);
            //holder.workView.setText(talent.workYearName);
            //holder.workView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_work_year,0,0);


            return convertView;
        }

        @Override
        public void onClick(View v) {
            Log.i("tag", "onClick: " + v.getTag());
        }
    }

    private static class ViewHolder {
        ImageView portraitView;
        ImageView userIconView;
        TextView userNameView;
        TextView timeofDView;
        TextView contentSView;
    }

    public static class Talent {
        public String headerIcon;
        public String userIcon;
        public String userName;
        public String timeofD;
        public String contentS;
    }
}
