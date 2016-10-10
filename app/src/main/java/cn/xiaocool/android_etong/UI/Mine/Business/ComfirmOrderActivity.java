package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.AddressInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.db.sp.AddressDB;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/7/20.
 */
public class ComfirmOrderActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private LinearLayout ll_delivery_address;
    private EditText et_customer_remark;
    private TextView et_change_infor,et_customer_phone,et_customer_name,tv_judge;
    private LinearLayout ll_address;
    private List<AddressInfo> address = new ArrayList<AddressInfo>();
    private AddressDB addressDB;
    private TextView tx_comfirm_order;
    private String id, shopname;
    private String deliveryAddress = "", phone = "", name = "";
    private String money,lebal,proid;
    private String judge = "0";
    private int count = 0;
    private ImageView img_goods_pic;
    private TextView tx_goods_name, tx_goods_count, tx_goods_price,
            tx_goods_price_subtotal, tx_goods_price_total, tx_shopname,tv_lebal;
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
                            JSONObject jsonObject = json.getJSONObject("data");
                            tx_goods_name.setText(jsonObject.getString("goodsname"));
                            tx_goods_price.setText("￥" + jsonObject.getString("price"));
                            money = String.valueOf(count * Integer.parseInt(jsonObject.getString("price")));
                            tx_goods_price_total.setText("￥" + money);
                            tx_goods_price_subtotal.setText("￥" + jsonObject.getString("price"));
                            String pic = jsonObject.getString("picture");
                            String[] array_pic = pic.split("[,]");
                            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + array_pic[0], img_goods_pic);
                        } else {
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case CommunalInterfaces.BOOKING_SHOPPING:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        String data = jsonObject.getString("data");
                        if (status.equals("success")) {
                            Toast.makeText(context, "购买成功,请去个人页面支付", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_confirm_order);
        context = this;
        Intent intent = getIntent();
        count = intent.getIntExtra("count", 0);
        Log.e("count=", String.valueOf(count));
        id = intent.getStringExtra("id");
        shopname = intent.getStringExtra("shopname");
        lebal = intent.getStringExtra("label");
        proid = intent.getStringExtra("proid");
        initview();
        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).getgoodsinfo(id);
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }
    private void initview() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        tx_goods_name = (TextView) findViewById(R.id.tx_goods_name);
        tx_goods_count = (TextView) findViewById(R.id.tx_goods_count);
        tx_goods_count.setText("数量：" + String.valueOf(count));
        tx_goods_price = (TextView) findViewById(R.id.tx_goods_price);
        tx_goods_price_subtotal = (TextView) findViewById(R.id.tx_goods_price_subtotal);
        tx_goods_price_total = (TextView) findViewById(R.id.tx_goods_price_total);
        img_goods_pic = (ImageView) findViewById(R.id.img_goods_pic);
        ll_delivery_address = (LinearLayout) findViewById(R.id.ll_delivery_address);
        ll_delivery_address.setOnClickListener(this);
        tx_comfirm_order = (TextView) findViewById(R.id.tx_comfirm_order);
        tx_comfirm_order.setOnClickListener(this);
        et_customer_remark = (EditText) findViewById(R.id.et_customer_remark);
        tx_shopname = (TextView) findViewById(R.id.tx_shop_name);
        tx_shopname.setText(shopname);
        tv_lebal = (TextView) findViewById(R.id.tv_lebal);
        tv_lebal.setText(lebal);
        et_change_infor = (TextView) findViewById(R.id.et_change_infor);
        et_customer_phone = (TextView) findViewById(R.id.et_customer_phone);
        et_customer_name = (TextView) findViewById(R.id.et_customer_name);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        tv_judge = (TextView) findViewById(R.id.tv_judge);

        addressDB = AddressDB.getInstance(getBaseContext());
        address = addressDB.queryAddress();

        if (address!=null){
            for (int i = 0 ; i < address.size() ; i++){
                if (address.get(i).isStatus()){
                    ll_address.setVisibility(View.VISIBLE);
                    et_customer_phone.setText(address.get(i).getPhone());
                    et_customer_name.setText(address.get(i).getName());
                    et_change_infor.setText(address.get(i).getProvinces() + " " + address.get(i).getStreet());
                    tv_judge.setText("更改收货地址");
                    phone = address.get(i).getPhone();
                    name = address.get(i).getName();
                    deliveryAddress =address.get(i).getProvinces() + " " + address.get(i).getStreet();
                    Log.e("设置完毕","收货地址");
                }
            }
        }else {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                judge = "0";
                finish();
                break;
            case R.id.ll_delivery_address:
                //添加地址页面
                Intent intent = new Intent();
                intent.setClass(context, DeliveryAddressActivity.class);
                intent.putExtra("judge", judge);
                intent.putExtra("deliveryaddress", deliveryAddress);
                intent.putExtra("phone", phone);
                intent.putExtra("name", name);
                startActivityForResult(intent, 1);
                break;
            case R.id.tx_comfirm_order:

                String customer_remark = et_customer_remark.getText().toString();
                if (customer_remark == null || customer_remark.equals("")) {
                    customer_remark = "";
                }

                if (!TextUtils.isEmpty(deliveryAddress)) {
                    if (phone.length() == 11) {
                        if (!TextUtils.isEmpty(name)) {
                            new MainRequest(context, handler).bookingshopping(id, name, deliveryAddress, String.valueOf(count), phone, customer_remark, money,proid);
                        } else {
                            Toast.makeText(context, "请输入姓名", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "请输入地址", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.e("success", "deliveryaddress");
            deliveryAddress = data.getStringExtra("deliveryaddress1");
            judge = data.getStringExtra("judge");
            Log.e("first",judge);
            phone = data.getStringExtra("phone");
            name = data.getStringExtra("name");
            Log.e("deliveryaddress=", deliveryAddress);
            if (!phone.isEmpty()&&!name.isEmpty()&&!deliveryAddress.isEmpty()){
                ll_address.setVisibility(View.VISIBLE);
                et_customer_phone.setText(phone);
                et_customer_name.setText(name);
                et_change_infor.setText(deliveryAddress);
                tv_judge.setText("更改收货地址");
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            judge = "0";
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
