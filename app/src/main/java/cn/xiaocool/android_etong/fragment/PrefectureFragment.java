package cn.xiaocool.android_etong.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Local.PublishInformationActivity;

import static cn.xiaocool.android_etong.util.StatusBarHeightUtils.getStatusBarHeight;

/**
 * Created by 潘 on 2016/6/12.
 */
public class PrefectureFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private RelativeLayout img_edit;
    private RelativeLayout ry_line;
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
        context = getActivity();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置状态栏高度
        ry_line = (RelativeLayout)getView().findViewById(R.id.lin);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ry_line.getLayoutParams();
        linearParams.height=getStatusBarHeight(context);
        ry_line.setLayoutParams(linearParams);
//        StatusBarCompat.compat(getActivity(), getResources().getColor(R.color.main_color_red));
        prefectureProductFragment = new  PrefectureProductFragment();
        prefectureMyFragment = new PrefectureMyFragment();
        fragments = new Fragment[]{prefectureMyFragment, prefectureProductFragment};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getChildFragmentManager().beginTransaction().add(R.id.fragment_container,prefectureMyFragment).commit();
        }
        initview();
    }

    private void initview() {
        img_edit = (RelativeLayout) getView().findViewById(R.id.img_edit);
        img_edit.setOnClickListener(this);
        mTabs = new Button[2];
        mTabs[0]  = (Button)getView().findViewById(R.id.btn_prefecture_my);
        mTabs[0].setOnClickListener(this);
        mTabs[1]  = (Button)getView().findViewById(R.id.btn_prefecture_product);
        mTabs[1].setOnClickListener(this);
        // 把第一个tab设为选中状态
        mTabs[0].setSelected(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_prefecture_my:
                index = 0;
                break;
            case R.id.btn_prefecture_product:
                index = 1;
                break;
            case R.id.img_edit:
                startActivity(new Intent(context, PublishInformationActivity.class));
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
