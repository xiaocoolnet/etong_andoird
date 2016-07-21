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
public class MineRequest {
    private UserInfo user;
    private Context mContext;
    private Handler handler;

    public MineRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo(mContext);
        user.readData(mContext);
    }

    //修改并上传宝贝详情信息
    public void changeGoodIntro(final String goodId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&id=" + goodId;
                String result_data = NetUtil.getResponse(WebAddress.CHANGE_GOOD_INTRO, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CHANGE_GOOD_INTRO;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
 //修改单条宝贝信息
 public void changeGoodIntroItem(final String suffix, final String infor) {
     new Thread() {
         Message msg = Message.obtain();

         public void run() {
             String data = suffix + infor;
             String result_data = NetUtil.getResponse(WebAddress.CHANGE_GOOD_INTRO_ITEM, data);
             try {
                 JSONObject obj = new JSONObject(result_data);
                 msg.what = CommunalInterfaces.CHANGE_GOOD_INTRO_ITEM;
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