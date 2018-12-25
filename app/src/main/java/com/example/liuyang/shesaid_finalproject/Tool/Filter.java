package com.example.liuyang.shesaid_finalproject.Tool;

import android.content.Context;
import android.graphics.PointF;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;

public class Filter {
    private String url;

    public static ImageView ImageOperation(Context context, String url, int select){
        ImageView img = null;

        switch (select){
            case 1:
                // 使用构造方法 GrayscaleTransformation(Context context)
                Glide.with(context)
                        .load(url)
                        .bitmapTransform(new GrayscaleTransformation(context))
                        .into(img);
            case 2: //乌墨色
                // 使用构造方法 SepiaFilterTransformation(Context context, float intensity)
                // intensity 渲染强度（单参构造器 - 默认1.0F）
                Glide.with(context)
                        .load(url)
                        .bitmapTransform(new SepiaFilterTransformation(context, 1.0F))
                        .into(img);
            case 3: //反转
                Glide.with(context)
                        .load(url)
                        .bitmapTransform(new InvertFilterTransformation(context))
                        .into(img);
            case 4:  //素描
                Glide.with(context)
                        .load(url)
                        .bitmapTransform(new SketchFilterTransformation(context))
                        .into(img);

            case 5: //像素画
                Glide.with(context)
                        .load(url)
                        .bitmapTransform(new PixelationFilterTransformation(context, 20F))
                        .into(img);
            case 6: //旋转
                Glide.with(context)
                        .load(url)
                        .bitmapTransform(new SwirlFilterTransformation(context, 1.0F, 0.4F, new PointF(0.5F, 0.5F)))
                        .into(img);

        }


        return img;
    }


}
