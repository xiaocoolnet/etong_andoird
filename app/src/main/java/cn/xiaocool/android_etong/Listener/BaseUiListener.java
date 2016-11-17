package cn.xiaocool.android_etong.Listener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import cn.xiaocool.android_etong.UI.MainActivity;

/**
 * Created by 潘 on 2016/10/26.
 */

public class BaseUiListener implements IUiListener {
    private Context context;

    public BaseUiListener(Context context){
        this.context = context;
    }

    protected void doComplete(JSONObject values) {
        Log.e("response=",values.toString());
        context.startActivity(new Intent(context, MainActivity.class));

    }

    @Override
    public void onComplete(Object response) {
        doComplete((JSONObject) response);
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
