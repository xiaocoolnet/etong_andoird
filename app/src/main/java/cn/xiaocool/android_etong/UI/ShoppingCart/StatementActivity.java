package cn.xiaocool.android_etong.UI.ShoppingCart;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.StatementAdapter;
import cn.xiaocool.android_etong.bean.Shop.ShoppingCart_StoreName;

/**
 * Created by 潘 on 2016/8/25.
 */
public class StatementActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private double amount;
    private TextView tv_goods_price_total,tx_comfirm_order;
    private ListView list_statement;
    private List<ShoppingCart_StoreName.DataBean> dataBeans;
    private StatementAdapter statementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_statement);
        context = this;
        dataBeans = (List<ShoppingCart_StoreName.DataBean>) getIntent().getSerializableExtra("databeans");
        amount = getIntent().getDoubleExtra("amount",1);
        Log.e("amount", String.valueOf(amount));
        initView();
        statementAdapter = new StatementAdapter(context,dataBeans);
        list_statement.setAdapter(statementAdapter);
        fixListViewHeight(list_statement);
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        tv_goods_price_total = (TextView) findViewById(R.id.tv_goods_price_total);
        tv_goods_price_total.setText(String.valueOf(amount));
        tx_comfirm_order = (TextView) findViewById(R.id.tx_comfirm_order);
        tx_comfirm_order.setOnClickListener(this);
        list_statement = (ListView) findViewById(R.id.list_statement);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tx_comfirm_order:
                dataBeans.clear();
                statementAdapter = new StatementAdapter(context,dataBeans);
                list_statement.setAdapter(statementAdapter);
                fixListViewHeight(list_statement);
                tv_goods_price_total.setText("0");
                Toast.makeText(context,"结算成功",Toast.LENGTH_SHORT).show();
                break;
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
