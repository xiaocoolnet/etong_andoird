package cn.xiaocool.android_etong.fragment.FlashSale;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class FlashSaleFourFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private ListView listView;
    private List<NewArrivalBean.NewArrivalDataBean> list;
    private FlashSaleAdapter flashSaleAdapter;


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
    }

    private void initView() {
        list = new ArrayList<>();
        listView = (ListView) getView().findViewById(R.id.list);
    }

    @Override
    public void onClick(View view) {

    }

    public void getCityList() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiStores apiStores = retrofit.create(ApiStores.class);
        Call<NewArrivalBean> call = apiStores.GetTimeGoodList("4");

        call.enqueue(new Callback<NewArrivalBean>() {
            @Override
            public void onResponse(Call<NewArrivalBean> call, Response<NewArrivalBean> response) {
                Log.e("cc", "dd");
                list.addAll(response.body().getData());
                Log.e("resultlist", list.toString());
                Log.e("getList", list.get(2).getId());
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
}