package cn.xiaocool.android_etong.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.GetBBSListAdapter;
import cn.xiaocool.android_etong.bean.CityBBSBean;
import cn.xiaocool.android_etong.bean.CityBBSBean.DataBean;
import cn.xiaocool.android_etong.dao.ApiStores;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static cn.xiaocool.android_etong.net.constant.NetBaseConstant.PREFIX;

/**
 * Created by 潘 on 2016/6/14.
 */

public class PrefectureMyFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private List<DataBean> list;
    //    private List<CityBBSBean> list;
    private GetBBSListAdapter getBBSListAdapter;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_prefecturemy, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getCityList();
    }

    private void setAdapter() {
        if (getBBSListAdapter != null) {
            getBBSListAdapter.notifyDataSetChanged();
        } else {
            getBBSListAdapter = new GetBBSListAdapter(context, list);
            listView.setAdapter(getBBSListAdapter);
        }
    }

    private void initView() {
        listView = (ListView) getView().findViewById(R.id.get_city_bbs_listView);
    }

    public void getCityList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiStores apiStores = retrofit.create(ApiStores.class);
        Call<CityBBSBean> call = apiStores.getBBSList("1");

        call.enqueue(new Callback<CityBBSBean>() {
            @Override
            public void onResponse(Call<CityBBSBean> call, Response<CityBBSBean> response) {
                Log.e("cc", "dd");
                list = response.body().getData();
                Log.e("resultlist", list.toString());
                Log.e("getList", list.get(2).getContent());
                setAdapter();//异步请求结束后，设置适配器
            }

            @Override
            public void onFailure(Call<CityBBSBean> call, Throwable t) {
                Log.e("err", t.toString());
            }


        });
    }

    @Override
    public void onResume() {
        getCityList();
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }

}
