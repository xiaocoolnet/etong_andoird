package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.AfterSalesManagementActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.CommonProblemActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.EditStoreActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsManageActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.OrderManageActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.StoreHomepageActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.UploadGoodsActivity;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/6/27.
 */
public class BusinessActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private Button btn_uploadgoods,btn_baobeiguanli,btn_dianpuguanli,btn_shouhouguanli,btn_dingdanguanli,btn_changjianwenti;
    private String shopid;
    private TextView tx_store_name;
    private Context context;
    private ImageView img_store_head;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.GETMYSHOP:
                    Log.e("getmyshop","getmyshop");
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    if(NetUtil.isConnnected(context)){
                        try {
                            String status = jsonObject.getString("status");
                            String data = jsonObject.getString("data");
                            if (status.equals("success")){
                                progressDialog.dismiss();
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String shopid = jsonObject1.getString("id");
                                String head = jsonObject1.getString("address");
                                String shopname = jsonObject1.getString("shopname");
                                Log.e("head=",head);
                                ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR+jsonObject1.getString("address"), img_store_head);
                                if (shopname.equals("null")||shopname==null||shopname.equals("")){
                                    tx_store_name.setText("未设置");
                                }else {
                                    tx_store_name.setText(shopname);
                                }
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(context,jsonObject.getString("data"),Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_business);
        context = this;
        initview();
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        Intent intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        Log.e("shopid=", intent.getStringExtra("shopid"));
        if(NetUtil.isConnnected(context)){
            progressDialog.setMessage("正在加载");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            new MainRequest(context,handler).getmyshop();
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
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
        btn_changjianwenti = (Button) findViewById(R.id.btn_changjianwenti);
        btn_changjianwenti.setOnClickListener(this);
        img_store_head = (ImageView) findViewById(R.id.img_store);
        img_store_head.setOnClickListener(this);
        tx_store_name = (TextView) findViewById(R.id.tx_business_touxiang);
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
                startActivityForResult(intent3, 1);
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
            case R.id.btn_changjianwenti:
                Intent intent6 = new Intent();
                intent6.setClass(BusinessActivity.this, CommonProblemActivity.class);
                startActivity(intent6);
                break;
            case R.id.img_store:
                Intent intent7 = new Intent();
                intent7.putExtra("shopid", shopid);
                intent7.setClass(BusinessActivity.this, StoreHomepageActivity   .class);
                startActivity(intent7);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.e("编辑店铺回调","success");
            try {
                new MainRequest(context,handler).getmyshop();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
