package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.android_etong.R;

/**
 * Created by 潘 on 2016/7/20.
 */
public class BuyNowActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private Button btn_comfirm;
    private ImageView img_jia,img_jian;
    private TextView tx_goods_count;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_buynow);
        context = this;
        initview();
    }

    private void initview() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        img_jia = (ImageView) findViewById(R.id.img_jia);
        img_jia.setOnClickListener(this);
        img_jian = (ImageView) findViewById(R.id.img_jian);
        img_jian.setOnClickListener(this);
        tx_goods_count = (TextView) findViewById(R.id.tx_goods_count);
        btn_comfirm = (Button) findViewById(R.id.btn_comfirm);
        btn_comfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.img_jia:
                count++;
                tx_goods_count.setText(String.valueOf(count));
                break;
            case R.id.img_jian:
                if (count>0){
                    count--;
                    tx_goods_count.setText(String.valueOf(count));
                }
                break;
            case R.id.btn_comfirm:
                Intent intent = new Intent();
                intent.setClass(context,ComfirmOrderActivity.class);
                startActivity(intent);
                break;
        }
    }
}
