package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.fragment.business.OrderAllFragment;
import cn.xiaocool.android_etong.fragment.business.OrderFinishFragment;
import cn.xiaocool.android_etong.fragment.business.OrderSendGoodsFragment;
import cn.xiaocool.android_etong.fragment.business.OrderSignFragment;

/**
 * Created by 潘 on 2016/7/17.
 */
public class OrderManageActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private Button[] mTabs;
    private OrderAllFragment orderAllFragment;
    private OrderSendGoodsFragment orderSendGoodsFragment;
    private OrderSignFragment orderSignFragment;
    private OrderFinishFragment orderFinishFragment;
    private Fragment[] fragments;
    private FragmentManager fragmentManager;
    private int index, currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_manage);
        context = this;
        initfragment();
        initdata();
    }

    private void initdata() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
    }

    private void initfragment() {
        orderAllFragment = new OrderAllFragment();
        orderSendGoodsFragment = new OrderSendGoodsFragment();
        orderSignFragment = new OrderSignFragment();
        orderFinishFragment = new OrderFinishFragment();

        fragments = new Fragment[]{orderAllFragment,orderSendGoodsFragment,orderSignFragment,orderFinishFragment};
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,orderAllFragment);
        fragmentTransaction.commit();
        initview();
    }

    private void initview() {
        mTabs = new Button[4];
        mTabs[0] = (Button) findViewById(R.id.btn_quanbu);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (Button) findViewById(R.id.btn_daifahuo);
        mTabs[1].setOnClickListener(this);
        mTabs[2] = (Button) findViewById(R.id.btn_daiqianshou);
        mTabs[2].setOnClickListener(this);
        mTabs[3] = (Button) findViewById(R.id.btn_yiwancheng);
        mTabs[3].setOnClickListener(this);
        mTabs[0].setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_quanbu:
                index=0;
                break;
            case R.id.btn_daifahuo:
                index=1;
                break;
            case R.id.btn_daiqianshou:
                index=2;
                break;
            case R.id.btn_yiwancheng:
                index=3;
                break;
            case R.id.rl_back:
                finish();
                break;
        }
        if (currentIndex!=index){
            FragmentTransaction fragmenttransaction = fragmentManager.beginTransaction();
            fragmenttransaction.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()){
                fragmenttransaction.add(R.id.fragment_container,fragments[index]);
            }
            fragmenttransaction.show(fragments[index]);
            fragmenttransaction.commit();
        }
        mTabs[currentIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentIndex = index;
    }
}
