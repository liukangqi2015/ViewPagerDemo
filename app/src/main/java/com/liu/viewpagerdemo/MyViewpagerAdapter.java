package com.liu.viewpagerdemo;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by liu on 2016/12/23.
 */

public class MyViewpagerAdapter extends PagerAdapter {
    private ArrayList<View> views=new ArrayList<>();
    private int[] resId;
    private LayoutInflater inflater;


    public MyViewpagerAdapter(ArrayList<View> views) {
        this.views = views;
    }

    public MyViewpagerAdapter(int[] resID){
        this.resId=resID;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        Log.e("remove","position:"+position+" position%views.size():"+position%views.size());
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        Log.e("add","position:"+position+" position%views.size():"+position%views.size());
//        View view=views.get(position%views.size());
        if (inflater==null){
            inflater=LayoutInflater.from(container.getContext());
        }
        View view=inflater.inflate(resId[position%resId.length],container,false);
//        Log.e("add",view.getParent()+"");
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
//        if (views==null||views.size()<=0)
//            return 0;
//        if (views.size()==1){
//            return 1;
//        }
        if (resId==null)
            return 0;
        if (resId.length==1){
            return 1;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
