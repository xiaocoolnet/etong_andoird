package cn.xiaocool.android_etong.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.xiaocool.android_etong.R;

/**
 * Created by 潘 on 2016/6/12.
 */
public class HomepageFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage,container,false);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
