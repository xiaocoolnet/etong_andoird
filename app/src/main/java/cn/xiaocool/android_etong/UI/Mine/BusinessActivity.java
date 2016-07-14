package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.UploadGoodsActivity;

/**
 * Created by 潘 on 2016/6/27.
 */
public class BusinessActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private Button btn_uploadgoods;
    private String shopid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_business);
        initview();
        Intent intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        Log.e("shopid=", intent.getStringExtra("shopid"));
    }
    private void initview() {
        rl_back=(RelativeLayout)findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        btn_uploadgoods=(Button)findViewById(R.id.btn_uploadgoods);
        btn_uploadgoods.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_uploadgoods:
                Intent intent = new Intent();
                intent.setClass(BusinessActivity.this, UploadGoodsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
