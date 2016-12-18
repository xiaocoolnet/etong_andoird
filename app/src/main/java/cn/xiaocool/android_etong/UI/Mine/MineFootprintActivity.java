package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

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

    private PullToRefreshListView listView;
    private TextView tvTitle;
    private RelativeLayout rlBack;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanListLoading;
    private Context context;
    private ProgressDialog progressDialog;
    private MineFootprintAdapter everydayChoicenessAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GetMyBrowseHistory:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            progressDialog.dismiss();
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
                            if (newArrivalDataBeanList.size() >= 7) {
                                for (int i = 0; i < 7; i++) {
                                    newArrivalDataBeanListLoading.add(newArrivalDataBeanList.get(i));
                                }
                            } else {
                                for (int i = 0; i < newArrivalDataBeanList.size(); i++) {
                                    newArrivalDataBeanListLoading.add(newArrivalDataBeanList.get(i));
                                }
                            }
                            everydayChoicenessAdapter = new MineFootprintAdapter(context, newArrivalDataBeanListLoading);
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
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        progressDialog.setMessage("正在加载...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        new HomeRequest(this, handler).GetMyBrowseHistory();


        //设置可上拉刷新和下拉刷新
        listView.setMode(PullToRefreshBase.Mode.BOTH);

        //设置刷新时显示的文本
        ILoadingLayout startLayout = listView.getLoadingLayoutProxy(true, false);
        startLayout.setPullLabel("正在下拉刷新...");
        startLayout.setRefreshingLabel("正在玩命加载中...");
        startLayout.setReleaseLabel("放开以刷新");

        ILoadingLayout endLayout = listView.getLoadingLayoutProxy(false, true);
        endLayout.setPullLabel("正在上拉刷新...");
        endLayout.setRefreshingLabel("正在玩命加载中...");
        endLayout.setReleaseLabel("放开以刷新");

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDataAsyncTask(MineFootprintActivity.this, 1).execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDataAsyncTask(MineFootprintActivity.this, 2).execute();

            }
        });
    }

    /**
     * 异步下载任务
     */
    private static class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {

        private MineFootprintActivity mainActivity;
        private int judge;

        public LoadDataAsyncTask(MineFootprintActivity mainActivity, int judge) {
            this.mainActivity = mainActivity;
            this.judge = judge;
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                mainActivity.loadData(judge);
                Thread.sleep(2000);
                return "seccess";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        /**
         * 完成时的方法
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("seccess")) {
                mainActivity.everydayChoicenessAdapter.notifyDataSetChanged();
                mainActivity.listView.onRefreshComplete();//刷新完成
            }
        }
    }

    private void loadData(int judge) {
        int size = newArrivalDataBeanListLoading.size();
        if (judge == 1) {
            return;
        } else {
            if (newArrivalDataBeanList.size() >= (size + 7)) {
                for (int i = size; i < size + 7; i++) {
                    newArrivalDataBeanListLoading.add(newArrivalDataBeanList.get(i));
                }
            } else {
                for (int i = size; i < newArrivalDataBeanList.size(); i++) {
                    newArrivalDataBeanListLoading.add(newArrivalDataBeanList.get(i));
                }
            }
            return;
        }
    }

    private void initView() {
        listView = (PullToRefreshListView) findViewById(R.id.listView_everyday_choiceness);
        newArrivalDataBeanList = new ArrayList<>();
        newArrivalDataBeanListLoading = new ArrayList<>();
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("我的足迹");
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
