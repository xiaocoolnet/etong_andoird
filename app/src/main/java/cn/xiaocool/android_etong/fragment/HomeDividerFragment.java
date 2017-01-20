package cn.xiaocool.android_etong.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.xiaocool.android_etong.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeDividerFragment extends Fragment {


    public HomeDividerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_divider, container, false);
    }

}
