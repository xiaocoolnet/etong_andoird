package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.MineFootprintAdapter;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.HomeRequest;

/**
 * Created by 潘 on 2016/10/10.
 */

public class MineFootprintActivity extends Activity implements View.OnClickListener {

    private ListView listView;
    private TextView tvTitle;
    private RelativeLayout rlBack;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList;
    private Context context;
    private MineFootprintAdapter everydayChoicenessAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GetMyBrowseHistory:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                NewArrivalBean.NewArrivalDataBean newArrivalDataBean = new NewArrivalBean.NewArrivalDataBean();
                                newArrivalDataBean.setId(dataObject.getString("object_id"));
                                newArrivalDataBean.setGoodsname(dataObject.getString("goodsname"));
                                newArrivalDataBean.setPrice(dataObject.getString("price"));
                                newArrivalDataBean.setDescription(dataObject.getString("description"));
                                newArrivalDataBean.setPicture(dataObject.getString("photo"));

                                newArrivalDataBeanList.add(newArrivalDataBean);
                            }
                            everydayChoicenessAdapter = new MineFootprintAdapter(context, newArrivalDataBeanList);
                            listView.setAdapter(everydayChoicenessAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.everyday_choiceness);
        context = this;
        initView();
        new HomeRequest(this, handler).GetMyBrowseHistory();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView_everyday_choiceness);
        newArrivalDataBeanList = new ArrayList<>();
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("我的足迹");
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
