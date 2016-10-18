package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 潘 on 2016/9/30.
 */

public class WalletActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private String name , touxiang;
    private Button btn_youhuiquan,btn_xianjinhongbao;
    private CircleImageView img_mine_head;
    private TextView tx_mine_name,tv_yue;
    private Context context;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.GetMyWallet:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")){
                            Log.e("success","GetMyWallet");
                            JSONObject object = jsonObject1.getJSONObject("data");
                            if (object.getString("availablemoney").equals("null")||object.getString("availablemoney")==null){
                                tv_yue.setText("余额 0元");
                            }else {
                                tv_yue.setText("余额 "+object.getString("availablemoney")+"元");
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wallet);
        context = this;
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        touxiang = intent.getStringExtra("touxiang");
        initView();
        new MainRequest(context,handler).GetMyWallet();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        btn_youhuiquan = (Button) findViewById(R.id.btn_youhuiquan);
        btn_youhuiquan.setOnClickListener(this);
        btn_xianjinhongbao = (Button) findViewById(R.id.btn_xianjinhongbao);
        btn_xianjinhongbao.setOnClickListener(this);
        img_mine_head = (CircleImageView) findViewById(R.id.img_mine_head);
        tv_yue = (TextView) findViewById(R.id.tv_yue);
        tx_mine_name = (TextView) findViewById(R.id.tx_mine_name);
        if (name.equals("null")) {
            tx_mine_name.setText("未设置昵称");
        } else {
            tx_mine_name.setText(name);
        }
        ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + touxiang, img_mine_head);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_youhuiquan:
                startActivity(new Intent(context, CouponActivity.class));
                break;
            case R.id.btn_xianjinhongbao:
                Toast.makeText(context,"该功能正在完善",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
