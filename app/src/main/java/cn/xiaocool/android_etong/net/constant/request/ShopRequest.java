package cn.xiaocool.android_etong.net.constant.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/7/20.
 */
public class ShopRequest {
    private UserInfo user;
    private Context mContext;
    private Handler handler;

    public ShopRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo(mContext);
        user.readData(mContext);
    }

    //收藏宝贝
    public void likeGood(final String goodId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&goodsid=" + goodId + "&type=1";
                Log.e("user id is ",user.getUserId());
                String result_data = NetUtil.getResponse(WebAddress.LIKE_GOOD, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.LIKE_GOOD;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //取消收藏宝贝
    public void cancelLike(final String goodId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&goodsid=" + goodId + "&type=" + "1";
                String result_data = NetUtil.getResponse(WebAddress.CANCLE_LIKE_GOOD, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CANCLE_LIKE_GOOD;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

}