package cn.xiaocool.android_etong.UI.Main;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.lzy.widget.tab.PagerSlidingTabStrip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.FlashSaleAdapter;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.fragment.FlashSale.FlashSaleFiveFragment;
import cn.xiaocool.android_etong.fragment.FlashSale.FlashSaleFourFragment;
import cn.xiaocool.android_etong.fragment.FlashSale.FlashSaleOneFragment;
import cn.xiaocool.android_etong.fragment.FlashSale.FlashSaleThreeFragment;
import cn.xiaocool.android_etong.fragment.FlashSale.FlashSaleTwoFragment;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/7/24.
 */
public class FlashSaleActivity extends FragmentActivity implements View.OnClickListener, ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {

    private String[] titles = {"6:00", "8:00","10:00","12:00","14:00"};
    private TextView tvTitle,tv_time1,tv_time2,tv_time3;
    private TextView tv1,tv2,tv3,tv4,tv5;
    private RelativeLayout rlBack;
    private SliderLayout mDemoSlider;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList;
    private Context context;
    private long time;
    private ArrayList<Fragment> fragments;
    private String type;
    private ProgressBar progressBar;
    private PagerSlidingTabStrip activityAddressTab;
    private ViewPager activityAddressViewPager;
    private ProgressDialog progressDialog;
    public int pushtype = 0;
    private FlashSaleAdapter flashSaleAdapter;
    FlashSaleOneFragment flashSaleOneFragment = new FlashSaleOneFragment();
    FlashSaleTwoFragment flashSaleTwoFragment = new FlashSaleTwoFragment();
    FlashSaleThreeFragment flashSaleThreeFragment = new FlashSaleThreeFragment();
    FlashSaleFourFragment flashSaleFourFragment = new FlashSaleFourFragment();
    FlashSaleFiveFragment flashSaleFiveFragment = new FlashSaleFiveFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.flash_sale);
        context = this;
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        initView();
        setFragment();
        judgeTime();
    }

    /**
     * 设置fragment
     */
    private void setFragment() {
        fragments = new ArrayList<>();
        fragments.add(flashSaleOneFragment);
        fragments.add(flashSaleTwoFragment);
        fragments.add(flashSaleThreeFragment);
        fragments.add(flashSaleFourFragment);
        fragments.add(flashSaleFiveFragment);
        activityAddressViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        activityAddressTab.setViewPager(activityAddressViewPager);
        Log.e("title=",titles[0]);
    }

    /**
     * viewpager适配器
     */
    private class MyAdapter extends FragmentPagerAdapter {



        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    private void initView() {
        newArrivalDataBeanList = new ArrayList<>();
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("限时抢购");
        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        tv_time3 = (TextView) findViewById(R.id.tv_time3);

        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        activityAddressTab = (PagerSlidingTabStrip) findViewById(R.id.activity_address_tab);
        activityAddressViewPager = (ViewPager) findViewById(R.id.activity_address_viewPager);
        activityAddressViewPager.setOffscreenPageLimit(4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }



    private void judgeTime() {
        Calendar cal = Calendar.getInstance();// 当前日期
        int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
        int minute = cal.get(Calendar.MINUTE);// 获取分钟
        int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数
        final long start = 6* 60 ;// 起始时间 00:00的分钟数
        final long end = 8 * 60;// 结束时间 8:00的分钟数
        final long end2 = 10*60;
        final long end3 = 12*60;
        final long end4 = 14*60;
        final long end5 = 16*60;
        if (minuteOfDay >= start && minuteOfDay <= end) {
            activityAddressViewPager.setCurrentItem(0);
            type = "1";
            titles[0]=("6:00\n正在进行");
            titles[1]=("8:00\n即将开始");
            titles[2]=("10:00\n即将开始");
            titles[3]=("12:00\n即将开始");
            titles[4]=("14:00\n即将开始");
            time = end*60 - minuteOfDay*60;
            handler1.postDelayed(runnable, 1000);
        }else if (minuteOfDay >= end && minuteOfDay <= end2){
            activityAddressViewPager.setCurrentItem(1);
            type = "2";
            titles[0]=("6:00\n已结束");
            titles[1]=("8:00\n正在进行");
            titles[2]=("10:00\n即将开始");
            titles[3]=("12:00\n即将开始");
            titles[4]=("14:00\n即将开始");
            time = end2*60 - minuteOfDay*60;
            handler1.postDelayed(runnable, 1000);
        }else if (minuteOfDay >= end2 && minuteOfDay <= end3){
            activityAddressViewPager.setCurrentItem(2);
            type = "3";
            titles[0]=("6:00\n已结束");
            titles[1]=("8:00\n已结束");
            titles[2]=("10:00\n正在进行");
            titles[3]=("12:00\n即将开始");
            titles[4]=("14:00\n即将开始");
            time = end3*60 - minuteOfDay*60;
            handler1.postDelayed(runnable, 1000);
        }else if (minuteOfDay >= end3 && minuteOfDay <= end4){
            activityAddressViewPager.setCurrentItem(3);
            type = "4";
            titles[0]=("6:00\n已结束");
            titles[1]=("8:00\n已结束");
            titles[2]=("10:00\n已结束");
            titles[3]=("12:00\n正在进行");
            titles[4]=("14:00\n即将开始");
            time = end4*60 - minuteOfDay*60;
            handler1.postDelayed(runnable, 1000);
        }else if (minuteOfDay >= end4&& minuteOfDay <= end5){
            activityAddressViewPager.setCurrentItem(4);
            type = "5";
            titles[0]=("6:00\n已结束");
            titles[1]=("8:00\n已结束");
            titles[2]=("10:00\n已结束");
            titles[3]=("12:00\n已结束");
            titles[4]=("14:00\n正在进行");
            time = end5*60  - minuteOfDay*60;
            handler1.postDelayed(runnable, 1000);
        }else if (minuteOfDay >= end5){
            activityAddressViewPager.setCurrentItem(4);
            type = "5";
            titles[0]=("6:00\n已结束");
            titles[1]=("8:00\n已结束");
            titles[2]=("10:00\n已结束");
            titles[3]=("12:00\n已结束");
            titles[4]=("14:00\n已结束");
            tv_time2.setText("已结束");
        }
        Log.e("type=",type);

        return;
    }

    public  String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue() ;
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = hour+"："+minute+"："+second;
        return strtime;

    }

    Handler handler1 = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time--;
            String formatLongToTimeStr = formatLongToTimeStr(time);
            String[] split = formatLongToTimeStr.split("：");
            for (int i = 0; i < split.length; i++) {
                if(i==0){
                    tv_time1.setText(split[0]+"小时");
                }
                if(i==1){
                    tv_time2.setText(split[1]+"分钟");
                }
                if(i==2){
                    tv_time3.setText(split[2]+"秒");
                }

            }
            if(time>0){
                handler1.postDelayed(this, 1000);
            }
        }
    };

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
//        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        activityAddressViewPager.setCurrentItem(pushtype);
        pushtype = 0;
    }

    @Override
    public void onPause() {
//        if (mDemoSlider != null) {
//            mDemoSlider.stopAutoCycle();
//        }
        super.onPause();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
