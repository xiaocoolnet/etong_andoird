package cn.xiaocool.android_etong.fragment.business;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.xiaocool.android_etong.R;

/**
 * Created by 潘 on 2016/7/17.
 */
public class OrderFinishFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.business_order_finish,container,false);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
