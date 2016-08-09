package cn.xiaocool.android_etong.UI.Local;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.Window;

import cn.xiaocool.android_etong.R;

/**
 * Created by 潘 on 2016/8/8.
 */
public class PanicBuyingActivity extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_panic_buying);
    }

    @Override
    public void onClick(View v) {

    }
}
