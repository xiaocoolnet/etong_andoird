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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.DetailAdapter;
import cn.xiaocool.android_etong.adapter.GoodRecommendAdapter;
import cn.xiaocool.android_etong.adapter.SelectPropertyAdapter;
import cn.xiaocool.android_etong.bean.Shop.Detail;
import cn.xiaocool.android_etong.bean.Shop.GoodRecommendBean;
import cn.xiaocool.android_etong.bean.Shop.Property;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by 潘 on 2016/7/20.
 */
public class GoodsDetailActivity extends Activity implements View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private Context context;
    private RelativeLayout rl_back;
    private ScrollView goodsdetail_scrollview;
    private SliderLayout mDemoSlider;
    private TextView tx_goods_name, tx_pic_txt, tx_goods_price, tv_goods_address, tv_goods_description, tv_no_content;
    private ImageView img_goods_pic;
    private Button btn_store;
    private Button btn_lijigoumai, btn_shopping_cart, btn_chat;
    private ImageView btnLike;
    private String id, pic, goodsname, price, shopname, address, description, shopid, shop_uid, shop_photo,content;
    private String[] arraypic;
    private int count = 1;
    private List<Detail.DataBean> dataBeans;
    private List<Property.DataBean> dataBeanList;
    private List<GoodRecommendBean.DataBean> goodRecommendBean;
    private SelectPropertyAdapter selectPropertyAdapter;
    private SelectPropertyAdapter selectPropertyAdapter1;
    private ListView list_detail;
    private ListView list_property;
    private ListView list_property1;
    private List<List<Boolean>> booleans;
    private List<List<Boolean>> booleans2;
    private DetailAdapter detailAdapter;
    private String lebal = "";
    private String proids = "";
    private ProgressDialog progressDialog;
    public static final String action = "jason.broadcast.action";
    private GoodRecommendAdapter goodRecommendAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GET_GOODS_INFO:
                    JSONObject json = (JSONObject) msg.obj;
                    try {
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            JSONObject jsonObject = json.getJSONObject("data")  ;
                            address = jsonObject.getString("address");
                            tv_goods_address.setText(address);
                            description = jsonObject.getString("description");
                            tv_goods_description.setText(description);
                            content =  jsonObject.getString("content");
                            if (content==null||content.equals("null")){
                                content = "";
                            }
                        } else {
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
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
                        if (status.equals("success")) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "添加购物车成功", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
//                            Toast.makeText(context,data,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case CommunalInterfaces.LIKE_GOOD:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        if (jsonObject1.getString("status").equals("success")) {
                            ToastUtils.makeShortToast(context, "收藏成功！");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.CANCLE_LIKE_GOOD:
                    JSONObject jsonObject2 = (JSONObject) msg.obj;
                    try {
                        if (jsonObject2.getString("status").equals("success")) {
                            ToastUtils.makeShortToast(context, "取消收藏成功！");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.GetGoodsComments:
                    JSONObject json1 = (JSONObject) msg.obj;
                    try {
                        String status = json1.getString("status");
                        String data = json1.getString("data");
                        if (status.equals("success")) {
                            JSONArray jsonArray = json1.getJSONArray("data");
                            dataBeans.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Detail.DataBean dataBean = new Detail.DataBean();
                                dataBean.setId(object.getString("id"));
                                dataBean.setContent(object.getString("content"));
                                dataBean.setAdd_time(object.getString("add_time"));
                                JSONArray jsonArray1 = object.getJSONArray("user_info");
                                JSONObject jsonObject3 = jsonArray1.getJSONObject(0);
                                dataBean.setName(jsonObject3.getString("name"));
                                dataBean.setPhoto(jsonObject3.getString("photo"));
                                dataBeans.add(dataBean);
                            }
                            if (detailAdapter != null) {
                                Log.e("success", "设置适配器");
                                detailAdapter.notifyDataSetChanged();
                            } else {
                                Log.e("success", "设置适配器");
                                detailAdapter = new DetailAdapter(context, dataBeans);
                                list_detail.setAdapter(detailAdapter);
                                setListViewHeightBasedOnChildren(list_detail);
                            }
                        } else {
                            list_detail.setVisibility(View.GONE);
                            tv_no_content.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.GetGoodsPropertyList:
                    Log.e("getmyshop", "getmyshop");
                    JSONObject jsonObject3 = (JSONObject) msg.obj;
                    if (NetUtil.isConnnected(context)) {
                        try {
                            String status = jsonObject3.getString("status");
                            String data = jsonObject3.getString("data");
                            if (status.equals("success")) {
                                dataBeanList.clear();
                                booleans.clear();
                                booleans2.clear();
                                JSONArray jsonArray = jsonObject3.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject4 = jsonArray.getJSONObject(i);
                                    Property.DataBean dataBean = new Property.DataBean();
                                    List<Boolean> booleans_item = new ArrayList<>();
                                    dataBean.setId(jsonObject4.getString("id"));
                                    dataBean.setTermid(jsonObject4.getString("typeid"));
                                    dataBean.setName(jsonObject4.getString("name"));
                                    JSONArray jsonArray1 = jsonObject4.getJSONArray("propertylist");
                                    List<Property.DataBean.PlistBean> plistBeans = new ArrayList<>();
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        Property.DataBean.PlistBean plistBean = new Property.DataBean.PlistBean();
                                        JSONObject object = jsonArray1.getJSONObject(j);
                                        plistBean.setId(object.getString("id"));
                                        plistBean.setName(object.getString("name"));
                                        plistBean.setProid(object.getString("proid"));
                                        plistBeans.add(plistBean);
                                        booleans_item.add(false);
                                    }
                                    dataBean.setPlist(plistBeans);
                                    dataBeanList.add(dataBean);
                                    booleans.add(booleans_item);
                                    booleans2.add(booleans_item);
                                }
                                Log.e("success", "good");
                            } else {
                                Toast.makeText(context, jsonObject3.getString("data"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                    }
                    //加入相关推荐栏
                case CommunalInterfaces.GET_GOOD_RECOMMEND:
                    JSONObject jsonObject4 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject4.getString("status");
                        JSONObject jsonObject5;
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject4.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject5 = jsonArray.getJSONObject(i);
                                GoodRecommendBean.DataBean dataBean = new GoodRecommendBean.DataBean();
                                dataBean.setId(jsonObject5.getString("id"));
                                dataBean.setShopid(jsonObject5.getString("shopid"));
                                dataBean.setGoodsname(jsonObject5.getString("goodsname"));
                                dataBean.setPrice(jsonObject5.getString("price"));
                                dataBean.setDescription(jsonObject5.getString("description"));
                                dataBean.setPicture(jsonObject5.getString("picture"));
                                goodRecommendBean.add(dataBean);
                            }
                            if (goodRecommendAdapter != null) {
                                goodRecommendAdapter.notifyDataSetChanged();
                            } else {
                                goodRecommendAdapter = new GoodRecommendAdapter(context, goodRecommendBean);
                                relevanceGridView.setAdapter(goodRecommendAdapter);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private GridView relevanceGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goodsdetails);
        context = this;
        Intent intent = getIntent();
        shopid = intent.getStringExtra("shopid");
//        Log.e("shopid=", shopid);
        id = intent.getStringExtra("id");//这是goodsId
        pic = intent.getStringExtra("pic");
        price = intent.getStringExtra("price");
        goodsname = intent.getStringExtra("goodsname");
        shopname = intent.getStringExtra("shopname");
        shop_uid = intent.getStringExtra("shop_uid");
        shop_photo = intent.getStringExtra("shop_photo");
        arraypic = pic.split("[,]");
        for (String pic_name : arraypic) {
            Log.e("pic_name=", pic_name);
        }
        Log.e("id=", id);
//        MyApp mAPP = (MyApp) getApplication();
//        handler = mAPP.getHandler();
//        handler.sendEmptyMessage(CommunalInterfaces.UPDATA_SHOPPING_CART);
        initview();
        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).getgoodsinfo(id);
            new MainRequest(context, handler).GetGoodsComments(id);
            new MainRequest(context, handler).GetGoodsPropertyList(id);
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
        setview();
        initpic();
        goodRecommendBean = new ArrayList<>();
        initRelevance();
    }

    //初始化相关推荐
    private void initRelevance() {
        if (NetUtil.isConnnected(this)) {
            new ShopRequest(this, handler).getGoodsRecommend(id, "", "", "");
        }
    }

    private void initview() {
        dataBeans = new ArrayList<>();
        dataBeanList = new ArrayList<>();
        booleans = new ArrayList<>();
        booleans2 = new ArrayList<>();
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
        goodsdetail_scrollview = (ScrollView) findViewById(R.id.goodsdetail_scrollview);
        progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        btnLike = (ImageView) findViewById(R.id.good_details_iv_like);
        btnLike.setOnClickListener(this);
        btn_chat = (Button) findViewById(R.id.btn_chat);
        btn_chat.setOnClickListener(this);
        list_detail = (ListView) findViewById(R.id.list_detail);
        tv_no_content = (TextView) findViewById(R.id.tv_no_content);
        tx_pic_txt = (TextView) findViewById(R.id.tx_pic_txt);
        tx_pic_txt.setOnClickListener(this);
        btn_store = (Button) findViewById(R.id.btn_store);
        btn_store.setOnClickListener(this);
        relevanceGridView = (GridView) findViewById(R.id.details_gv_relevance_goods);
    }


    private void setview() {
        tx_goods_name.setText(goodsname);
        tx_goods_price.setText("￥" + price);
    }

    public void setSelect(int firstPosition, int secondPosition, Boolean judge) {
        for (int i = 0; i < booleans.get(firstPosition).size(); i++) {
            booleans.get(firstPosition).set(i, false);
            if (i == secondPosition) {
                booleans.get(firstPosition).set(secondPosition, judge);
            }
        }
    }

    public Boolean judge() {
        int judge = 0;
        for (int i = 0; i < booleans.size(); i++) {
            judge = 0;
            for (int j = 0; j < booleans.get(i).size(); j++) {
                if (booleans.get(i).get(j)) {
                    judge++;
                }
            }
            if (judge == 0) {
                Toast.makeText(context, "请选择" + dataBeanList.get(i).getName() + "标签", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public String getLebal() {
        for (int i = 0; i < booleans.size(); i++) {
            for (int j = 0; j < booleans.get(i).size(); j++) {
                if (booleans.get(i).get(j)) {
                    lebal = lebal + dataBeanList.get(i).getName() + ":" + dataBeanList.get(i).getPlist().get(j).getName() + "; ";
                    proids = proids + dataBeanList.get(i).getPlist().get(j).getProid() + ",";
                }
            }
        }
        Log.e("lebal=", lebal);
        if (TextUtils.isEmpty(proids)) {
        } else {
            proids = proids.substring(0, proids.length() - 1);
            Log.e("proids", proids);
        }
        return lebal;
    }

    private void initpic() {
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        HashMap<String, String> url_maps = new HashMap<String, String>();
//        url_maps.put("Hannibal", "http://hq.xiaocool.net/uploads/microblog/sp1.jpg");
//        url_maps.put("Big Bang Theory", "http://hq.xiaocool.net/uploads/microblog/sp2.jpg");
//        url_maps.put("House of Cards", "http://hq.xiaocool.net/uploads/microblog/sp3.jpg");
//        url_maps.put("Game of Thrones", "http://hq.xiaocool.net/uploads/microblog/sp4.jpg");
        for (int i = 0; i < arraypic.length; i++) {
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
                goodsdetail_scrollview.scrollTo(0, 0);
                showPopwindow(context, arraypic[0], price, goodsname);
                break;
            case R.id.btn_shopping_cart:
                goodsdetail_scrollview.scrollTo(0, 0);
                showPopwindow_shoppingcart(GoodsDetailActivity.this, arraypic[0], price, goodsname);
                break;
            case R.id.good_details_iv_like:
                if (!btnLike.isSelected()) {
                    new ShopRequest(this, handler).likeGood(id);
                    btnLike.setSelected(true);
                } else {
                    new ShopRequest(this, handler).cancelLike(id);
                    btnLike.setSelected(false);
                }
                break;
            case R.id.btn_chat:
                Intent intent1 = new Intent();
                intent1.putExtra("shop_uid", shop_uid);
                intent1.putExtra("shop_photo", shop_photo);
                intent1.putExtra("shopname", shopname);
                intent1.setClass(context, ChatActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_store:
                Intent intent = new Intent();
                intent.setClass(context, StoreHomepageActivity.class);
                intent.putExtra("shopid", shopid);
                context.startActivity(intent);
                break;
            case R.id.tx_pic_txt:
                Intent intent2 = new Intent();
                intent2.setClass(context,ImgTxtDetailActivity.class);
                intent2.putExtra("pic",pic);
                intent2.putExtra("content",content);
                intent2.putExtra("goodsname",goodsname);
                startActivity(intent2);
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
    private void showPopwindow(final Context context, String picname, String goodsprice, String goodsname) {
        // 利用layoutInflater获得View
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        list_property = (ListView) view.findViewById(R.id.list_property);
        ImageView img_pic = (ImageView) view.findViewById(R.id.img_goods_pic_small);
        ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + picname, img_pic);
        TextView tx_goodsname = (TextView) view.findViewById(R.id.tx_goods_name);
        tx_goodsname.setText(goodsname);
        TextView tx_goods_price = (TextView) view.findViewById(R.id.tx_goods_price);
        tx_goods_price.setText(goodsprice);
        final TextView tx_goods_count = (TextView) view.findViewById(R.id.tx_goods_count);
        tx_goods_count.setText(String.valueOf(count));
        ImageView img_jia = (ImageView) view.findViewById(R.id.img_jia);
        ImageView img_jian = (ImageView) view.findViewById(R.id.img_jian);
        Button btn_comfirm = (Button) view.findViewById(R.id.btn_comfirm);

        selectPropertyAdapter = new SelectPropertyAdapter(GoodsDetailActivity.this, dataBeanList, booleans);
        list_property.setAdapter(selectPropertyAdapter);
        setListViewHeightBasedOnChildren(list_property);

        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judge()) {
                    Intent intent = new Intent();
                    intent.putExtra("count", count);
                    intent.putExtra("id", id);
                    intent.putExtra("shopname", shopname);
                    intent.putExtra("label", getLebal());
                    intent.putExtra("proid", proids);
                    intent.setClass(context, ComfirmOrderActivity.class);
                    startActivity(intent);
                    window.dismiss();
                } else {

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
                if (count > 1) {
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
    private void showPopwindow_shoppingcart(final Context context, String picname, String goodsprice, String goodsname) {
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
        list_property1 = (ListView) view.findViewById(R.id.list_property);
        ImageView img_pic = (ImageView) view.findViewById(R.id.img_goods_pic_small);
        ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + picname, img_pic);
        TextView tx_goodsname = (TextView) view.findViewById(R.id.tx_goods_name);
        tx_goodsname.setText(goodsname);
        TextView tx_goods_price = (TextView) view.findViewById(R.id.tx_goods_price);
        tx_goods_price.setText(goodsprice);
        final TextView tx_goods_count = (TextView) view.findViewById(R.id.tx_goods_count);
        tx_goods_count.setText(String.valueOf(count));
        ImageView img_jia = (ImageView) view.findViewById(R.id.img_jia);
        ImageView img_jian = (ImageView) view.findViewById(R.id.img_jian);
        Button btn_comfirm = (Button) view.findViewById(R.id.btn_comfirm);

        selectPropertyAdapter1 = new SelectPropertyAdapter(GoodsDetailActivity.this, dataBeanList, booleans);
        list_property1.setAdapter(selectPropertyAdapter1);
        setListViewHeightBasedOnChildren(list_property1);

        //添加购物车
        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setProgressStyle(AlertDialog.THEME_HOLO_LIGHT);
                progressDialog.setMessage("正在加载");
                progressDialog.show();

                if (NetUtil.isConnnected(context)) {
                    if (judge()) {
                        Log.e("shopid=", shopid);
                        window.dismiss();
                        Intent intent = new Intent(action);
                        intent.putExtra("data", "yes i am data");
                        sendBroadcast(intent);
                        getLebal();
                        new MainRequest(context, handler).addShoppingCart(id, String.valueOf(count), shopid, proids);
                    } else {
                    }
                } else {
                    Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
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
                if (count > 1) {
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

    /*
  解决scrollview下listview显示不全
*/
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
