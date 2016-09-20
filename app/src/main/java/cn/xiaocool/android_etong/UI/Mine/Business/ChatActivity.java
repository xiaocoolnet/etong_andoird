package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import cn.xiaocool.android_etong.R;

/**
 * Created by 潘 on 2016/9/13.
 */
public class ChatActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
    }

    @Override
    public void onClick(View v) {

    }
}
