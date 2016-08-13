package cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by wzh on 2016/8/11.
 */
public class PayNowActivity extends Activity {
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.top_title_text)
    TextView topTitleText;
    @BindView(R.id.pay_way0_icon)
    ImageView payWay0Icon;
    @BindView(R.id.pay_way0_name)
    TextView payWay0Name;
    @BindView(R.id.iv_payWay0_item)
    ImageView ivPayWay0Item;
    @BindView(R.id.rl_payWay0)
    RelativeLayout rlPayWay0;
    @BindView(R.id.pay_way1_icon)
    ImageView payWay1Icon;
    @BindView(R.id.pay_way1_name)
    TextView payWay1Name;
    @BindView(R.id.iv_payWay1_item)
    ImageView ivPayWay1Item;
    @BindView(R.id.rl_payWay1)
    RelativeLayout rlPayWay1;
    @BindView(R.id.pay_way2_icon)
    ImageView payWay2Icon;
    @BindView(R.id.pay_way2_name)
    TextView payWay2Name;
    @BindView(R.id.pay_now_price)
    TextView payNowPrice;
    @BindView(R.id.pay_now_price_tv)
    TextView payNowBtnPrice;
    @BindView(R.id.iv_payWay2_item)
    ImageView ivPayWay2Item;
    @BindView(R.id.rl_payWay2)
    RelativeLayout rlPayWay2;
    @BindView(R.id.rl_confirm_pay)
    RelativeLayout rlConfirmPay;
    private String orderId;
    private String shopName;
    private String price;
    private Context context;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.PAY_ORDER_LIST:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        if (jsonObject.getString("status").equals("success")) {
                            ToastUtils.makeShortToast(context, "支付订单成功！");
                            finish();
                        } else {
                            ToastUtils.makeShortToast(context, "支付失败！");
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
        setContentView(R.layout.shop_pay_now);
        context = this;
        ButterKnife.bind(this);
        orderId = getIntent().getStringExtra("orderId");
//        shopName = getIntent().getStringExtra("orderId");
        price = getIntent().getStringExtra("price");
        initView();
    }

    private void initView() {
        topTitleText.setText("支付订单");
        payWay0Icon.setSelected(true);
        payWay1Icon.setSelected(false);
        payWay2Icon.setSelected(false);
        payNowPrice.setText(price);
        payNowBtnPrice.setText("立即支付¥" + price);
    }

    @OnClick({R.id.btn_back, R.id.rl_payWay0, R.id.rl_payWay1, R.id.rl_payWay2, R.id.rl_confirm_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.rl_payWay0:
                ivPayWay0Item.setSelected(true);
                ivPayWay1Item.setSelected(false);
                ivPayWay2Item.setSelected(false);
                break;
            case R.id.rl_payWay1:
                ivPayWay0Item.setSelected(false);
                ivPayWay1Item.setSelected(true);
                ivPayWay2Item.setSelected(false);
                break;
            case R.id.rl_payWay2:
                ivPayWay0Item.setSelected(false);
                ivPayWay1Item.setSelected(false);
                ivPayWay2Item.setSelected(true);
                break;
            case R.id.rl_confirm_pay:
                //网络请求
                if (NetUtil.isConnnected(this)) {
                    new ShopRequest(this, handler).payOrder(orderId);
                }
                break;
        }
    }
}
