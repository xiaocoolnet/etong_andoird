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
import cn.xiaocool.android_etong.UI.Mine.Business.AfterSalesManagementActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.EditStoreActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsManageActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.OrderManageActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.UploadGoodsActivity;

/**
 * Created by 潘 on 2016/6/27.
 */
public class BusinessActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private Button btn_uploadgoods,btn_baobeiguanli,btn_dianpuguanli,btn_shouhouguanli,btn_dingdanguanli;
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
        btn_baobeiguanli = (Button) findViewById(R.id.btn_baobeiguanli);
        btn_baobeiguanli.setOnClickListener(this);
        btn_dianpuguanli = (Button) findViewById(R.id.btn_dianpuguanli);
        btn_dianpuguanli.setOnClickListener(this);
        btn_shouhouguanli = (Button) findViewById(R.id.btn_shouhouguanli);
        btn_shouhouguanli.setOnClickListener(this);
        btn_dingdanguanli = (Button) findViewById(R.id.btn_dingdanguanli);
        btn_dingdanguanli.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_uploadgoods:
                Intent intent = new Intent();
                intent.putExtra("shopid", shopid);
                intent.setClass(BusinessActivity.this, UploadGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_baobeiguanli:
                Intent intent2 = new Intent();
                intent2.putExtra("shopid", shopid);
                intent2.setClass(BusinessActivity.this, GoodsManageActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_dianpuguanli:
                Intent intent3 = new Intent();
                intent3.putExtra("shopid", shopid);
                intent3.setClass(BusinessActivity.this, EditStoreActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_shouhouguanli:
                Intent intent4 = new Intent();
                intent4.putExtra("shopid", shopid);
                intent4.setClass(BusinessActivity.this, AfterSalesManagementActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_dingdanguanli:
                Intent intent5 = new Intent();
                intent5.putExtra("shopid", shopid);
                intent5.setClass(BusinessActivity.this, OrderManageActivity.class);
                startActivity(intent5);
                break;
        }
    }
}
