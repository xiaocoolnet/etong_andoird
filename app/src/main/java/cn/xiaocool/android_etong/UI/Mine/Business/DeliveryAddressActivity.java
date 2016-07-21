package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;

import cn.xiaocool.android_etong.R;

/**
 * Created by 潘 on 2016/7/21.
 */
public class DeliveryAddressActivity extends Activity implements View.OnClickListener {

    private Context context;
    private String deliveryAddress;
    private EditText et_change_infor;
    private RelativeLayout rl_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delivery_address);
        context = this;
        initview();
        Intent intent = getIntent();
        deliveryAddress = intent.getStringExtra("deliveryaddress");
        et_change_infor.setText(deliveryAddress);
    }

    private void initview() {
        et_change_infor = (EditText) findViewById(R.id.et_change_infor);
        et_change_infor.setText(deliveryAddress);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                deliveryAddress = et_change_infor.getText().toString();
                Intent intent1 = new Intent();
                intent1.putExtra("deliveryaddress1",deliveryAddress);
                Log.e("deliveryaddress=",deliveryAddress);
                setResult(RESULT_OK,intent1);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            deliveryAddress = et_change_infor.getText().toString();
            Intent intent1 = new Intent();
            intent1.putExtra("deliveryaddress",deliveryAddress);
            setResult(RESULT_OK,intent1);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
