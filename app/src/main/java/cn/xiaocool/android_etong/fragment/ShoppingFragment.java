package cn.xiaocool.android_etong.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
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

import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsDetailActivity;
import cn.xiaocool.android_etong.adapter.StoreAdapter;
import cn.xiaocool.android_etong.bean.Shop.ShoppingCart_StoreName;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

import static cn.xiaocool.android_etong.util.StatusBarHeightUtils.getStatusBarHeight;

/**
 * Created by 潘 on 2016/6/12.
 */
public class ShoppingFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private TextView tx_shopping_price;
    private RelativeLayout ry_line;
    private CheckBox cb_all_select;
    private ListView list_Shopping_Cart;
    private StoreAdapter storeAdapter;
    private List<ShoppingCart_StoreName.DataBean> dataBeans;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GET_SHOPPING_CART:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            dataBeans.clear();
                            Log.e("success", "getshoppingcart");
                            JSONArray jsonarray = jsonObject.getJSONArray("data");
                            int length = jsonarray.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject json = (JSONObject) jsonarray.get(i);
                                ShoppingCart_StoreName.DataBean store = new ShoppingCart_StoreName.DataBean();
                                store.setShopname(json.getString("shopname"));
                                store.setShopid(json.getString("shopid"));
                                JSONArray jsonArray = json.getJSONArray("goodslist");
                                List<ShoppingCart_StoreName.DataBean.GoodslistBean> goodses = new ArrayList<>();
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject jsonObject2 = (JSONObject) jsonArray.get(j);
                                    ShoppingCart_StoreName.DataBean.GoodslistBean good = new ShoppingCart_StoreName.DataBean.GoodslistBean();
                                    good.setId(jsonObject2.getString("id"));
                                    good.setGid(jsonObject2.getString("gid"));
                                    good.setGoodsname(jsonObject2.getString("goodsname"));
                                    good.setPicture(jsonObject2.getString("picture"));
                                    good.setDescription(jsonObject2.getString("description"));
                                    good.setOprice(jsonObject2.getString("oprice"));
                                    good.setPrice(jsonObject2.getString("price"));
                                    good.setNumber(jsonObject2.getString("number"));
                                    good.setTime(jsonObject2.getString("time"));
                                    goodses.add(good);
                                }
                                store.setGoodslist(goodses);
                                dataBeans.add(store);
                            }
                                storeAdapter = new StoreAdapter(ShoppingFragment.this, dataBeans);
                                list_Shopping_Cart.setAdapter(storeAdapter);
                                fixListViewHeight(list_Shopping_Cart);
                        } else {
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.EDIT_SHOPPING_CART:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            Log.e("success", "edit_shoppingcart");
                        } else {
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.DELETE_SHOPPING_CART:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            Log.e("success", "delete_shoppingcart");
                        } else {
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
//                case CommunalInterfaces.UPDATA_SHOPPING_CART:
//                    new MainRequest(context,handler).GetShoppingCart();
//                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);
        context = getActivity();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置状态栏高度
        ry_line = (RelativeLayout) getView().findViewById(R.id.lin2);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ry_line.getLayoutParams();
        linearParams.height = getStatusBarHeight(context);
        ry_line.setLayoutParams(linearParams);
//        MyApp myApp = (MyApp)getActivity().getApplication();
//        myApp.setHandler(handler);
        initView();
        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).GetShoppingCart();
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        dataBeans = new ArrayList<>();
        list_Shopping_Cart = (ListView) getView().findViewById(R.id.list_shopping_cart);
        tx_shopping_price = (TextView) getView().findViewById(R.id.tx_shopping_price);
        cb_all_select = (CheckBox) getView().findViewById(R.id.cb_all_select);
        cb_all_select.setOnClickListener(this);
        initShoppingCart();
    }

    private void initShoppingCart() {
        IntentFilter filter = new IntentFilter(GoodsDetailActivity.action);
        context.registerReceiver(broadcastReceiver, filter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Log.e("data=", intent.getExtras().getString("data"));
            if (NetUtil.isConnnected(context)) {
                new MainRequest(context, handler).GetShoppingCart();
            } else {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void onDestroy() {
        context.unregisterReceiver(broadcastReceiver);
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_all_select:
                boolean flag = cb_all_select.isChecked();
                for (int i = 0; i < storeAdapter.getSelect().size(); i++) {
                    storeAdapter.getSelect().set(i, flag);
                    for (int j = 0; j < storeAdapter.getPAdapter(i).getSelect().size(); j++) {
                        storeAdapter.getPAdapter(i).getSelect().set(j, flag);
                    }
                }
                updateAmount();
                storeAdapter.notifyDataSetChanged();
                break;
        }
    }

    public void checkAll(boolean checked) {
        cb_all_select.setChecked(checked);
    }

    public void updateAmount() {
        double amount = 0;
        tx_shopping_price.setText("￥");
        for (int i = 0; i < dataBeans.size(); i++) {
            for (int j = 0; j < dataBeans.get(i).getGoodslist().size(); j++) {
                if (storeAdapter.getPAdapter(i).getSelect().get(j)) {
                    amount += Integer.valueOf(dataBeans.get(i).getGoodslist().get(j).getPrice())
                            * Integer.valueOf(dataBeans.get(i).getGoodslist().get(j).getNumber());
                }
            }
        }
        if (amount != 0) {
            tx_shopping_price.setText("￥" + amount);
        }
    }

    public void setGoodsNumber(int storePosition, int goodsPosition, String number) {
        dataBeans.get(storePosition).getGoodslist().get(goodsPosition).setNumber(number);
        Log.e("success", number);
    }

    public void removePosition(int storePosition){
        dataBeans.remove(storePosition);
        storeAdapter = new StoreAdapter(ShoppingFragment.this, dataBeans);
        list_Shopping_Cart.setAdapter(storeAdapter);
        fixListViewHeight(list_Shopping_Cart);
    }

    public void setUpdate() {
        for (int i = 0; i < dataBeans.size(); i++) {
            for (int j = 0; j < dataBeans.get(i).getGoodslist().size(); j++) {
                new MainRequest(context, handler).EditShoppingCart(dataBeans.get(i).getGoodslist().get(j).getGid(), dataBeans.get(i).getGoodslist().get(j).getNumber());
            }
        }
    }

    public void DeleteDate(String goodsid) {
        if (NetUtil.isConnnected(context)) {
            Log.e("ready", "delete_shopping_cart");
            new MainRequest(context, handler).DeleteShoppingCart(goodsid);
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    public void fixListViewHeight(ListView listView) {

        // 如果没有设置数据适配器，则ListView没有子项，返回。

        ListAdapter listAdapter = listView.getAdapter();

        int totalHeight = 0;

        if (listAdapter == null) {

            return;

        }

        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {

            View listViewItem = listAdapter.getView(index, null, listView);

            // 计算子项View 的宽高

            listViewItem.measure(0, 0);

            // 计算所有子项的高度和

            totalHeight += listViewItem.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        // listView.getDividerHeight()获取子项间分隔符的高度

        // params.height设置ListView完全显示需要的高度

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
    }

}
