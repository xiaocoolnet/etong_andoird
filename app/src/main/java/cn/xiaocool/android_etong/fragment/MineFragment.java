package cn.xiaocool.android_etong.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.LoginActivity;
import cn.xiaocool.android_etong.UI.MineEditActivity;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.xiaocool.android_etong.util.StatusBarHeightUtils.getStatusBarHeight;

/**
 * Created by 潘 on 2016/6/12.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private CircleImageView img_mine_head;
    private RelativeLayout ry_line;
    private Button btn_zhuxiao;
    private TextView tx_mine_name;
    private Context context;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                //获取个人资料
                case CommunalInterfaces.GETUSERINFO:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        if (state.equals("success")) {
                            Log.e("success", "加载资料更新成功");
                            JSONObject object = jsonObject.getJSONObject("data");
                            tx_mine_name.setText(object.getString("name"));
                            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + object.getString("photo"), img_mine_head);
                        }else{
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,container,false);
        context = getActivity();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置状态栏高度
        ry_line = (RelativeLayout)getView().findViewById(R.id.lin);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ry_line.getLayoutParams();
        linearParams.height=getStatusBarHeight(context);
        ry_line.setLayoutParams(linearParams);
        initview();
    }

    private void initview() {
        new MainRequest(context,handler).userinfo();
        tx_mine_name = (TextView)getView().findViewById(R.id.tx_mine_name);
        img_mine_head = (CircleImageView)getView().findViewById(R.id.img_mine_head);
        img_mine_head.setOnClickListener(this);
        btn_zhuxiao=(Button)getView().findViewById(R.id.btn_zhuxiao);
        btn_zhuxiao.setOnClickListener(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            try {
                new MainRequest(context,handler).userinfo();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_mine_head:
                startActivityForResult(new Intent(context, MineEditActivity.class), 1);
                break;
            case R.id.btn_zhuxiao:
                startActivity(new Intent(context, LoginActivity.class));
                getActivity().finish();
                Toast.makeText(context,"退出登录",Toast.LENGTH_SHORT).show();
        }
    }
}
