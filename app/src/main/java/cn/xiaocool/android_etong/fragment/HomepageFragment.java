package cn.xiaocool.android_etong.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.HomePage.SearchActivity;
import cn.xiaocool.android_etong.UI.Main.EverydayBargainActivity;
import cn.xiaocool.android_etong.UI.Main.EverydayChoicenessActivity;
import cn.xiaocool.android_etong.UI.Main.FlashSaleActivity;
import cn.xiaocool.android_etong.UI.Main.NewArrivalActivity;
import cn.xiaocool.android_etong.UI.Main.QualityLifeActivity;
import cn.xiaocool.android_etong.adapter.EverydayGoodShopAdapter;
import cn.xiaocool.android_etong.adapter.HomepageGuessLikeAdapter;
import cn.xiaocool.android_etong.bean.HomePage.EveryDayGoodShopBean;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.HomeRequest;
import cn.xiaocool.android_etong.util.IntentUtils;
import cn.xiaocool.android_etong.util.NoScrollGridView;

/**
 * Created by 潘 on 2016/6/12.
 */
public class HomepageFragment extends Fragment implements View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private Context context;
    private SliderLayout mDemoSlider;
    private TextView et_search;
    private RelativeLayout rl_meirijingxuan;
    private RelativeLayout rl_bestshop_left, rl_bestshop_right, rlNewArrival, rlEverydayBargain, rlEverydayChoiceness;
    private LinearLayout llQualityLife, llFlashSale;
    private NoScrollGridView gridView0,gridView1;
    private List<EveryDayGoodShopBean.DataBean> dataBeenList;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList;
    private EverydayGoodShopAdapter everydayGoodShopAdapter;
    private HomepageGuessLikeAdapter homepageGuessLikeAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GET_HOMEPAGE_EVERY_GOODSHOP:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                EveryDayGoodShopBean.DataBean dataBean = new EveryDayGoodShopBean.DataBean();
                                dataBean.setId(dataObject.getString("id"));
                                dataBean.setShopname(dataObject.getString("shopname"));
                                dataBean.setFavorite(dataObject.getString("favorite"));
                                dataBean.setPhoto(dataObject.getString("photo"));
                                dataBeenList.add(dataBean);
                            }
                            everydayGoodShopAdapter = new EverydayGoodShopAdapter(context, dataBeenList);
                            gridView0.setAdapter(everydayGoodShopAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.GET_NEW_ARRIVAL:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                NewArrivalBean.NewArrivalDataBean newArrivalDataBean = new NewArrivalBean.NewArrivalDataBean();
                                newArrivalDataBean.setId(dataObject.getString("id"));
                                newArrivalDataBean.setDescription(dataObject.getString("description"));
                                newArrivalDataBean.setPrice(dataObject.getString("price"));
                                newArrivalDataBean.setPicture(dataObject.getString("picture"));
                                newArrivalDataBean.setPayNum(dataObject.getString("paynum"));
                                newArrivalDataBeanList.add(newArrivalDataBean);
                            }
                            homepageGuessLikeAdapter = new HomepageGuessLikeAdapter(context, newArrivalDataBeanList);
                            gridView1.setAdapter(homepageGuessLikeAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        context = getActivity();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
        initdata();
        new HomeRequest(context, handler).getGuessLike("烟台市");//获取每日好店
        new HomeRequest(context, handler).getNewArrival("&recommend=6");//获取猜你喜欢
    }

    private void initview() {
        et_search = (TextView) getView().findViewById(R.id.et_search);
        et_search.clearFocus();
        et_search.setOnClickListener(this);
        rlNewArrival = (RelativeLayout) getView().findViewById(R.id.homepage_rl_new_arrival);
        rlNewArrival.setOnClickListener(this);
        llQualityLife = (LinearLayout) getView().findViewById(R.id.homepage_ll_quality_life);
        llQualityLife.setOnClickListener(this);
        llFlashSale = (LinearLayout) getView().findViewById(R.id.homepage_ll_flash_sale);
        llFlashSale.setOnClickListener(this);
        rlEverydayBargain = (RelativeLayout) getView().findViewById(R.id.homepage_rl_everyday_bargain_price);
        rlEverydayBargain.setOnClickListener(this);
        rlEverydayChoiceness = (RelativeLayout) getView().findViewById(R.id.homepage_rl_everyday_choiceness);
        rlEverydayChoiceness.setOnClickListener(this);
        gridView0 = (NoScrollGridView) getView().findViewById(R.id.homepage_everyday_goodshop_gridview);
        gridView1 = (NoScrollGridView) getView().findViewById(R.id.homepage_guess_like_gridview);
        dataBeenList = new ArrayList<>();
        newArrivalDataBeanList = new ArrayList<>();
    }

    private void initdata() {
        mDemoSlider = (SliderLayout) getView().findViewById(R.id.slider);

        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://hq.xiaocool.net/uploads/microblog/sp1.jpg");
        url_maps.put("Big Bang Theory", "http://hq.xiaocool.net/uploads/microblog/sp2.jpg");
        url_maps.put("House of Cards", "http://hq.xiaocool.net/uploads/microblog/sp3.jpg");
        url_maps.put("Game of Thrones", "http://hq.xiaocool.net/uploads/microblog/sp4.jpg");

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

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homepage_rl_new_arrival:
                IntentUtils.getIntent((Activity) context, NewArrivalActivity.class);
                break;
            case R.id.homepage_ll_quality_life:
                IntentUtils.getIntent((Activity) context, QualityLifeActivity.class);
                break;
            case R.id.homepage_ll_flash_sale:
                IntentUtils.getIntent((Activity) context, FlashSaleActivity.class);
                break;
            case R.id.homepage_rl_everyday_bargain_price:
                IntentUtils.getIntent((Activity) context, EverydayBargainActivity.class);
                break;
            case R.id.homepage_rl_everyday_choiceness:
                IntentUtils.getIntent((Activity) context, EverydayChoicenessActivity.class);
                break;
            case R.id.et_search:
                Intent intent = new Intent();
                intent.setClass(context, SearchActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
//        Toast.makeText(context, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
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

}
