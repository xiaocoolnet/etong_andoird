package cn.xiaocool.android_etong.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XScrollView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.HomePage.GoodsTabActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsDetailActivity;
import cn.xiaocool.android_etong.adapter.DivideListAdapter;
import cn.xiaocool.android_etong.adapter.TopDivideAdapter;
import cn.xiaocool.android_etong.bean.HomePage.MenuTypeList;
import cn.xiaocool.android_etong.bean.HomePage.TypeGoodsList;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.HomeRequest;
import cn.xiaocool.android_etong.util.NoScrollGridView;
import cn.xiaocool.android_etong.util.SPUtils;
import cn.xiaocool.android_etong.view.CustomerFooter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeDividerFragment extends Fragment {


    @BindView(R.id.top_divide)
    NoScrollGridView topDivide;
    @BindView(R.id.table_divide)
    NoScrollGridView tableDivide;
    @BindView(R.id.xscrollview)
    XScrollView xscrollview;
    @BindView(R.id.xrefresh_view)
    XRefreshView xrefreshView;

    private Context context;
    public List<MenuTypeList.ChildlistBeanX> childlistBeanXs = new ArrayList<>();
    public String type = "0";
    private List<TypeGoodsList> typeGoodsLists;
    private DivideListAdapter adapter;
    private View rootView;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GET_MENU:
                    JSONObject jsonObject2 = (JSONObject) msg.obj;
                    try {
                        if (jsonObject2.getString("status").equals("success")) {
                            typeGoodsLists.clear();
                            typeGoodsLists.addAll(getBeanFromJson(jsonObject2.toString()));
                            setAdapter();
                        }else {
                            typeGoodsLists.addAll(getBeanFromJson((String) SPUtils.get(context, WebAddress.GET_GOODS_LIST,"")));
                            setAdapter();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    private void setAdapter() {

//        if (adapter == null) {
            adapter = new DivideListAdapter(context,typeGoodsLists);
            tableDivide.setAdapter(adapter);
//        }else {
//            adapter.notifyDataSetChanged();
//        }
    }

    private List<TypeGoodsList> getBeanFromJson(String result) {
        String data = "";
        try {
            JSONObject json = new JSONObject(result);
            data = json.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(data, new TypeToken<List<TypeGoodsList>>() {
        }.getType());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home_divider,container, false);
        }
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rootView != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeGoodsLists = new ArrayList<>();
    }

    private void setView() {
        xscrollview.setOnScrollListener(new XScrollView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(ScrollView view, int scrollState, boolean arriveBottom) {
            }

            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
            }
        });
        xrefreshView.setAutoRefresh(false);
        xrefreshView.setPullLoadEnable(true);
        xrefreshView.setPinnedTime(1000);
        xrefreshView.setAutoLoadMore(false);
//		outView.setSilenceLoadMore();
        xrefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                getView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xrefreshView.stopLoadMore();
                    }
                }, 2000);
            }

            @Override
            public void onRefresh() {
                new HomeRequest(context,handler).GetTypeGoodList(type);
                getView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xrefreshView.stopRefresh();
                    }
                }, 2000);
            }
        });
        xrefreshView.setCustomFooterView(new CustomerFooter(context));
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        setView();
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getData() {
        //test
        TopDivideAdapter topDivideAdapter = new TopDivideAdapter(context, R.layout.item_top_divide,childlistBeanXs);
        if (childlistBeanXs != null){
            for (int i = 0; i < childlistBeanXs.size(); i++) {
                topDivideAdapter.add(childlistBeanXs.get(i).getName());

            }
            topDivide.setAdapter(topDivideAdapter);

        }


        topDivide.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, GoodsTabActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("menu", (Serializable) childlistBeanXs.get(i).getChildlist());
                intent.putExtras(bundle);
                intent.putExtra("title",childlistBeanXs.get(i).getName());
                startActivity(intent);
            }
        });


        tableDivide.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TypeGoodsList bean = typeGoodsLists.get(i);
                Intent intent = new Intent();
                intent.setClass(context, GoodsDetailActivity.class);
                intent.putExtra("id", bean.getId());//传出goodId
                intent.putExtra("artno", bean.getArtno());
                intent.putExtra("shopid", bean.getShopid());//传出shopid
                intent.putExtra("brand", bean.getBrand());
                intent.putExtra("goodsname", bean.getGoodsname());
                intent.putExtra("adtitle", bean.getAdtitle());
                intent.putExtra("oprice", bean.getOprice());
                intent.putExtra("price", bean.getPrice());//传出price
                intent.putExtra("unit", bean.getUnit());
                intent.putExtra("description", bean.getDescription());
                intent.putExtra("pic", bean.getPicture());//传出pic
                intent.putExtra("showid", bean.getShowid());
                intent.putExtra("address", bean.getAddress());
                intent.putExtra("freight", bean.getFreight());
                intent.putExtra("pays", bean.getPays());
                intent.putExtra("racking", bean.getRacking());
                intent.putExtra("recommend", bean.getRecommend());
                intent.putExtra("shopname", bean.getShopname());//店铺名字
                intent.putExtra("sales", bean.getSales());
                intent.putExtra("paynum", bean.getPaynum());
                context.startActivity(intent);
            }
        });



        handler.postDelayed(LOAD_DATA,500);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(!isVisibleToUser)handler.removeCallbacks(LOAD_DATA);
    }

    private Runnable LOAD_DATA = new Runnable() {
        @Override
        public void run() {
            //在这里数据内容加载到Fragment上
            new HomeRequest(context,handler).GetTypeGoodList(type);
        }
    };
}
