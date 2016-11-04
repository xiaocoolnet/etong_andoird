package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
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
    private String picName, title, description,type, brand, artNo, standard, price, oprice, freight, inventory, goodDetails, shipAddress,content;
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
                            description = object.getString("description");
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
                            content = object.getString("content");
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
    private RelativeLayout rlTitle;
    private LinearLayout llType;
    private RelativeLayout rlBrand;
    private RelativeLayout rlArtNo,rlPrice,rlOPrice,rlFreight,rlDetails,rlAddress,rlIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.business_change_goods_intro);
        ButterKnife.bind(this);
        initView();
        Intent intent = getIntent();
        goodId = intent.getStringExtra("goodId");
        Log.e("goodidis",goodId);
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
        tvPrice = (TextView) findViewById(R.id.editGood_et_price);
        tvPrice.setOnClickListener(this);
        tvOprice = (TextView) findViewById(R.id.editGood_et_oprice);
        tvOprice.setOnClickListener(this);
        tvInventory = (TextView) findViewById(R.id.editGood_et_inventory);
        tvFreight = (TextView) findViewById(R.id.business_tv_good_freight);
        tvFreight.setOnClickListener(this);
        btnInventory = (RelativeLayout) findViewById(R.id.edit_good_inventory);
        btnInventory.setOnClickListener(this);
        etGoodDetails = (TextView) findViewById(R.id.editGood_et_details);
        etGoodDetails.setOnClickListener(this);
        etShipAddress = (TextView) findViewById(R.id.business_tv_good_ship_address);
        etShipAddress.setOnClickListener(this);
        rlTitle = (RelativeLayout) findViewById(R.id.edit_good_title);
        rlTitle.setOnClickListener(this);//标题
        llType = (LinearLayout) findViewById(R.id.edit_good_type);
        llType.setOnClickListener(this);//分类
        rlBrand = (RelativeLayout) findViewById(R.id.edit_good_brand);
        rlBrand.setOnClickListener(this);//品牌
        rlArtNo = (RelativeLayout) findViewById(R.id.edit_good_artNo);
        rlArtNo.setOnClickListener(this);//货号
        rlPrice = (RelativeLayout) findViewById(R.id.edit_good_price);
        rlPrice.setOnClickListener(this);//现价
        rlOPrice = (RelativeLayout) findViewById(R.id.edit_good_oPrice);
        rlOPrice.setOnClickListener(this);//原价
        rlFreight = (RelativeLayout) findViewById(R.id.edit_good_freight);
        rlFreight.setOnClickListener(this);//运费
        rlDetails = (RelativeLayout) findViewById(R.id.edit_good_details);
        rlDetails.setOnClickListener(this);//详情
        rlAddress = (RelativeLayout) findViewById(R.id.edit_good_address);
        rlAddress.setOnClickListener(this);//发货地址
        rlIntro = (RelativeLayout) findViewById(R.id.edit_good_intro);
        rlIntro.setOnClickListener(this);//宝贝简介
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
            case R.id.edit_good_title:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        title, "a=UpdateGoodsName&id=" + goodId + "&goodsname=");
                break;
            case R.id.edit_good_intro:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        description, "a=UpdateGoodsDescription&id=" + goodId + "description=");
                break;
            case R.id.edit_good_type:
                IntentUtils.changeInforIntent(this, ChangeGoodTypeActivity.class,
                        "", "a=UpdateGoodsType&id=" + goodId + "&type=");//分类
                break;
            case R.id.edit_good_brand:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        brand, "a=UpdateGoodsBand" + goodId + "&band=");
                break;
            case R.id.edit_good_artNo:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        artNo, "&a=UpdateGoodsArtno" + goodId + "&artno=");
                break;
            case R.id.business_tv_good_standard:
                Intent standardIntent = new Intent();
                standardIntent.setClass(this, EditStandardItemActivity.class);//跳转修改商品规格
                standardIntent.putExtra("goodId", goodId);
                startActivity(standardIntent);
                break;
            case R.id.edit_good_oPrice:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        oprice, "a=UpdateGoodsOPrice&id=" + goodId + "&oprice=");
                break;
            case R.id.edit_good_price:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        price, "a=UpdateGoodsPrice&id=" + goodId + "&price=");
                break;
            case R.id.edit_good_freight:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        freight, "a=UpdateGoodsFreight&id=" + goodId + "&freight=");
                break;
            case R.id.edit_good_inventory:
//                Intent inventoryIntent = new Intent();
//                inventoryIntent.setClass(this, UploadInventoryActivity.class);//跳转修改库存
//                inventoryIntent.putExtra("goodId", goodId);
//                startActivity(inventoryIntent);
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                       inventory, "a=UpdateStock&goodsid=" + goodId);
                break;
            case R.id.edit_good_details:
                IntentUtils.changeInforIntent(this, ChangeGoodDetailsActivity.class,
                        content, "a=UpdateGoodsDescription&id=" + goodId + "&content=");
                break;
            case R.id.edit_good_address:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        shipAddress, "a=UpdateGoodsDescription&id=" + goodId + "&address=");
                break;
        }
    }
}
