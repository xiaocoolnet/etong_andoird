package cn.xiaocool.android_etong.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.xiaocool.android_etong.UI.MainActivity;


/**
 * Created by Administrator on 2016/9/8.
 */
public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String type = bundle.getString(JPushInterface.EXTRA_EXTRA);

        if (type==null)return;
        String str = "";
        try {
            JSONObject jsonObject = new JSONObject(type);
            str = jsonObject.getString("type");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException" + type);
            e.printStackTrace();
        }


        Log.i(TAG, "[PushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));




        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.i(TAG, "[PushReceiver] 接收Registeration Id : " + regId);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.i(TAG, "[PushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        }else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.i(TAG, "[PushReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.i(TAG, "[PushReceiver] 接收到推送下来的通知的ID: " + notifactionId);


            Intent pointIntent = new Intent();
            pointIntent.setAction("com.USER_ACTION");
//            pointIntent.putExtra("loginOther",loginOther);
            context.sendBroadcast(pointIntent);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.i(TAG, "[PushReceiver] 用户点击打开了通知");


                if(true) {
                    Intent i = new Intent(context, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    context.startActivity(i);
                }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.i(TAG, "[PushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.i(TAG, "[PushReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.i(TAG, "[PushReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            }
            else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

}
