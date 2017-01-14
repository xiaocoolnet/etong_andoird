package cn.xiaocool.android_etong.fragment.FlashSale;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.FlashSaleAdapter;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.dao.ApiStores;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static cn.xiaocool.android_etong.net.constant.NetBaseConstant.PREFIX;

/**
 * Created by hzh on 2017/1/10.
 */

public class FlashSaleThreeFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private PullToRefreshListView listView;
    private List<NewArrivalBean.NewArrivalDataBean> list;
    private FlashSaleAdapter flashSaleAdapter;
    private int beginid = 0;
    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x2333:
                    setAdapter();
                    break;
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashsale,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getCityList();
        setrefrseh();
    }

    private void initView() {
        list = new ArrayList<>();
        listView = (PullToRefreshListView) getView().findViewById(R.id.list);
    }


    public void getCityList() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiStores apiStores = retrofit.create(ApiStores.class);
        Call<NewArrivalBean> call = apiStores.GetTimeGoodList("1", String.valueOf(beginid));

        call.enqueue(new Callback<NewArrivalBean>() {
            @Override
            public void onResponse(Call<NewArrivalBean> call, Response<NewArrivalBean> response) {
                Log.e("cc", "dd");
                list.addAll(response.body().getData());
                Log.e("resultlist", list.toString());
                Log.e("getList", list.get(2).getId());
                beginid = Integer.parseInt(list.get(list.size()-1).getId());
                setAdapter();  //异步请求结束后，设置适配器
            }

            @Override
            public void onFailure(Call<NewArrivalBean> call, Throwable t) {
                Log.e("err", t.toString());
            }

        });

        return;
    }

    private boolean setAdapter(){
        if (flashSaleAdapter != null) {
            flashSaleAdapter.notifyDataSetChanged();
        } else {
            flashSaleAdapter = new FlashSaleAdapter(context, list);
            listView.setAdapter(flashSaleAdapter);
        }
        return  true;
    }

    private void setrefrseh() {
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
                new LoadDataAsyncTask(context, 1).execute();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDataAsyncTask(context, 2).execute();

            }
        });

    }

    /**
     * 异步下载任务
     */
    private class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {

        private Context mainActivity;
        private int judge;

        public LoadDataAsyncTask(Context mainActivity, int judge) {
            this.mainActivity = mainActivity;
            this.judge = judge;
        }

        @Override
        protected String doInBackground(Void... params){
            try {
                if (judge==1) {
                    handle.sendEmptyMessage(0x2333);
                }else {
                    getCityList();
                }
                Log.e("refesh","refesh2");
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
                Log.e("refesh","refesh3");
                listView.onRefreshComplete();//刷新完成
                return;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }
}