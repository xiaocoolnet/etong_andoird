package cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xiaocool.android_etong.R;

/**
 * Created by wzh on 2016/8/8.
 */
public class OrderDetailsActivity extends Activity {


    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.top_title_text)
    TextView topTitleText;
    @BindView(R.id.order_reserve_man)
    TextView orderReserveMan;
    @BindView(R.id.order_reserve_name)
    TextView orderReserveName;
    @BindView(R.id.order_reserve_address)
    TextView orderReserveAddress;
    @BindView(R.id.order_reserve_address_name)
    TextView orderReserveAddressName;
    @BindView(R.id.order_reserve_phone)
    TextView orderReservePhone;
    @BindView(R.id.shop_pic_icon)
    ImageView shopPicIcon;
    @BindView(R.id.order_details_shop_name_tv)
    TextView orderDetailsShopNameTv;
    @BindView(R.id.order_details_good_status_tv)
    TextView orderDetailsGoodStatusTv;
    @BindView(R.id.img_shopping_chanpin)
    ImageView imgShoppingChanpin;
    @BindView(R.id.order_details_good_name_tv)
    TextView orderDetailsGoodNameTv;
    @BindView(R.id.tx_shopping_cloth_color)
    TextView txShoppingClothColor;
    @BindView(R.id.order_details_good_color_tv)
    TextView orderDetailsGoodColorTv;
    @BindView(R.id.tx_shopping_cloth_size)
    TextView txShoppingClothSize;
    @BindView(R.id.order_details_good_size_tv)
    TextView orderDetailsGoodSizeTv;
    @BindView(R.id.tx_shopping_cloth_price)
    TextView txShoppingClothPrice;
    @BindView(R.id.tx_goods_count)
    TextView txGoodsCount;
    @BindView(R.id.good_status_btn_3)
    TextView goodStatusBtn3;
    @BindView(R.id.good_status_btn_2)
    TextView goodStatusBtn2;
    @BindView(R.id.good_status_btn)
    TextView goodStatusBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topTitleText.setText("订单详情");
    }


    @OnClick({R.id.btn_back, R.id.good_status_btn_3, R.id.good_status_btn_2, R.id.good_status_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.good_status_btn_3:
                break;
            case R.id.good_status_btn_2:
                break;
            case R.id.good_status_btn:
                break;
        }
    }



}
