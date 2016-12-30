package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.android_etong.R;


/**
 * Created by hzh on 2016/12/30.
 */

public class ShopVerifySuccessActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_verify_success);
        initView();
    }

    private void initView() {
        RelativeLayout rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        TextView tvTop = (TextView) findViewById(R.id.top_title_text);
        tvTop.setText("验证详情");
        Button btnFinish = (Button) findViewById(R.id.btn_verify_finish);
        btnFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_verify_finish:
                finish();
                break;
        }
    }
}
