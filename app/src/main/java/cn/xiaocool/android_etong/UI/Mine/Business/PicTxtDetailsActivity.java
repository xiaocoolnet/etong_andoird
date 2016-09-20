package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by SJL on 2016/9/20.
 */
public class PicTxtDetailsActivity extends Activity {
    private TextView tv_descript;
    private String description, shopid, id, pic;
    Context context;
    private String[] arraypic;
    private ImageView iv[]=new ImageView[5];
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CommunalInterfaces.GET_GOODS_INFO:
                    JSONObject json = (JSONObject) msg.obj;
                    try {
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            JSONObject jsonObject = json.getJSONObject("data");
                            description = jsonObject.getString("description");
                            tv_descript.setText(description);
                        } else {
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_pic_txt_details);
        tv_descript = (TextView) findViewById(R.id.tv_descript);
        context = this;

        Intent intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        id = intent.getStringExtra("id");//这是goodsId
        pic = intent.getStringExtra("pic");
        arraypic = pic.split("[,]");
        for (String pic_name : arraypic) {
            Log.e("pic_name=", pic_name);
        }
        iv[0] = (ImageView) findViewById(R.id.iv1);
        iv[1] = (ImageView) findViewById(R.id.iv2);
        iv[2] = (ImageView) findViewById(R.id.iv3);
        iv[3] = (ImageView) findViewById(R.id.iv4);
        iv[4] = (ImageView) findViewById(R.id.iv5);


        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).getgoodsinfo(id);
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }

        for (int i = 0; i < arraypic.length; i++) {
            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + arraypic[i], iv[i]);

        }
        if(5!=arraypic.length){
            int r=5-arraypic.length;
            for(int j=r-1;j<5;j++){
                iv[j].setVisibility(View.GONE);
            }
        }
    }
}