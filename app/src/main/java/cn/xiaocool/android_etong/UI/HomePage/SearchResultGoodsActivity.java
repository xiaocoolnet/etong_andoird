package cn.xiaocool.android_etong.UI.HomePage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.Local;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.SearchResultGoodsAdapter;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/8/13.
 */
public class SearchResultGoodsActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private TextView tv_search;
    private ListView list_shop;
    private SearchResultGoodsAdapter searchResultGoodsAdapter;
    private String search_content,city;
    private List<Local> locals;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.SEARCH_GOODS:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        if (state.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Log.e("success", "加载数据");
                            for (int i = 0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Local local = new Local();
                                local.setId(jsonObject1.getString("id"));
                                local.setShopid(jsonObject1.getString("shopid"));
                                local.setGoodsname(jsonObject1.getString("goodsname"));
                                local.setPicture(jsonObject1.getString("picture"));
                                local.setDescription(jsonObject1.getString("description"));
                                local.setPrice(jsonObject1.getString("price"));
                                local.setOprice(jsonObject1.getString("oprice"));
                                local.setFreight(jsonObject1.getString("freight"));
                                JSONObject jsonObject2 = jsonObject1.getJSONObject("shop_name");
                                local.setShopname(jsonObject2.getString("shopname"));
                                locals.add(local);
                                Log.e("succees", "商品数据加载");
                            }if (searchResultGoodsAdapter!=null){
                                Log.e("success","设置适配器");
                                searchResultGoodsAdapter.notifyDataSetChanged();
                            }else {
                                Log.e("success","设置适配器");
                                searchResultGoodsAdapter = new SearchResultGoodsAdapter(context,locals);
                                list_shop.setAdapter(searchResultGoodsAdapter);
                                setListViewHeightBasedOnChildren(list_shop);
                            }
                        }else{
//                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private TextView tvSort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_result_shop);
        Intent intent = getIntent();
//        String searchName = getIntent().getStringExtra("searchName");
        search_content = intent.getStringExtra("search_content");
        city = intent.getStringExtra("city");
        Log.e("content=", search_content);
        Log.e("city=",city);
        context = this;
        initView();
        if (city.equals("homepage")){
            if (NetUtil.isConnnected(context)){
                new MainRequest(context,handler).SearchGoods(search_content);
            }else {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        }else {
            if (NetUtil.isConnnected(context)){
                new MainRequest(context,handler).SearchGoods(search_content,city);
            }else {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void initView() {
        locals = new ArrayList<>();
        rl_back = (RelativeLayout)findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        list_shop = (ListView) findViewById(R.id.list_shop);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setText(search_content);
        tv_search.setOnClickListener(this);
        tvSort = (TextView) findViewById(R.id.search_tv_sort_btn);
        tvSort.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_search:
                Intent intent = new Intent();
                intent.setClass(context, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.search_tv_sort_btn:
                showSortPopWindow();
                break;
        }
    }

    private void showSortPopWindow() {
//        View contentView = LayoutInflater.from(this,).inflate(R.layout.,null);//写到这了
    }

    /*
   解决scrollview下listview显示不全
 */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}
