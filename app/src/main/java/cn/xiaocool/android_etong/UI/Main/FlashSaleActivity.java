package cn.xiaocool.android_etong.UI.Main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.FlashSaleAdapter;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/7/24.
 */
public class FlashSaleActivity extends Activity implements View.OnClickListener, ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {
    private ListView listView;
    private TextView mTabs[];
    private TextView tvTitle,tv_progress_shengyu,tv_time,tv_time1,tv_time2,tv_time3;
    private TextView tv1,tv2,tv3,tv4,tv5;
    private int index, currentIndex;
    private RelativeLayout rlBack;
    private SliderLayout mDemoSlider;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList;
    private Context context;
    private long time;
    private String type;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private FlashSaleAdapter flashSaleAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case CommunalInterfaces.GET_NEW_ARRIVAL:
                case CommunalInterfaces.GetTimeGoodList:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            Log.e("seccess","intoGetTimeGoodList");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            for (int i = 0; i < 5; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                NewArrivalBean.NewArrivalDataBean newArrivalDataBean = new NewArrivalBean.NewArrivalDataBean();
                                newArrivalDataBean.setId(dataObject.getString("id"));
                                newArrivalDataBean.setArtno(dataObject.getString("artno"));
                                newArrivalDataBean.setShopid(dataObject.getString("shopid"));
                                newArrivalDataBean.setBrand(dataObject.getString("brand"));
                                newArrivalDataBean.setGoodsname(dataObject.getString("goodsname"));
                                newArrivalDataBean.setAdtitle(dataObject.getString("adtitle"));
                                newArrivalDataBean.setOprice(dataObject.getString("oprice"));
                                newArrivalDataBean.setPrice(dataObject.getString("price"));
                                newArrivalDataBean.setUnit(dataObject.getString("unit"));
                                newArrivalDataBean.setDescription(dataObject.getString("description"));
                                newArrivalDataBean.setPicture(dataObject.getString("picture"));
                                newArrivalDataBean.setShowid(dataObject.getString("showid"));
                                newArrivalDataBean.setAddress(dataObject.getString("address"));
                                newArrivalDataBean.setFreight(dataObject.getString("freight"));
                                newArrivalDataBean.setPays(dataObject.getString("pays"));
                                newArrivalDataBean.setRacking(dataObject.getString("racking"));
                                newArrivalDataBean.setRecommend(dataObject.getString("recommend"));

                                JSONObject jsonObject1 = dataObject.getJSONObject("shop_name");
                                String shopName = jsonObject1.getString("shopname");
                                if (!shopName.equals(null)) {
                                    newArrivalDataBean.setShopname(shopName);
                                } else {
                                    newArrivalDataBean.setShopname("null");
                                }
                                newArrivalDataBean.setSales(dataObject.getString("sales"));
                                newArrivalDataBean.setPayNum(dataObject.getString("paynum"));
                                newArrivalDataBeanList.add(newArrivalDataBean);
                            }
                            flashSaleAdapter = new FlashSaleAdapter(context, newArrivalDataBeanList);
                            listView.setAdapter(flashSaleAdapter);
                            progressDialog.dismiss();
                       }else {
                            progressDialog.dismiss();
                            Toast.makeText(context,jsonObject.getString("data"),Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.flash_sale);
        context = this;
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        initView();
        initdata();
        judgeTime();
        if (NetUtil.isConnnected(context)){
            progressDialog.setMessage("正在加载");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            new MainRequest(context,handler).GetTimeGoodList(type);
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.gridView_flash_sale);
        newArrivalDataBeanList = new ArrayList<>();
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("限时抢购");
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        tv_time3 = (TextView) findViewById(R.id.tv_time3);
        mTabs = new TextView[5];
        mTabs[0] = (TextView) findViewById(R.id.tv1);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (TextView) findViewById(R.id.tv2);
        mTabs[1].setOnClickListener(this);
        mTabs[2] = (TextView) findViewById(R.id.tv3);
        mTabs[2].setOnClickListener(this);
        mTabs[3] = (TextView) findViewById(R.id.tv4);
        mTabs[3].setOnClickListener(this);
        mTabs[4] = (TextView) findViewById(R.id.tv5);
        mTabs[4].setOnClickListener(this);
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tv_progress_shengyu = (TextView) findViewById(R.id.tv_progress_shengyu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv1:
                index = 0;
                if (NetUtil.isConnnected(context)){
                    progressDialog.setMessage("正在加载");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    new MainRequest(context,handler).GetTimeGoodList("1");
                }else {
                    Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv2:
                index = 1;
                if (NetUtil.isConnnected(context)){
                    progressDialog.setMessage("正在加载");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    new MainRequest(context,handler).GetTimeGoodList("2");
                }else {
                    Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv3:
                index = 2;
                if (NetUtil.isConnnected(context)){
                    progressDialog.setMessage("正在加载");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    new MainRequest(context,handler).GetTimeGoodList("3");
                }else {
                    Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv4:
                index = 3;
                if (NetUtil.isConnnected(context)){
                    progressDialog.setMessage("正在加载");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    new MainRequest(context,handler).GetTimeGoodList("4");
                }else {
                    Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv5:
                index = 4;
                if (NetUtil.isConnnected(context)){
                    progressDialog.setMessage("正在加载");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    new MainRequest(context,handler).GetTimeGoodList("5");
                }else {
                    Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        mTabs[currentIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentIndex = index;
    }

    private void initdata() {
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("新品上市", "http://hq.xiaocool.net/uploads/microblog/sp1.jpg");
        url_maps.put("推荐购买", "http://hq.xiaocool.net/uploads/microblog/sp2.jpg");
        url_maps.put("猜你喜欢", "http://hq.xiaocool.net/uploads/microblog/sp3.jpg");
        url_maps.put("每日特价", "http://hq.xiaocool.net/uploads/microblog/sp4.jpg");

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(context);
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
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
            type = "1";
            currentIndex = 0;
            mTabs[0].setText("6:00\n正在进行");
            mTabs[1].setText("8:00\n即将开始");
            mTabs[2].setText("10:00\n即将开始");
            mTabs[3].setText("12:00\n即将开始");
            mTabs[4].setText("14:00\n即将开始");
            time = end*60 - minuteOfDay*60;
            handler1.postDelayed(runnable, 1000);
        }else if (minuteOfDay >= end && minuteOfDay <= end2){
            type = "2";
            currentIndex = 1;
            mTabs[0].setText("6:00\n已结束");
            mTabs[1].setText("8:00\n正在进行");
            mTabs[2].setText("10:00\n即将开始");
            mTabs[3].setText("12:00\n即将开始");
            mTabs[4].setText("14:00\n即将开始");
            time = end2*60 - minuteOfDay*60;
            handler1.postDelayed(runnable, 1000);
        }else if (minuteOfDay >= end2 && minuteOfDay <= end3){
            type = "3";
            currentIndex = 2;
            mTabs[0].setText("6:00\n已结束");
            mTabs[1].setText("8:00\n已结束");
            mTabs[2].setText("10:00\n正在进行");
            mTabs[3].setText("12:00\n即将开始");
            mTabs[4].setText("14:00\n即将开始");
            time = end3*60 - minuteOfDay*60;
            handler1.postDelayed(runnable, 1000);
        }else if (minuteOfDay >= end3 && minuteOfDay <= end4){
            type = "4";
            currentIndex = 3;
            mTabs[0].setText("6:00\n已结束");
            mTabs[1].setText("8:00\n已结束");
            mTabs[2].setText("10:00\n已结束");
            mTabs[3].setText("12:00\n正在进行");
            mTabs[4].setText("14:00\n即将开始");
            time = end4*60 - minuteOfDay*60;
            handler1.postDelayed(runnable, 1000);
        }else if (minuteOfDay >= end4&& minuteOfDay <= end5){
            type = "5";
            currentIndex = 4;
            mTabs[0].setText("6:00\n已结束");
            mTabs[1].setText("8:00\n已结束");
            mTabs[2].setText("10:00\n已结束");
            mTabs[3].setText("12:00\n已结束");
            mTabs[4].setText("14:00\n正在进行");
            time = end5*60  - minuteOfDay*60;
            handler1.postDelayed(runnable, 1000);
        }else if (minuteOfDay >= end5){
            type = "5";
            currentIndex = 4;
            mTabs[0].setText("6:00\n已结束");
            mTabs[1].setText("8:00\n已结束");
            mTabs[2].setText("10:00\n已结束");
            mTabs[3].setText("12:00\n已结束");
            mTabs[4].setText("14:00\n已结束");
            tv_time2.setText("已结束");
        }
        Log.e("type=",type);
        mTabs[currentIndex].setSelected(true);
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
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onPause() {
        if (mDemoSlider != null) {
            mDemoSlider.stopAutoCycle();
        }
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
