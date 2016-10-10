package cn.xiaocool.android_etong.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.LoginActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.ApplyShopActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.AuditShopActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.ChatListActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.ConfirmSecurityActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.MyCommentActivity;
import cn.xiaocool.android_etong.UI.Mine.MineEditActivity;
import cn.xiaocool.android_etong.UI.Mine.MineFootprintActivity;
import cn.xiaocool.android_etong.UI.Mine.MyEvaluateActivity;
import cn.xiaocool.android_etong.UI.Mine.MyLikeActivity;
import cn.xiaocool.android_etong.UI.Mine.WalletActivity;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.IntentUtils;
import cn.xiaocool.android_etong.util.NetUtil;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.xiaocool.android_etong.util.StatusBarHeightUtils.getStatusBarHeight;

/**
 * Created by 潘 on 2016/6/12.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.mine_btn_allOrder)
    RelativeLayout mineBtnAllOrder;
    @BindView(R.id.mine_btn_noPay)
    Button mineBtnNoPay;
    @BindView(R.id.mine_btn_noUse)
    Button mineBtnNoUse;
    @BindView(R.id.mine_btn_noDeliver)
    Button mineBtnNoDeliver;
    @BindView(R.id.mine_btn_noConfirm)
    Button mineBtnNoConfirm;
    @BindView(R.id.mine_btn_noEvaluate)
    Button mineBtnNoEvaluate;
    @BindView(R.id.btn_fenxiang)
    Button btn_fenxiang;
    @BindView(R.id.btn_qianbao)
    Button btn_qianbao;
    @BindView(R.id.btn_zuji)
    Button btn_zuji;
    private ImageView img_setup;
    private CircleImageView img_mine_head;
    private RelativeLayout ry_line, rl_mine_shoucang, rl_order_list;
    private Button btn_kaidian, btn_daifukuan, btn_daishiyong, btn_daifahuo, btn_daiqueren, btn_daipinglun;
    private TextView tx_mine_name;
    private ProgressDialog progressDialog;
    private Context context;
    private String name , touxiang;
    private UserInfo userInfo;
    private SharedPreferences sp;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //获取个人资料
                case CommunalInterfaces.GETUSERINFO:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            Log.e("success", "加载资料更新成功");
                            JSONObject object = jsonObject.getJSONObject("data");
                            if (object.getString("name").equals("null")) {
                                tx_mine_name.setText("未设置昵称");
                            } else {
                                tx_mine_name.setText(object.getString("name"));
                            }
                            name = object.getString("name");
                            touxiang = object.getString("photo");
                            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + object.getString("photo"), img_mine_head);
                        } else {
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.GETMYSHOP:
                    Log.e("getmyshop", "getmyshop");
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    if (NetUtil.isConnnected(context)) {
                        try {
                            String status = jsonObject.getString("status");
                            String data = jsonObject.getString("data");
                            if (status.equals("success")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String shopid = jsonObject1.getString("id");
                                progressDialog.dismiss();
                                Intent intent = new Intent();
                                intent.putExtra("shopid", shopid);
                                intent.setClass(context, ConfirmSecurityActivity.class);
                                startActivity(intent);
                            }
                            //未开店
                            else if (data.equals("-10")) {
                                //申请店铺
                                startActivity(new Intent(context, ApplyShopActivity.class));
                                progressDialog.dismiss();
                            } else if (data.equals("0")) {
                                //正在审核
                                startActivity(new Intent(context, AuditShopActivity.class));
                                progressDialog.dismiss();

                            } else if (data.equals("-1")) {
                                Toast.makeText(context, "您的认证失败", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else if (data.equals("-2")) {
                                Toast.makeText(context, "您的店铺已被禁用", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    btn_kaidian.setEnabled(true);
                    break;
                case CommunalInterfaces.GET_MY_SHOP_TEXT:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")){
                            btn_kaidian.setText("我的店铺");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;


            }
        }
    };
    private Button btnComment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        context = getActivity();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置状态栏高度
        ry_line = (RelativeLayout) getView().findViewById(R.id.lin);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ry_line.getLayoutParams();
        linearParams.height = getStatusBarHeight(context);
        ry_line.setLayoutParams(linearParams);
        initview();
        userInfo = new UserInfo();
        userInfo.readData(context);
        sp = context.getSharedPreferences("list", context.MODE_PRIVATE);
        new MainRequest(context, handler).getMyShopText();
    }

    private void initview() {
        new MainRequest(context, handler).userinfo();
        tx_mine_name = (TextView) getView().findViewById(R.id.tx_mine_name);
        img_mine_head = (CircleImageView) getView().findViewById(R.id.img_mine_head);
        img_mine_head.setOnClickListener(this);
        btn_kaidian = (Button) getView().findViewById(R.id.btn_kaidian);
        btn_kaidian.setOnClickListener(this);
        img_setup = (ImageView) getView().findViewById(R.id.img_setup);
        img_setup.setOnClickListener(this);
        rl_mine_shoucang = (RelativeLayout) getView().findViewById(R.id.mine_btn_my_like);
        rl_mine_shoucang.setOnClickListener(this);
        mineBtnAllOrder.setOnClickListener(this);
        mineBtnNoPay.setOnClickListener(this);
        mineBtnNoUse.setOnClickListener(this);
        mineBtnNoDeliver.setOnClickListener(this);
        mineBtnNoConfirm.setOnClickListener(this);
        mineBtnNoEvaluate.setOnClickListener(this);
        btnComment = (Button) getView().findViewById(R.id.btn_comment);
        btnComment.setOnClickListener(this);
        btn_fenxiang = (Button) getView().findViewById(R.id.btn_fenxiang);
        btn_fenxiang.setOnClickListener(this);
        btn_qianbao.setOnClickListener(this);
        btn_zuji.setOnClickListener(this);
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            try {
                new MainRequest(context, handler).userinfo();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_mine_head:
                startActivityForResult(new Intent(context, MineEditActivity.class), 1);
                break;
            case R.id.btn_kaidian:
                progressDialog.setMessage("正在加载");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new MainRequest(context, handler).getmyshop();
                break;
            case R.id.img_setup:
                showPopupMenu(img_setup);
                break;
            case R.id.mine_btn_my_like:
                IntentUtils.getIntent((Activity) context, MyLikeActivity.class);
                break;
            case R.id.btn_comment:
                IntentUtils.getIntent((Activity) context, MyCommentActivity.class);//跳转我的评价
                break;
            case R.id.mine_btn_allOrder:
                Intent intent = new Intent();
                intent.putExtra("indent", 0);
                intent.setClass(context, MyEvaluateActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_btn_noPay:
                Intent intent1 = new Intent();
                intent1.putExtra("indent", 1);
                intent1.setClass(context, MyEvaluateActivity.class);
                startActivity(intent1);
                break;
            case R.id.mine_btn_noUse:
                Intent intent2 = new Intent();
                intent2.putExtra("indent", 2);
                intent2.setClass(context, MyEvaluateActivity.class);
                startActivity(intent2);
                break;
            case R.id.mine_btn_noDeliver:
                Intent intent3 = new Intent();
                intent3.putExtra("indent", 3);
                intent3.setClass(context, MyEvaluateActivity.class);
                startActivity(intent3);
                break;
            case R.id.mine_btn_noConfirm:
                Intent intent4 = new Intent();
                intent4.putExtra("indent", 4);
                intent4.setClass(context, MyEvaluateActivity.class);
                startActivity(intent4);
                break;
            case R.id.mine_btn_noEvaluate:
                Intent intent5 = new Intent();
                intent5.putExtra("indent", 5);
                intent5.setClass(context, MyEvaluateActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_fenxiang:
                Intent intent6 = new Intent();
                intent6.setClass(context, ChatListActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn_qianbao:
                Intent intent7 = new Intent();
                intent7.setClass(context, WalletActivity.class);
                intent7.putExtra("name",name);
                intent7.putExtra("touxiang",touxiang);
                startActivity(intent7);
                break;
            case R.id.btn_zuji:
                Intent intent8 = new Intent();
                intent8.setClass(context, MineFootprintActivity.class);
                startActivity(intent8);
                break;
        }
    }

    private void showPopupMenu(View view) {
        Log.e("popupmenu", "success");
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(context, view);
        //menu 布局
        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.quit:
                        userInfo.clearDataExceptPhone(context);
                        SharedPreferences.Editor e=sp.edit();
                        e.clear();
                        e.commit();
                        userInfo.setUserId(null);
                        startActivity(new Intent(context, LoginActivity.class));
                        getActivity().finish();
                        JPushInterface.stopPush(context);
                        Toast.makeText(context, "退出登录成功", Toast.LENGTH_SHORT).show();
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
