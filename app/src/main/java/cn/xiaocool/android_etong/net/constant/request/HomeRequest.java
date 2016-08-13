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
public class HomeRequest {
    private UserInfo user;
    private Context mContext;
    private Handler handler;

    public HomeRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo(mContext);
        user.readData(mContext);
    }

    //获取首页新品上市
    public void getNewArrival(final String recommend) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = recommend;
                String result_data = NetUtil.getResponse(WebAddress.GET_NEW_ARRIVAL, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_NEW_ARRIVAL;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取首页每日好店
    public void getEveryDayShop() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&type=10";
                String result_data = NetUtil.getResponse(WebAddress.GET_HOMEPAGE_EVERY_GOODSHOP, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_HOMEPAGE_EVERY_GOODSHOP;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取首页猜你喜欢
    public void getGuessLike() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "";
                String result_data = NetUtil.getResponse(WebAddress.GET_GUESS_LIKE, data);
                Log.e("accept like success",result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_GUESS_LIKE;
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