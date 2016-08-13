package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
    private ScrollView goodsdetail_scrollview;
    private SliderLayout mDemoSlider;
    private TextView tx_goods_name,tx_goods_price,tv_goods_address,tv_goods_description;
    private ImageView img_goods_pic;
    private Button btn_lijigoumai,btn_shopping_cart;
    private String id , pic , goodsname , price,shopname,address,description,shopid;
    private String[] arraypic;
    private int count = 1;
    private ProgressDialog progressDialog;
    public static final String action = "jason.broadcast.action";
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
                            JSONObject jsonObject = json.getJSONObject("data");
                            address = jsonObject.getString("address");
                            tv_goods_address.setText(address);
                            description = jsonObject.getString("description");
                            tv_goods_description.setText(description);
                        }else {
                            Toast.makeText(context,data,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.ADD_SHOPPING_CART:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        String data = jsonObject.getString("data");
                        if (status.equals("success")){
                            progressDialog.dismiss();
                            Toast.makeText(context,"添加购物车成功",Toast.LENGTH_SHORT).show();
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(context,data,Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_goodsdetails);
        context = this;
        Intent intent = getIntent();
        shopid=intent.getStringExtra("shopid");
        Log.e("shopid=",shopid);
        id=intent.getStringExtra("id");//这是goodsId
        pic = intent.getStringExtra("pic");
        price = intent.getStringExtra("price");
        goodsname = intent.getStringExtra("goodsname");
        shopname = intent.getStringExtra("shopname");
        arraypic = pic.split("[,]");
        for (String pic_name:arraypic){
            Log.e("pic_name=",pic_name);
        }
        Log.e("id=", id);
//        MyApp mAPP = (MyApp) getApplication();
//        handler = mAPP.getHandler();
//        handler.sendEmptyMessage(CommunalInterfaces.UPDATA_SHOPPING_CART);
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
        btn_shopping_cart = (Button) findViewById(R.id.btn_shopping_cart);
        btn_shopping_cart.setOnClickListener(this);
        tx_goods_name = (TextView) findViewById(R.id.tx_goods_name);
        tx_goods_price = (TextView) findViewById(R.id.tx_goods_price);
        tv_goods_address = (TextView) findViewById(R.id.tv_goods_address);
        tv_goods_description = (TextView) findViewById(R.id.tv_goods_description);
        goodsdetail_scrollview = (ScrollView)findViewById(R.id.goodsdetail_scrollview);
        progressDialog = new ProgressDialog(context,ProgressDialog.STYLE_SPINNER);
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
                goodsdetail_scrollview.scrollTo(0,0);
                showPopwindow(context, arraypic[0], price, goodsname);
                break;
            case R.id.btn_shopping_cart:
                goodsdetail_scrollview.scrollTo(0,0);
                showPopwindow_shoppingcart(context, arraypic[0], price, goodsname);
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


    /**
     * 显示popupWindow
     */
    private void showPopwindow(final Context context,String picname,String goodsprice,String goodsname) {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindow_buynow, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);


        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        window.setBackgroundDrawable(dw);


        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(GoodsDetailActivity.this.findViewById(R.id.btn_lijigoumai),
                Gravity.BOTTOM, 0, 0);

//        // 这里检验popWindow里的button是否可以点击
//        Button first = (Button) view.findViewById(R.id.first);
//        first.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                System.out.println("第一个按钮被点击了");
//            }
//        });

        ImageView img_pic = (ImageView)view.findViewById(R.id.img_goods_pic_small);
        ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + picname, img_pic);
        TextView tx_goodsname = (TextView)view.findViewById(R.id.tx_goods_name);
        tx_goodsname.setText(goodsname);
        TextView tx_goods_price = (TextView)view.findViewById(R.id.tx_goods_price);
        tx_goods_price.setText(goodsprice);
        final TextView tx_goods_count = (TextView)view.findViewById(R.id.tx_goods_count);
        tx_goods_count.setText(String.valueOf(count));
        ImageView img_jia = (ImageView)view.findViewById(R.id.img_jia);
        ImageView img_jian = (ImageView)view.findViewById(R.id.img_jian);
        Button btn_comfirm = (Button)view.findViewById(R.id.btn_comfirm);

        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("count",count);
                intent.putExtra("id",id);
                intent.putExtra("shopname",shopname);
                intent.setClass(context, ComfirmOrderActivity.class);
                startActivity(intent);
                window.dismiss();
            }
        });

        img_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                tx_goods_count.setText(String.valueOf(count));
            }
        });

        img_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count>1){
                    count--;
                    tx_goods_count.setText(String.valueOf(count));
                }
            }
        });

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow_shoppingcart(final Context context,String picname,String goodsprice,String goodsname) {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindow_buynow, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);


        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        window.setBackgroundDrawable(dw);


        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(GoodsDetailActivity.this.findViewById(R.id.btn_lijigoumai),
                Gravity.BOTTOM, 0, 0);

//        // 这里检验popWindow里的button是否可以点击
//        Button first = (Button) view.findViewById(R.id.first);
//        first.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                System.out.println("第一个按钮被点击了");
//            }
//        });

        ImageView img_pic = (ImageView)view.findViewById(R.id.img_goods_pic_small);
        ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + picname, img_pic);
        TextView tx_goodsname = (TextView)view.findViewById(R.id.tx_goods_name);
        tx_goodsname.setText(goodsname);
        TextView tx_goods_price = (TextView)view.findViewById(R.id.tx_goods_price);
        tx_goods_price.setText(goodsprice);
        final TextView tx_goods_count = (TextView)view.findViewById(R.id.tx_goods_count);
        tx_goods_count.setText(String.valueOf(count));
        ImageView img_jia = (ImageView)view.findViewById(R.id.img_jia);
        ImageView img_jian = (ImageView)view.findViewById(R.id.img_jian);
        Button btn_comfirm = (Button)view.findViewById(R.id.btn_comfirm);
        //添加购物车
        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setProgressStyle(AlertDialog.THEME_HOLO_LIGHT);
                progressDialog.setMessage("正在加载");
                progressDialog.show();
                if (NetUtil.isConnnected(context)){
                    Log.e("shopid=", shopid);
                    window.dismiss();
                    Intent intent = new Intent(action);
                    intent.putExtra("data", "yes i am data");
                    sendBroadcast(intent);
                    new MainRequest(context,handler).addShoppingCart(id, String.valueOf(count), shopid);
                }else {
                    Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

        img_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                tx_goods_count.setText(String.valueOf(count));
            }
        });

        img_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count>1){
                    count--;
                    tx_goods_count.setText(String.valueOf(count));
                }
            }
        });

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });
    }
}
