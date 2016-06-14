package cn.xiaocool.android_etong.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.xiaocool.android_etong.R;

/**
 * Created by 潘 on 2016/6/12.
 */
public class PrefectureFragment extends Fragment implements View.OnClickListener {
    private Button[] mTabs;
    private PrefectureMyFragment prefectureMyFragment;
    private PrefectureProductFragment prefectureProductFragment;
    private Fragment[] fragments;
    private int index;
    // 当前fragment的index
    private int currentTabIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prefecture,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prefectureProductFragment = new  PrefectureProductFragment();
        prefectureMyFragment = new PrefectureMyFragment();
        fragments = new Fragment[]{prefectureMyFragment, prefectureProductFragment};
        getChildFragmentManager().beginTransaction().add(R.id.fragment_container,prefectureMyFragment).commit();
        initview();
    }

    private void initview() {
        mTabs = new Button[2];
        mTabs[0]  = (Button)getView().findViewById(R.id.btn_prefecture_my);
        mTabs[0].setOnClickListener(this);
        mTabs[1]  = (Button)getView().findViewById(R.id.btn_prefecture_product);
        mTabs[1].setOnClickListener(this);
        // 把第一个tab设为选中状态
        mTabs[0].setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_prefecture_my:
                index = 0;
                break;
            case R.id.btn_prefecture_product:
                index = 1;
                break;
        }
        if (currentTabIndex != index) {

            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                transaction.add(R.id.fragment_container, fragments[index]);
            }
            transaction.show(fragments[index]);
            transaction.commit();
        }

        mTabs[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }
}
