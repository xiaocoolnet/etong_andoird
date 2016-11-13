package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.StoreHomePageAdapter;
import cn.xiaocool.android_etong.bean.business.StoreHomepage;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by 潘 on 2016/7/19.
 */
public class StoreHomepageActivity extends Activity implements View.OnClickListener {
    private Context context;
    private Button btn_shoucang;
    private String shopid, shopname, shop_uid, shop_photo;
    private RelativeLayout rl_back;
    private TextView tx_store_name;
    private Button btn_chat_store;
    private ImageView img_store_head;
    private GridView list_store_goods;
    private ArrayList<StoreHomepage.DataBean> goods_list;
    private StoreHomePageAdapter storeHomePageAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GETSHOPGOODLIST:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            goods_list.clear();
                            JSONArray jsonarray = jsonObject.getJSONArray("data");
                            Log.e("jsonArray", jsonObject.getString("data"));
                            int length = jsonarray.length();
                            JSONObject json;
                            for (int i = 0; i < length; i++) {
                                json = (JSONObject) jsonarray.get(i);
                                StoreHomepage.DataBean databean = new StoreHomepage.DataBean();
                                String pic = json.getString("picture");
//                                String[] arraypic = pic.split("[,]");
//                                Log.e("第一张图片名称",arraypic[0]);
                                databean.setGoodsname(json.getString("goodsname"));
                                databean.setPicture(pic);
                                databean.setPrice(json.getString("price"));
                                databean.setShopid(json.getString("shopid"));
                                databean.setId(json.getString("id"));
                                databean.setShowid(shopname);
                                goods_list.add(databean);
                            }
                            if (storeHomePageAdapter != null) {
                                storeHomePageAdapter.notifyDataSetChanged();
                            } else {
                                storeHomePageAdapter = new StoreHomePageAdapter(context, goods_list, shopid, shop_uid, shop_photo);
                                list_store_goods.setAdapter(storeHomePageAdapter);
//                                setListViewHeightBasedOnChildren(list_store_goods);
                            }
                        } else {
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.GET_SHOP_INFO:
                    Log.e("getmyshop", "getmyshop");
                    JSONObject jsonObject0 = (JSONObject) msg.obj;
                    if (NetUtil.isConnnected(context)) {
                        try {
                            String status = jsonObject0.getString("status");
                            String data = jsonObject0.getString("data");
                            if (status.equals("success")) {
                                JSONObject jsonObject1 = jsonObject0.getJSONObject("data");
                                String shopid = jsonObject1.getString("id");
                                String head = jsonObject1.getString("photo");
                                shop_uid = jsonObject1.getString("uid");
                                shop_photo = head;
                                shopname = jsonObject1.getString("shopname");
                                Log.e("head=", head);
                                ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + jsonObject1.getString("photo"), img_store_head);
                                if (shopname.equals("null") || shopname == null || shopname.equals("")) {
                                    tx_store_name.setText("未设置");
                                } else {
                                    tx_store_name.setText(shopname);
                                }
                                initdata();
                            } else {
                                Toast.makeText(context, jsonObject0.getString("data"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case CommunalInterfaces.LIKE_GOOD:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        if (jsonObject1.getString("status").equals("success")) {
                            ToastUtils.makeShortToast(context, "收藏成功！");
                            btn_shoucang.setText("已收藏");
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
                            btn_shoucang.setText("收藏");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private RelativeLayout shopShare;

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_store_homepage);
        context = this;
        Intent intent = getIntent();
        shopid = intent.getStringExtra("shopid");


        // 微信注册初始化
        api = WXAPIFactory.createWXAPI(this, "wxb32c00ffa8140d93", true);
        api.registerApp("wxb32c00ffa8140d93");


        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).getshopinfo(shopid);
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
        initview();

    }

    private void initview() {
        goods_list = new ArrayList<StoreHomepage.DataBean>();
        list_store_goods = (GridView) findViewById(R.id.list_store_goods);
        tx_store_name = (TextView) findViewById(R.id.tx_store_name);
        img_store_head = (ImageView) findViewById(R.id.img_store_head);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        btn_shoucang = (Button) findViewById(R.id.btn_shoucang);
        btn_shoucang.setOnClickListener(this);
        btn_chat_store = (Button) findViewById(R.id.btn_chat_store);
        btn_chat_store.setOnClickListener(this);
        shopShare = (RelativeLayout) findViewById(R.id.shop_right_share_icon);
        shopShare.setOnClickListener(this);
    }

    private void initdata() {
        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).getshopgoodlist(shopid);
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_shoucang:
                if (!btn_shoucang.isSelected()) {
                    new ShopRequest(this, handler).likeShop(shopid);
                    btn_shoucang.setSelected(true);
                } else {
                    new ShopRequest(this, handler).cancelLikeShop(shopid);
                    btn_shoucang.setSelected(false);
                }
                break;
            case R.id.btn_chat_store:
                Intent intent1 = new Intent();
                intent1.putExtra("shop_uid", shop_uid);
                intent1.putExtra("shop_photo", shop_photo);
                intent1.putExtra("shopname", shopname);
                intent1.setClass(context, ChatActivity.class);
                startActivity(intent1);
                break;
            case R.id.shop_right_share_icon:
                showPopupMenu(shopShare);//弹出分享店铺菜单
                break;

        }
    }


    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.share_shop, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                switch (item.getItemId()) {
                    case R.id.share_shop_to_wechat0:
                        share2weixin(0,shopname);//好友
                        break;
                    case R.id.share_shop_to_wechat1:
                        share2weixin(1,shopname);//朋友圈
                        break;
                }
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
//                Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });

        popupMenu.show();
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


    private void share2weixin(int flag,String shopName) {
        // Bitmap bmp = BitmapFactory.decodeResource(getResources(),
        // R.drawable.weixin_share);

        if (!api.isWXAppInstalled()) {
            Toast.makeText(context, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://baidu.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = shopName;
        msg.description = "非常好的店铺！！";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(),
                R.drawable.wechat_logo);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        api.sendReq(req);
    }


}
