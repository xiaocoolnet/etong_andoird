package cn.xiaocool.android_etong.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.KeyBoardUtils;

/**
 * Created by 潘 on 2016/6/21.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private UserInfo user;
    private Context context;
    private String phone ,password;
    private EditText et_login_phone , et_login_password;
    private TextView tx_forget_password , tx_zhuce;
    private Button btn_login;
    private Handler handle = new Handler() {
     public void handleMessage(Message msg){
         switch (msg.what){
             case CommunalInterfaces.LOGIN:
                 try {
                     JSONObject json = (JSONObject) msg.obj;
                     String status = json.getString("status");
                     String data = json.getString("data");
                     if (status.equals("success")) {
                         Log.e("data",data);
                         JSONObject item = new JSONObject(data);
                         user.setUserId(item.getString("id"));
                         user.writeData(context);;
                         Toast.makeText(LoginActivity.this, "登陆成功",
                                 Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(LoginActivity.this,MainActivity.class));
                         finish();
                     } else {
                         Toast.makeText(LoginActivity.this, data,
                                 Toast.LENGTH_SHORT).show();
                     }
                 } catch (JSONException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                 }
                 break;
         }
     }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        context = this;
        initview();
    }

    private void initview() {
        et_login_phone = (EditText)findViewById(R.id.et_login_phone);
        et_login_password = (EditText)findViewById(R.id.et_login_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        tx_forget_password = (TextView)findViewById(R.id.tx_forget_password);
        tx_forget_password.setOnClickListener(this);
        tx_zhuce = (TextView)findViewById(R.id.tx_zhuce);
        tx_zhuce.setOnClickListener(this);
        user = new UserInfo();
        user.readData(this);
        if (!user.getUserPhone().equals("")) {
            et_login_phone.setText(user.getUserPhone());
        }
        // 切换后将EditText光标置于末尾
        CharSequence charSequence = et_login_phone.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
        KeyBoardUtils.showKeyBoardByTime(et_login_phone, 300);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tx_zhuce:
                Intent intent = new Intent(LoginActivity.this,RegisiterActivity.class);
                startActivity(intent);
                break;
            case R.id.tx_forget_password:
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }
//登录
    private void login() {
        phone = et_login_phone.getText().toString();
        password = et_login_password.getText().toString();
        user.setUserPhone(phone);
        user.setUserPassword(password);
        user.writeData(context);
        Log.e("et_login_phone is", et_login_phone.getText().toString());
        Log.e("et_login_password is",et_login_password.getText().toString());
        if(phone.length()==11){
            if (!password.equals("")){
                new MainRequest(context,handle).login(phone,password);
            }else {
                Toast.makeText(context,"请输入密码",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context,"请输入正确手机号",Toast.LENGTH_SHORT).show();
        }
    }
}
