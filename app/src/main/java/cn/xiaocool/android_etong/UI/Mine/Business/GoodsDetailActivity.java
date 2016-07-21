package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/7/20.
 */
public class GoodsDetailActivity extends Activity implements View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private Context context;
    private RelativeLayout rl_back;
    private SliderLayout mDemoSlider;
    private TextView tx_goods_name,tx_goods_price;
    private ImageView img_goods_pic;
    private Button btn_lijigoumai;
    private String id , pic , goodsname , price;
    private String[] arraypic;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.GET_GOODS_INFO:
                    JSONObject json = (JSONObject) msg.obj;
                    try {
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")){

                        }else {
                            Toast.makeText(context,data,Toast.LENGTH_SHORT).show();
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
        id=intent.getStringExtra("id");
        pic = intent.getStringExtra("pic");
        price = intent.getStringExtra("price");
        goodsname = intent.getStringExtra("goodsname");
        arraypic = pic.split("[,]");
        for (String pic_name:arraypic){
            Log.e("pic_name=",pic_name);
        }
        Log.e("id=", id);
        initview();
        if (NetUtil.isConnnected(context)){
            new MainRequest(context,handler).getgoodsinfo(id);
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
        setview();
        initpic();
    }

    private void initview() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        img_goods_pic = (ImageView) findViewById(R.id.img_goods_pic);
        btn_lijigoumai = (Button) findViewById(R.id.btn_lijigoumai);
        btn_lijigoumai.setOnClickListener(this);
        tx_goods_name = (TextView) findViewById(R.id.tx_goods_name);
        tx_goods_price = (TextView) findViewById(R.id.tx_goods_price);
    }

    private void setview() {
        tx_goods_name.setText(goodsname);
        tx_goods_price.setText("￥"+price);
    }

    private void initpic() {
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        HashMap<String,String> url_maps = new HashMap<String, String>();
//        url_maps.put("Hannibal", "http://hq.xiaocool.net/uploads/microblog/sp1.jpg");
//        url_maps.put("Big Bang Theory", "http://hq.xiaocool.net/uploads/microblog/sp2.jpg");
//        url_maps.put("House of Cards", "http://hq.xiaocool.net/uploads/microblog/sp3.jpg");
//        url_maps.put("Game of Thrones", "http://hq.xiaocool.net/uploads/microblog/sp4.jpg");
        for (int i=0;i<arraypic.length;i++){
            int count = arraypic.length;
            url_maps.put(goodsname+"  图"+i, WebAddress.GETAVATAR+arraypic[i]);
        }
        if (arraypic.length==1){
            img_goods_pic.setVisibility(View.VISIBLE);
            mDemoSlider.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR+arraypic[0],img_goods_pic);
        }else {
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
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_lijigoumai:
                Intent intent = new Intent();
                intent.putExtra("id",id);
                intent.setClass(context,BuyNowActivity.class);
                startActivity(intent);
                break;
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
