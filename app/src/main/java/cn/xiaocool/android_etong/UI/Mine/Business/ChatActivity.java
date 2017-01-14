package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.ChatAdapter;
import cn.xiaocool.android_etong.bean.Mine.PersonChat;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

import static cn.xiaocool.android_etong.util.TimeToolUtils.fromateTimeShowByRule;

/**
 * Created by 潘 on 2016/9/13.
 */
public class ChatActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private Context context;
    private String shopid,shop_uid,shop_photo,shopname;
    private ChatAdapter chatAdapter;
    private TextView tv_receive_name;
    private EditText et_chat_message;
    private Button btn_chat_message_send;
    private UserInfo user;
    /**
     * 声明ListView
     */
    private ListView lv_chat_dialog;
    /**
     * 集合
     */
    private List<PersonChat> personChats = new ArrayList<PersonChat>();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    /**
                     * ListView条目控制在最后一行
                     */
                    lv_chat_dialog.setSelection(personChats.size());
                    break;
                case CommunalInterfaces.SendChatData:
                    JSONObject json = (JSONObject) msg.obj;
                    try {
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            Log.e("success", "chat");
                            JSONObject object = json.getJSONObject("data");
                            PersonChat personChat = new PersonChat();
                            personChat.setChatTime(fromateTimeShowByRule(object.getLong("create_time")));
                            personChat.setPhoto(user.getUserImg());
                            //代表自己发送
                            personChat.setMeSend(true);
                            //得到发送内容
                            personChat.setChatMessage(et_chat_message.getText().toString());
                            //加入集合
                            personChats.add(personChat);
                            //清空输入框
                            et_chat_message.setText("");
                            //刷新ListView
                            chatAdapter.notifyDataSetChanged();
                            handler.sendEmptyMessage(1);
                        } else {
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.xcGetChatData:
                    JSONObject json1 = (JSONObject) msg.obj;
                    try {
                        String status = json1.getString("status");
                        String data = json1.getString("data");
                        if (status.equals("success")) {
                            Log.e("success", "chat");
                            JSONArray object = json1.getJSONArray("data");
                            for (int i = 0 ;i<object.length();i++){
                                JSONObject jsonobj = object.getJSONObject(i);
                                if (jsonobj.getString("send_uid").equals(user.getUserId())){
                                    PersonChat personChat = new PersonChat();
                                    personChat.setChatTime(fromateTimeShowByRule(jsonobj.getLong("create_time")));
                                    personChat.setPhoto(user.getUserImg());
                                    //代表自己发送
                                    personChat.setMeSend(true);
                                    //得到发送内容
                                    personChat.setChatMessage(jsonobj.getString("content"));
                                    //加入集合
                                    personChats.add(personChat);
                                }else {
                                    PersonChat personChat = new PersonChat();
                                    personChat.setChatTime(fromateTimeShowByRule(jsonobj.getLong("create_time")));
                                    personChat.setPhoto(jsonobj.getString("receive_face"));
                                    //代表自己发送
                                    personChat.setMeSend(false);
                                    //得到发送内容
                                    personChat.setChatMessage(jsonobj.getString("content"));
                                    //加入集合
                                    personChats.add(personChat);
                                }
                            }
//                            /**
//                             * 虚拟1条发送方的消息
//                             */
//                            for (int i = 0; i <1; i++) {
//                                PersonChat personChat = new PersonChat();
//                                personChat.setMeSend(false);
//                                personChat.setChatMessage("欢迎光临本店铺。");
//                                personChat.setPhoto(shop_photo);
//                                personChats.add(personChat);
//                            }
                            /**
                             *setAdapter
                             */
                            chatAdapter = new ChatAdapter(context, personChats);
                            lv_chat_dialog.setAdapter(chatAdapter);
                        } else {
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        context = this;
        user = new UserInfo();
        user.readData(this);
        Intent intent = getIntent();
//        shopid = intent.getStringExtra("shopid");
        shop_uid = intent.getStringExtra("shop_uid");
        shop_photo = intent.getStringExtra("shop_photo");
        shopname = intent.getStringExtra("shopname");
        initView();
        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).xcGetChatData(shop_uid);
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
        initChat();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        tv_receive_name = (TextView) findViewById(R.id.tv_receive_name);
        tv_receive_name.setText(shopname);
        lv_chat_dialog = (ListView) findViewById(R.id.lv_chat_dialog);
        btn_chat_message_send = (Button) findViewById(R.id.btn_chat_message_send);
        et_chat_message = (EditText) findViewById(R.id.et_chat_message);
    }
    private void initChat() {
        /**
         * 发送按钮的点击事件
         */
        btn_chat_message_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Aut-generated method stub
                if (TextUtils.isEmpty(et_chat_message.getText().toString().trim())) {
                    Toast.makeText(context, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (NetUtil.isConnnected(context)) {
                    new MainRequest(context, handler).SendChatData(shop_uid, et_chat_message.getText().toString());
                } else {
                    Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }


    //这是格式化时间的
    public static String getStrTime(long cc_time){
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        //例如 :cc_time = 1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time*1000L));
        return re_StrTime;
    }



    class Reciver extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }



}
