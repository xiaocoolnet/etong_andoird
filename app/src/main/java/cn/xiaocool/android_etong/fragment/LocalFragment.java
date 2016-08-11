package cn.xiaocool.android_etong.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.HomePage.ShopListActivity;
import cn.xiaocool.android_etong.UI.Local.DailySpecialActivity;
import cn.xiaocool.android_etong.UI.Local.EntertainmentActivity;
import cn.xiaocool.android_etong.UI.Local.FoodActivity;
import cn.xiaocool.android_etong.UI.Local.HotelActivity;
import cn.xiaocool.android_etong.UI.Local.KtvActivity;
import cn.xiaocool.android_etong.UI.Local.MoiveActivity;
import cn.xiaocool.android_etong.UI.Local.NewUsersActivity;
import cn.xiaocool.android_etong.UI.Local.PanicBuyingActivity;
import cn.xiaocool.android_etong.UI.Local.RechargeActivity;
import cn.xiaocool.android_etong.UI.Local.ServiceActivity;
import cn.xiaocool.android_etong.UI.Local.TakeOutFoodAcitvity;
import cn.xiaocool.android_etong.UI.Local.TravelAroundActivity;

import static cn.xiaocool.android_etong.util.StatusBarHeightUtils.getStatusBarHeight;

/**
 * Created by 潘 on 2016/6/12.
 */
public class LocalFragment extends Fragment implements View.OnClickListener , BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private Context context;
    private SliderLayout mDemoSlider;
    private RelativeLayout ry_line;
    private LinearLayout ll_eqianggou,ll_xinkezhuanxiang,ll_jinritejia;
    private Button btn_quanbu,btn_meishi,btn_dianying,btn_jiudian,btn_waimai,btn_shenghuoyule,
            btn_zhoubianyou,btn_shenghuofuwu,btn_ktv,btn_shoujichongzhi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local,container,false);
        context = getActivity();
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置状态栏高度
        ry_line = (RelativeLayout)getView().findViewById(R.id.lin);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ry_line.getLayoutParams();
        linearParams.height=getStatusBarHeight(context);
        ry_line.setLayoutParams(linearParams);
        initview();
    }

    private void initview() {
        btn_quanbu = (Button)getView().findViewById(R.id.btn_quanbu);
        btn_quanbu.setOnClickListener(this);
        btn_meishi = (Button)getView().findViewById(R.id.btn_meishi);
        btn_meishi.setOnClickListener(this);
        btn_dianying = (Button)getView().findViewById(R.id.btn_dianying);
        btn_dianying.setOnClickListener(this);
        btn_jiudian = (Button)getView().findViewById(R.id.btn_jiudian);
        btn_jiudian.setOnClickListener(this);
        btn_waimai = (Button)getView().findViewById(R.id.btn_waimai);
        btn_waimai.setOnClickListener(this);
        btn_shenghuoyule = (Button)getView().findViewById(R.id.btn_shenghuoyule);
        btn_shenghuoyule.setOnClickListener(this);
        btn_zhoubianyou = (Button)getView().findViewById(R.id.btn_zhoubianyou);
        btn_zhoubianyou.setOnClickListener(this);
        btn_shenghuofuwu = (Button)getView().findViewById(R.id.btn_shenghuofuwu);
        btn_shenghuofuwu.setOnClickListener(this);
        btn_ktv = (Button)getView().findViewById(R.id.btn_ktv);
        btn_ktv.setOnClickListener(this);
        btn_shoujichongzhi = (Button)getView().findViewById(R.id.btn_shoujichongzhi);
        btn_shoujichongzhi.setOnClickListener(this);
        ll_eqianggou = (LinearLayout)getView().findViewById(R.id.ll_eqianggou);
        ll_eqianggou.setOnClickListener(this);
        ll_xinkezhuanxiang = (LinearLayout)getView().findViewById(R.id.ll_xinkezhuanxiang);
        ll_xinkezhuanxiang.setOnClickListener(this);
        ll_jinritejia = (LinearLayout)getView().findViewById(R.id.ll_jinritejia);
        ll_jinritejia.setOnClickListener(this);

        mDemoSlider = (SliderLayout) getView().findViewById(R.id.slider);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://hq.xiaocool.net/uploads/microblog/sp1.jpg");
        url_maps.put("Big Bang Theory", "http://hq.xiaocool.net/uploads/microblog/sp2.jpg");
        url_maps.put("House of Cards", "http://hq.xiaocool.net/uploads/microblog/sp3.jpg");
        url_maps.put("Game of Thrones", "http://hq.xiaocool.net/uploads/microblog/sp4.jpg");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(context);
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

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
        switch (v.getId()){
            case R.id.btn_quanbu:
                Intent intent = new Intent();
                intent.setClass(context, ShopListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_meishi:
                Intent intent1 = new Intent();
                intent1.setClass(context, FoodActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_dianying:
                Intent intent2 = new Intent();
                intent2.setClass(context, MoiveActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_jiudian:
                Intent intent3 = new Intent();
                intent3.setClass(context, HotelActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_waimai:
                Intent intent4 = new Intent();
                intent4.setClass(context, TakeOutFoodAcitvity.class);
                startActivity(intent4);
                break;
            case R.id.btn_shenghuoyule:
                Intent intent5 = new Intent();
                intent5.setClass(context, EntertainmentActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_zhoubianyou:
                Intent intent6 = new Intent();
                intent6.setClass(context, TravelAroundActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn_shenghuofuwu:
                Intent intent7 = new Intent();
                intent7.setClass(context, ServiceActivity.class);
                startActivity(intent7);
                break;
            case R.id.btn_ktv:
                Intent intent8 = new Intent();
                intent8.setClass(context, KtvActivity.class);
                startActivity(intent8);
                break;
            case R.id.btn_shoujichongzhi:
                Intent intent9 = new Intent();
                intent9.setClass(context, RechargeActivity.class);
                startActivity(intent9);
                break;
            case R.id.ll_eqianggou:
                Intent intent10 = new Intent();
                intent10.setClass(context, PanicBuyingActivity.class);
                startActivity(intent10);
                break;
            case R.id.ll_xinkezhuanxiang:
                Intent intent11 = new Intent();
                intent11.setClass(context, NewUsersActivity.class);
                startActivity(intent11);
                break;
            case R.id.ll_jinritejia:
                Intent intent12 = new Intent();
                intent12.setClass(context, DailySpecialActivity.class);
                startActivity(intent12);
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
