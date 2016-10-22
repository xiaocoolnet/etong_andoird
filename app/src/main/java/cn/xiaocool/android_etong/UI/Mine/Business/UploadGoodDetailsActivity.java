package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xiaocool.android_etong.R;

/**
 * Created by wzh on 2016/10/22.
 */

public class UploadGoodDetailsActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.top_title_text)
    TextView topTitleText;
    @BindView(R.id.upload_good_details_et)
    EditText uploadGoodDetailsEt;
    @BindView(R.id.upload_good_btn)
    RelativeLayout uploadGoodBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_good_details);
        ButterKnife.bind(this);
        topTitleText.setText("输入宝贝详情");
        Intent intent = getIntent();
        String content = intent.getStringExtra("tv_content");
        uploadGoodDetailsEt.setText(content);
        uploadGoodDetailsEt.setSelection(content.length());//光标置后
    }



    @OnClick({R.id.btn_back, R.id.upload_good_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                Intent intent0=new Intent();
                intent0.putExtra("good_details", "");
                setResult(1000, intent0);
                finish();
                break;
            case R.id.upload_good_btn:
                Intent intent=new Intent();
                intent.putExtra("good_details", uploadGoodDetailsEt.getText().toString());//携带数据返回
                setResult(1000, intent);
                finish();
                break;
        }
    }
}
