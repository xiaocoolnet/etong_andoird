package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by 潘 on 2016/7/20.
 */
public class GoodsDetailActivity extends Activity implements View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private Context context;
    private RelativeLayout rl_back;
    private SliderLayout mDemoSlider;
    private ImageView img_goods_pic;
    private Button btn_lijigoumai;
    private String id, pic, goodsname;
    private String[] arraypic;
    private ImageView iv_likeBtn;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.LIKE_GOOD:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            ToastUtils.makeShortToast(GoodsDetailActivity.this, "收藏成功！");
                            iv_likeBtn.setSelected(true);
                        } else {
                            ToastUtils.makeShortToast(GoodsDetailActivity.this, "收藏失败！");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.CANCLE_LIKE_GOOD:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")) {
                            ToastUtils.makeShortToast(GoodsDetailActivity.this, "取消收藏成功！");
                            iv_likeBtn.setSelected(false);
                        } else {
                            ToastUtils.makeShortToast(GoodsDetailActivity.this, "取消收藏失败！");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goodsdetails);
        context = this;
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        pic = intent.getStringExtra("pic");
        goodsname = intent.getStringExtra("goodsname");
        arraypic = pic.split("[,]");
        for (String pic_name : arraypic) {
            Log.e("pic_name=", pic_name);
        }
        Log.e("id=", id);
        initview();
        initpic();
    }

    private void initview() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        img_goods_pic = (ImageView) findViewById(R.id.img_goods_pic);
        btn_lijigoumai = (Button) findViewById(R.id.btn_lijigoumai);
        btn_lijigoumai.setOnClickListener(this);
        iv_likeBtn = (ImageView) findViewById(R.id.good_details_iv_like);
        iv_likeBtn.setOnClickListener(this);
        iv_likeBtn.setSelected(false);
    }

    private void initpic() {
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        HashMap<String, String> url_maps = new HashMap<String, String>();
//        url_maps.put("Hannibal", "http://hq.xiaocool.net/uploads/microblog/sp1.jpg");
//        url_maps.put("Big Bang Theory", "http://hq.xiaocool.net/uploads/microblog/sp2.jpg");
//        url_maps.put("House of Cards", "http://hq.xiaocool.net/uploads/microblog/sp3.jpg");
//        url_maps.put("Game of Thrones", "http://hq.xiaocool.net/uploads/microblog/sp4.jpg");
        for (int i = 0; i < arraypic.length; i++) {
            int count = arraypic.length;
            url_maps.put(goodsname + "  图" + i, WebAddress.GETAVATAR + arraypic[i]);
        }
        if (arraypic.length == 1) {
            img_goods_pic.setVisibility(View.VISIBLE);
            mDemoSlider.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + arraypic[0], img_goods_pic);
        } else {
            mDemoSlider.setVisibility(View.VISIBLE);
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
    }


    //对应轮播图片部分
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_lijigoumai:
                Intent intent = new Intent();
                intent.setClass(context, BuyNowActivity.class);
                startActivity(intent);
                break;
            case R.id.good_details_iv_like:
                if (NetUtil.isConnnected(this)) {
                    if (!iv_likeBtn.isSelected()) {
                        new ShopRequest(this, handler).likeGood(id);
                    }else{
                        new ShopRequest(this,handler).cancelLike(id);
                    }
                }
        }
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    //轮播图继承的Demo接口
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
