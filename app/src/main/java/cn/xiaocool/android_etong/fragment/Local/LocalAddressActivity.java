package cn.xiaocool.android_etong.fragment.Local;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.util.KeyBoardUtils;

/**
 * Created by 潘 on 2016/8/17.
 */
public class LocalAddressActivity extends Activity implements View.OnClickListener {
    private Context context;
    private String city;
    private TextView tv_back;
    private EditText et_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_local_address);
        context = this;
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        initView();
    }

    private void initView() {
        et_address = (EditText) findViewById(R.id.et_address);
        et_address.setText(city);
        // 切换后将EditText光标置于末尾
        CharSequence charSequence = et_address.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
        KeyBoardUtils.showKeyBoardByTime(et_address, 300);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                Intent intent = new Intent();
                intent.putExtra("city", et_address.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.putExtra("city", et_address.getText().toString());
            setResult(RESULT_OK,intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
