package cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xiaocool.android_etong.R;

/**
 * Created by wzh on 2016/8/11.
 */
public class SellerSearchOrderActivity extends Activity {
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.top_title_text)
    TextView topTitleText;
    @BindView(R.id.seller_search_order_et)
    EditText sellerSearchOrderEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.seller_order_search);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topTitleText.setText("订单搜索");
    }


    @OnClick({R.id.btn_back, R.id.seller_search_order_et})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.seller_search_order_et:
                break;
        }
    }
}
