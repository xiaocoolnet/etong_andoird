package cn.xiaocool.android_etong.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.StoreHomepageActivity;

/**
 * Created by 潘 on 2016/6/12.
 */
public class HomepageFragment extends Fragment implements View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private Context context;
    private SliderLayout mDemoSlider;
    private RelativeLayout rl_meirijingxuan;
    private RelativeLayout rl_bestshop_left , rl_bestshop_right;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage,container,false);
        context = getActivity();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
        initdata();
    }

    private void initview() {
        rl_meirijingxuan = (RelativeLayout) getView().findViewById(R.id.rl_meirijingxuan);
        rl_meirijingxuan.setOnClickListener(this);
        rl_bestshop_left = (RelativeLayout)getView().findViewById(R.id.rl_bestshop_left);
        rl_bestshop_left.setOnClickListener(this);
        rl_bestshop_right = (RelativeLayout)getView().findViewById(R.id.rl_bestshop_right);
        rl_bestshop_right.setOnClickListener(this);
    }

    private void initdata() {
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
//            case R.id.rl_meirijingxuan:
//                Intent intent7 = new Intent();
//                intent7.putExtra("shopid", "2");
//                intent7.setClass(context, StoreHomepageActivity.class);
//                startActivity(intent7);
//                break;
            case R.id.rl_bestshop_left:
                Intent intent8 = new Intent();
                intent8.putExtra("shopid", "2");
                intent8.setClass(context, StoreHomepageActivity.class);
                startActivity(intent8);
                break;
            case R.id.rl_bestshop_right:
                Intent intent9 = new Intent();
                intent9.putExtra("shopid", "2");
                intent9.setClass(context, StoreHomepageActivity.class);
                startActivity(intent9);
                break;
        }

    }
    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(context, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
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
