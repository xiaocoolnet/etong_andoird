package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MineRequest;
import cn.xiaocool.android_etong.util.IntentUtils;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/7/20.
 */
public class EditGoodIntroActivity extends Activity implements View.OnClickListener {
    private String picName, title, type, brand, artNo, standard, price, oprice, freight, inventory, goodDetails, shipAddress;
    private RelativeLayout btnBack, btnStandard, btnInventory;
    private TextView tvTitle, tvType, tvBrand, tvArtNo, tvStandard, tvPrice, tvOprice, tvFreight, tvInventory,
            etGoodDetails, etShipAddress;
    private RelativeLayout rlPic;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.CHANGE_GOOD_INTRO:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONObject object = jsonObject.getJSONObject("data");
                            picName = object.getString("picture");
                            Log.e("aaaabbbb", picName);
                            title = object.getString("goodsname");
//                            type = object.getString("type");
                            brand = object.getString("brand");
                            artNo = object.getString("artno");
                            standard = object.getString("unit");
                            price = object.getString("price");
                            oprice = object.getString("oprice");
                            freight = object.getString("freight");
                            inventory = object.getString("inventory");
                            goodDetails = object.getString("description");
                            shipAddress = object.getString("address");
                            Log.e("result", picName + title + type + brand + artNo + standard + price
                                    + oprice + freight + inventory + goodDetails + shipAddress);
                            tvTitle.setText(title);
                            tvType.setText(type);
                            tvBrand.setText(brand);
                            tvArtNo.setText(artNo);
                            tvStandard.setText(standard);
                            tvPrice.setText(price);
                            tvOprice.setText(oprice);
                            tvFreight.setText(freight);
                            tvInventory.setText(inventory);
                            etGoodDetails.setText(goodDetails);
                            etShipAddress.setText(shipAddress);
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
        ButterKnife.bind(this);
        initView();
        Intent intent = getIntent();
        goodId = intent.getStringExtra("goodId");
        if (NetUtil.isConnnected(this)) {


            new MineRequest(this, handler).changeGoodIntro(goodId);
            Log.e("next", "next");
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
        rlPic = (RelativeLayout) findViewById(R.id.rl_carousel_pic);
        rlPic.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.business_tv_good_name);
        tvTitle.setOnClickListener(this);
        tvType = (TextView) findViewById(R.id.business_et_change_type2);
        tvType.setOnClickListener(this);
        tvBrand = (TextView) findViewById(R.id.business_tv_good_brand);
        tvBrand.setOnClickListener(this);
        tvArtNo = (TextView) findViewById(R.id.business_tv_good_art_no);
        tvArtNo.setOnClickListener(this);
        btnStandard = (RelativeLayout) findViewById(R.id.business_tv_good_standard);
        btnStandard.setOnClickListener(this);
//        tvStandard = (TextView) findViewById(R.id.business_tv_good_standard);
//        tvStandard.setOnClickListener(this);
        tvPrice = (TextView) findViewById(R.id.editGood_et_price);
        tvPrice.setOnClickListener(this);
        tvOprice = (TextView) findViewById(R.id.editGood_et_oprice);
        tvOprice.setOnClickListener(this);
        tvInventory = (TextView) findViewById(R.id.editGood_et_inventory);
        tvFreight = (TextView) findViewById(R.id.business_tv_good_freight);
        tvFreight.setOnClickListener(this);
        btnInventory = (RelativeLayout) findViewById(R.id.edit_good_invrntpry);
        btnInventory.setOnClickListener(this);
        etGoodDetails = (TextView) findViewById(R.id.editGood_et_details);
        etGoodDetails.setOnClickListener(this);
        etShipAddress = (TextView) findViewById(R.id.business_tv_good_ship_address);
        etShipAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_carousel_pic:
                Intent intent = new Intent();
                intent.setClass(this, EditGoodLookPicActivity.class);
                intent.putExtra("picName", picName);
                startActivity(intent);
                break;
            case R.id.business_tv_good_name:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        (String) tvTitle.getText(), "a=UpdateGoodsName&id=" + goodId + "&goodsname=");
                break;
            case R.id.business_tv_good_brand:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        (String) tvBrand.getText(), "a=UpdateGoodsBand" + goodId + "&band=");
                break;
            case R.id.business_tv_good_art_no:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        (String) tvArtNo.getText(), "");
                break;
            case R.id.business_tv_good_standard:
                Intent standardIntent = new Intent();
                standardIntent.setClass(this, EditStandardItemActivity.class);//跳转修改商品规格
                standardIntent.putExtra("goodId", goodId);
                startActivity(standardIntent);
                break;
            case R.id.editGood_et_price:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        (String) tvPrice.getText(), "a=UpdateGoodsPrice&id=" + goodId + "&price=");
                break;
            case R.id.editGood_et_oprice:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        (String) tvOprice.getText(), "a=UpdateGoodsOPrice&id=" + goodId + "&oprice=");
                break;
            case R.id.business_tv_good_freight:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        (String) tvFreight.getText(), "a=UpdateGoodsFreight&id=" + goodId + "&freight=");
                break;
            case R.id.editGood_et_inventory:
//                Intent inventoryIntent = new Intent();
//                inventoryIntent.setClass(this, UploadInventoryActivity.class);//跳转修改库存
//                inventoryIntent.putExtra("goodId", goodId);
//                startActivity(inventoryIntent);
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        (String) tvInventory.getText(), "a=UpdateStock&goodsid=" + goodId);
                break;
            case R.id.editGood_et_details:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        (String) etGoodDetails.getText(), "a=UpdateGoodsDescription&id=" + goodId + "&description=");
                break;
            case R.id.business_tv_good_ship_address:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        (String) etShipAddress.getText(), "");
                break;
        }
    }
}
