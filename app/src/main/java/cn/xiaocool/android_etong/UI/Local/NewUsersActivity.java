package cn.xiaocool.android_etong.UI.Local;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.fragment.Local.NewUsers_All_Fragment;
import cn.xiaocool.android_etong.fragment.Local.NewUsers_Distence_Fragment;

/**
 * Created by 潘 on 2016/8/10.
 */
public class NewUsersActivity extends Activity implements View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private RelativeLayout rl_back;
    private SliderLayout mDemoSlider;
    private NewUsers_All_Fragment newUser_all_fragment;
    private NewUsers_Distence_Fragment newUser_distence_Fragment;
    private Button[] mTabs;
    private Fragment[] fragments;
    private int index, currentIndex;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_users);
        context = this;
        initView();
        initData();
        initFragment();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
    }

    private void initData() {
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

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

    private void initFragment() {
        newUser_all_fragment = new NewUsers_All_Fragment();
        newUser_distence_Fragment = new NewUsers_Distence_Fragment();
        fragments = new Fragment[]{newUser_all_fragment,newUser_distence_Fragment};
        getFragmentManager().beginTransaction().add(R.id.fragment_container,newUser_all_fragment).show(newUser_all_fragment).commit();
        initBtn();
    }

    private void initBtn() {
        mTabs = new Button[2];
        mTabs[0] = (Button) findViewById(R.id.btn_all);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (Button) findViewById(R.id.btn_nearly);
        mTabs[1].setOnClickListener(this);
        mTabs[0].setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_all:
                index = 0;
                break;
            case R.id.btn_nearly:
                index = 1;
                break;
        }
        if (currentIndex != index) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()) {
                fragmentTransaction.add(R.id.fragment_container, fragments[index]);
            }
            fragmentTransaction.show(fragments[index]);
            fragmentTransaction.commit();
        }
        mTabs[currentIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentIndex = index;
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

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
