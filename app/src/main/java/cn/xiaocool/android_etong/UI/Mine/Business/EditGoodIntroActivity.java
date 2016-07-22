package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.net.constant.request.MineRequest;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.util.IntentUtils;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/7/20.
 */
public class EditGoodIntroActivity extends Activity implements View.OnClickListener {
    private String picName, title, type, brand, artNo, standard, freight, shipAddress, goodDetails;
    private RelativeLayout btnBack;
    private TextView etTitle, etBrand, etartNo, etStandard, etFreight, etShipAddress, etGoodDetails;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.CHANGE_GOOD_INTRO:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONObject object = jsonObject.getJSONObject("data");
                            title = object.getString("goodsname");
                            standard = object.getString("unit");
                            freight = object.getString("price");
                            shipAddress = object.getString("address");
                            goodDetails = object.getString("description");
                            etTitle.setText(title);
                            //etBrand.setText(title);
                            //etartNo.setText(title);
                            etStandard.setText(standard);
                            etFreight.setText(freight);
                            etShipAddress.setText(shipAddress);
                            etGoodDetails.setText(goodDetails);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }
    };
    private String goodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.business_change_goods_intro);
        initView();
        Intent intent = getIntent();
        goodId = intent.getStringExtra("goodId");
        if (NetUtil.isConnnected(this)) {
            new MineRequest(this, handler).changeGoodIntro(goodId);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetUtil.isConnnected(this)) {
            new MineRequest(this, handler).changeGoodIntro(goodId);
        }
    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.rl_back);
        btnBack.setOnClickListener(this);
        etTitle = (TextView) findViewById(R.id.business_tv_good_name);
        etTitle.setOnClickListener(this);
        etBrand = (TextView) findViewById(R.id.business_tv_good_brand);
        etBrand.setOnClickListener(this);
        etartNo = (TextView) findViewById(R.id.business_tv_good_art_no);
        etartNo.setOnClickListener(this);
        etStandard = (TextView) findViewById(R.id.business_tv_good_standard);
        etStandard.setOnClickListener(this);
        etFreight = (TextView) findViewById(R.id.business_tv_good_freight);
        etFreight.setOnClickListener(this);
        etShipAddress = (TextView) findViewById(R.id.business_tv_good_ship_address);
        etShipAddress.setOnClickListener(this);
        etGoodDetails = (TextView) findViewById(R.id.business_tv_good_details);
        etGoodDetails.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.business_tv_good_name:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class, (String) etTitle.getText(),
                        "a=UpdateGoodsName&id=" + goodId + "&goodsname=");
                break;
            case R.id.business_tv_good_brand:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class, (String) etBrand.getText(), "");
                break;
            case R.id.business_tv_good_art_no:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class, (String) etartNo.getText(), "");
                break;
            case R.id.business_tv_good_standard:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class, (String) etStandard.getText(), "");
                break;
            case R.id.business_tv_good_freight:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class, (String) etFreight.getText(), "");
                break;
            case R.id.business_tv_good_ship_address:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class, (String) etShipAddress.getText(), "");
                break;
            case R.id.business_tv_good_details:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class, (String) etGoodDetails.getText(),
                        "a=UpdateGoodsDescription&id=" + goodId + "&description=");
                break;
        }
    }
}
