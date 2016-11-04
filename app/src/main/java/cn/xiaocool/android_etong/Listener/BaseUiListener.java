package cn.xiaocool.android_etong.Listener;

import android.util.Log;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Created by 潘 on 2016/10/26.
 */

public class BaseUiListener implements IUiListener {

    public void onComplete(JSONObject response) {
        doComplete(response);
    }

    protected void doComplete(JSONObject values) {
    }

    @Override
    public void onComplete(Object response) {
    }

    @Override
    public void onError(UiError e) {
        Log.e("onError:", "code:" + e.errorCode + ", msg:"
                + e.errorMessage + ", detail:" + e.errorDetail);
    }
    @Override
    public void onCancel() {
        Log.e("onCancel", "");
    }
}
