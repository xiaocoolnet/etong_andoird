package cn.xiaocool.android_etong.UI.Local;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.fragment.Local.Ktv_All_Fragment;
import cn.xiaocool.android_etong.fragment.Local.Ktv_Distence_Fragment;

/**
 * Created by 潘 on 2016/8/7.
 */
public class KtvActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private String city;
    private Ktv_All_Fragment ktv_all_fragment;
    private Ktv_Distence_Fragment ktv_distence_fragment;
    private Button[] mTabs;
    private Fragment[] fragments;
    private int index, currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ktv);
        Intent intent = new Intent();
        city = intent.getStringExtra("city");
        initView();
        initFragment();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
    }

    private void initFragment() {
        ktv_all_fragment = new Ktv_All_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("city",city);
        ktv_all_fragment.setArguments(bundle);
        ktv_distence_fragment = new Ktv_Distence_Fragment();
        fragments = new Fragment[]{ktv_all_fragment,ktv_distence_fragment};
        getFragmentManager().beginTransaction().add(R.id.fragment_container,ktv_all_fragment).show(ktv_all_fragment).commit();
        initBtn();
    }

    private void initBtn() {
        mTabs = new Button[2];
        mTabs[0] = (Button) findViewById(R.id.btn_all);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (Button) findViewById(R.id.btn_nearly);
        mTabs[1].setOnClickListener(this);
        mTabs[0].setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_all:
                index = 0;
                break;
            case R.id.btn_nearly:
                index = 1;
                break;
        }
        if (currentIndex != index) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()) {
                fragmentTransaction.add(R.id.fragment_container, fragments[index]);
            }
            fragmentTransaction.show(fragments[index]);
            fragmentTransaction.commit();
        }
        mTabs[currentIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentIndex = index;
    }

}

