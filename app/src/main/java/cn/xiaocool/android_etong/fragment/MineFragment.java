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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.LoginActivity;
import cn.xiaocool.android_etong.UI.Mine.BusinessActivity;
import cn.xiaocool.android_etong.UI.Mine.MineEditActivity;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.xiaocool.android_etong.util.StatusBarHeightUtils.getStatusBarHeight;

/**
 * Created by 潘 on 2016/6/12.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private ImageView img_setup;
    private CircleImageView img_mine_head;
    private RelativeLayout ry_line;
    private Button btn_kaidian;
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
        btn_kaidian=(Button)getView().findViewById(R.id.btn_kaidian);
        btn_kaidian.setOnClickListener(this);
        img_setup=(ImageView)getView().findViewById(R.id.img_setup);
        img_setup.setOnClickListener(this);

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
            case R.id.btn_kaidian:
                startActivity(new Intent(context, BusinessActivity.class));
                break;
            case R.id.img_setup:
                showPopupMenu(img_setup);
                break;

        }
    }

    private void showPopupMenu(View view) {
        Log.e("popupmenu","success");
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(context,view);
        //menu 布局
        popupMenu.getMenuInflater().inflate(R.menu.main,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.quit:
                        startActivity(new Intent(context, LoginActivity.class));
                        getActivity().finish();
                        Toast.makeText(context,"退出登录成功",Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });
//        // PopupMenu关闭事件 重写方法
//        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
//            @Override
//            public void onDismiss(PopupMenu menu) {
//                Toast.makeText(context, "关闭PopupMenu", Toast.LENGTH_SHORT).show();
//            }
//        });
        popupMenu.show();

    }
}
