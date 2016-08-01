package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.AddGoodAttributeAdapter;
import cn.xiaocool.android_etong.bean.UploadGoodSanndard.UploadStandardBean;

/**
 * Created by wzh on 2016/7/31.
 */
public class AddGoodAttributeActivity extends Activity {
    private AddGoodAttributeAdapter addGoodAttributeAdapter;
    private GridView gridView;
    private TextView tvTitle;
    private RelativeLayout rlBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_good_attribute);
        initView();
        List<UploadStandardBean.DataBean.PlistBean> list =
                (ArrayList<UploadStandardBean.DataBean.PlistBean>) getIntent().getSerializableExtra("list");
        addGoodAttributeAdapter = new AddGoodAttributeAdapter(this, list);
        gridView.setAdapter(addGoodAttributeAdapter);
    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.gridView_add_good_attribute);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("选择规格");
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_back) {
                    finish();
                }
            }
        });
    }
}
