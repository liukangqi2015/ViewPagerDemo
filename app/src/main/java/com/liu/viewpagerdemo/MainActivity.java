package com.liu.viewpagerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 简单地轮播图Demo
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private ArrayList<View> views = new ArrayList<>();
    private ArrayList<View> dot_views=new ArrayList<>();
    private int[] resIDs = {R.layout.view01, R.layout.view02, R.layout.view03, R.layout.view04};
    private Handler handler=new VPHandler(this);
    private Timer timer;
    private TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
    }

    private void intiView() {
//        LayoutInflater from = LayoutInflater.from(this);
        viewPager = (ViewPager) findViewById(R.id.vp);
        linearLayout= (LinearLayout) findViewById(R.id.ll);
//        for (int i = 0; i < 3; i++) {
//            views.add(from.inflate(resIDs[i], null));
//        }

        //初始化指示器的小点
        for (int i = 0; i < resIDs.length; i++) {
            View view_dot=new View(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(15,15);
            params.rightMargin=15;
            view_dot.setLayoutParams(params);
            view_dot.setBackgroundDrawable(getResources().getDrawable(R.drawable.dot_selector));
            //默认选中第一项
            if (i==0){
                view_dot.setSelected(true);
            }
            dot_views.add(view_dot);
            linearLayout.addView(view_dot);
        }
        //以下两个判断是为了解决addView时对其parent检测的异常（其实还有一个简单地做法是每一次addView时都去inflate一个page视图）
//        if (views.size()==2){
//            views.add(from.inflate(resIDs[0],null));
//            views.add(from.inflate(resIDs[1],null));
//        }
//        if (views.size()==3){
//            views.add(from.inflate(resIDs[0],null));
//            views.add(from.inflate(resIDs[1],null));
//            views.add(from.inflate(resIDs[2],null));
//        }
        //为ViewPager添加滑动改变监听
        viewPager.addOnPageChangeListener(this);

        MyViewpagerAdapter myViewpagerAdapter = new MyViewpagerAdapter(resIDs);
        viewPager.setAdapter(myViewpagerAdapter);
        timer=new Timer();
        //由于默认显示第一页，所以要在第一页停留2s
        timer.schedule(timerTask,2000,2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //当Activity处在stop应该停止Timer，不然Timer会一直运行
        if (timer!=null){
            timer.cancel();
            timer=null;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e("onPageSelected",position+"");
        //切换指示器的小点
//        int realPos=position%dot_views.size();
//        for (int i = 0; i < dot_views.size(); i++) {
//            if (realPos==i){
//                dot_views.get(i).setSelected(true);
//            }else {
//                dot_views.get(i).setSelected(false);
//            }
//        }
        int realPos=position%resIDs.length;
        for (int i = 0; i < resIDs.length; i++) {
            if (realPos==i){
                dot_views.get(i).setSelected(true);
            }else {
                dot_views.get(i).setSelected(false);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //handler建议写法，防止内存泄漏
   static class VPHandler extends Handler{
        private WeakReference<MainActivity> mActivity;
        public VPHandler(MainActivity activity)
        {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg)
        {
            MainActivity activity = mActivity.get();
            super.handleMessage(msg);

            switch (msg.what)
            {
                case 0:
                    //将ViewPager滑动一页
                    activity.viewPager.setCurrentItem(activity.viewPager.getCurrentItem()+1);
                    break;
            }
        }

    }
}
