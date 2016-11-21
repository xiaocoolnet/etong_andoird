package cn.xiaocool.android_etong.UI.Main;

import android.app.Activity;
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
    private TextView tvTitle,tv_progress_shengyu;
    private TextView tv1,tv2,tv3,tv4,tv5;
    private RelativeLayout rlBack;
    private SliderLayout mDemoSlider;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList;
    private Context context;
    private String type;
    private ProgressBar progressBar;
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
        initView();
        initdata();
        judgeTime();
        if (NetUtil.isConnnected(context)){
//            new HomeRequest(this, handler).getNewArrival("&recommend=2");
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
                if (NetUtil.isConnnected(context)){
//            new HomeRequest(this, handler).getNewArrival("&recommend=2");
                    new MainRequest(context,handler).GetTimeGoodList("1");
                }else {
                    Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv2:
                if (NetUtil.isConnnected(context)){
//            new HomeRequest(this, handler).getNewArrival("&recommend=2");
                    new MainRequest(context,handler).GetTimeGoodList("2");
                }else {
                    Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv3:
                if (NetUtil.isConnnected(context)){
//            new HomeRequest(this, handler).getNewArrival("&recommend=2");
                    new MainRequest(context,handler).GetTimeGoodList("3");
                }else {
                    Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv4:
                if (NetUtil.isConnnected(context)){
//            new HomeRequest(this, handler).getNewArrival("&recommend=2");
                    new MainRequest(context,handler).GetTimeGoodList("4");
                }else {
                    Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv5:
                if (NetUtil.isConnnected(context)){
//            new HomeRequest(this, handler).getNewArrival("&recommend=2");
                    new MainRequest(context,handler).GetTimeGoodList("5");
                }else {
                    Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                }
                break;
        }
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
        final int start = 6* 60 ;// 起始时间 00:00的分钟数
        final int end = 8 * 60;// 结束时间 8:00的分钟数
        final int end2 = 10*60;
        final int end3 = 12*60;
        final int end4 = 14*60;
        if (minuteOfDay >= start && minuteOfDay <= end) {
            type = "1";
            mTabs[0].setSelected(true);
        }else if (minuteOfDay >= end && minuteOfDay <= end2){
            type = "2";
            mTabs[1].setSelected(true);
        }else if (minuteOfDay >= end2 && minuteOfDay <= end3){
            type = "3";
            mTabs[2].setSelected(true);
        }else if (minuteOfDay >= end3 && minuteOfDay <= end4){
            type = "4";
            mTabs[3].setSelected(true);
        }else if (minuteOfDay >= end4){
            type = "5";
            mTabs[4].setSelected(true);
        }
        Log.e("type=",type);
        return;
    }

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
