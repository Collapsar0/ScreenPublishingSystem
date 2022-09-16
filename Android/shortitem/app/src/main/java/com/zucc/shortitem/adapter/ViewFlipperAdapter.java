package com.zucc.shortitem.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;


public class ViewFlipperAdapter extends BaseAdapter {
    private Context context;
    private List<String> data;
    ImageView imageView;

    public ViewFlipperAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //创建一个ImageView

        imageView = new ImageView(context);

        //设置ImageView的缩放类型

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        //为ImageView设置布局参数

        imageView.setLayoutParams(new ViewGroup.LayoutParams(

                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

        String  url = data.get(position);

        Uri uri = Uri.parse(url);


        Glide.with(this.context).load(uri).into(imageView);

        return imageView;
    }
}
