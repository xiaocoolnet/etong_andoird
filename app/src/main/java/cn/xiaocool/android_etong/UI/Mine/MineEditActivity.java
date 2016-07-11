package cn.xiaocool.android_etong.UI.Mine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetBaseUtils;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.xiaocool.android_etong.util.StatusBarHeightUtils.getStatusBarHeight;


/**
 * Created by 潘 on 2016/6/22.
 */
public class MineEditActivity extends Activity implements View.OnClickListener {

    private LinearLayout ln_name,ln_phone,ln_address;
    private TextView my_edit_sex,tx_upname,tx_name,tx_edit_phone;
    private String head = null,name;
    private CircleImageView set_head_img;
    private Context mContext;
    private UserInfo user;
    private RelativeLayout ry_line;
    private RelativeLayout btnBack;
    // 保存的文件的路径
    @SuppressLint("SdCardPath")
    private String filepath = "/sdcard/myheader";
    private String picname = "newpic";
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private static final int KEY = CommunalInterfaces.UPLOADAVATAR;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                //上传头像
                case CommunalInterfaces.UPLOADAVATAR:
                    Log.e("upavatar","success");
                    String key = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key);
                        String state1=json.getString("status");
                        if (state1.equals("success")) {
                            Log.e("success", "头像上传成功");
                            Toast.makeText(mContext, "头像上传成功", Toast.LENGTH_SHORT).show();
                            new MainRequest(mContext,handler).updatauseravatar(picname + ".jpg");
                        }else{
                            Toast.makeText(mContext, json.getString("data"),Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                break;
                //更新头像资料
                case CommunalInterfaces.UPUSERAVATAR:
                    try {
                        JSONObject json1 = (JSONObject) msg.obj;
                        String state=json1.getString("status");
                        if (state.equals("success")) {
                           Log.e("success", "头像资料更新成功");
                            Toast.makeText(mContext,"头像资料上传成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, json1.getString("data"),Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                //获取个人资料
                case CommunalInterfaces.GETUSERINFO:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        if (state.equals("success")) {
                            Log.e("success", "加载资料更新成功");
                            JSONObject object = jsonObject.getJSONObject("data");
                            tx_name.setText(object.getString("name"));
                            name=object.getString("name");
                            user.setUserPhone(object.getString("phone"));
                            user.setUserName(name);
                            user.writeData(mContext);
                            tx_edit_phone.setText(object.getString("phone"));
                            if(object.getString("sex").equals("0")){
                                my_edit_sex.setText("女");
                            }else if(object.getString("sex").equals("1")){
                                my_edit_sex.setText("男");
                            }
                            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + object.getString("photo"), set_head_img);
                        }else{
                            Toast.makeText(mContext, jsonObject.getString("data"),Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                //修改性别
                case CommunalInterfaces.UPDATAUSERSEX:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        if (state.equals("success")) {
                            Log.e("success", "更新性别成功");
                            Toast.makeText(mContext, "更新性别成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, jsonObject.getString("data"),Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
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
        setContentView(R.layout.my_edit);
        mContext=this;
        user = new UserInfo();
        user.readData(mContext);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initdata();
        new MainRequest(mContext,handler).userinfo();
    }

    private void initdata() {
        //设置标题栏高度
        ry_line = (RelativeLayout)findViewById(R.id.lin_edit);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ry_line.getLayoutParams();
        linearParams.height=getStatusBarHeight(mContext);
        ry_line.setLayoutParams(linearParams);
        //设置头像
        set_head_img = (CircleImageView) findViewById(R.id.set_head_img);
        set_head_img.setOnClickListener(this);
        my_edit_sex = (TextView)findViewById(R.id.my_edit_sex);
        my_edit_sex.setOnClickListener(this);
//        my_edit_name = (EditText)findViewById(R.id.my_edit_name);
        btnBack = (RelativeLayout) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        tx_name=(TextView)findViewById(R.id.tx_name);
        ln_name=(LinearLayout)findViewById(R.id.ln_name);
        ln_name.setOnClickListener(this);
        ln_phone=(LinearLayout)findViewById(R.id.ln_phone);
        ln_phone.setOnClickListener(this);
        ln_address=(LinearLayout)findViewById(R.id.ln_address);
        ln_address.setOnClickListener(this);
        tx_edit_phone=(TextView)findViewById(R.id.tx_edit_phone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_head_img:
                ShowPickDialog();
                break;
            case R.id.my_edit_sex:
                ShowSexDialog();
                break;
            case R.id.btn_back:
                Intent dateIntent = new Intent();
                setResult(1,dateIntent);
                finish();
                break;
            case R.id.ln_name:
                Intent intent = new Intent(MineEditActivity.this,EditNameActivity.class);
                startActivityForResult(intent, 4);
                break;
            case R.id.ln_phone:
                Intent intent1 = new Intent(MineEditActivity.this,EditPhoneActivity.class);
                startActivityForResult(intent1, 5);
                break;
            case R.id.ln_address:
                Intent intent2 = new Intent();
                intent2.setClass(MineEditActivity.this,AddressActivity.class);
                startActivity(intent2);
                break;
        }
    }

    private void ShowSexDialog() {
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT).setNegativeButton("男", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                my_edit_sex.setText("男");
                new MainRequest(mContext,handler).updatausersex("1");
            }
        }).setPositiveButton("女", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                my_edit_sex.setText("女");
                new MainRequest(mContext,handler).updatausersex("0");
            }
        }).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent dateIntent2 = new Intent();
            setResult(1,dateIntent2);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void ShowPickDialog() {
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT).setNegativeButton("相册", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intentFromGallery = new Intent();
                intentFromGallery.setType("image/*"); // 设置文件类型
                intentFromGallery.setAction(Intent.ACTION_PICK);
                startActivityForResult(intentFromGallery, PHOTO_REQUEST_ALBUM);

            }
        }).setPositiveButton("拍照", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File file = new File(path, "newpic.jpg");
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                }

                startActivityForResult(intentFromCapture, PHOTO_REQUEST_CAMERA);
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case PHOTO_REQUEST_CAMERA:// 相册
                    // 判断存储卡是否可以用，可用进行存储
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File tempFile = new File(path, "newpic.jpg");
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(getApplicationContext(), "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PHOTO_REQUEST_ALBUM:// 图库
                    startPhotoZoom(data.getData());
                    break;

                case PHOTO_REQUEST_CUT: // 图片缩放完成后
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
                case 4:
                    String name =data.getStringExtra("name");
                    tx_name.setText(name);
                    break;
                case 5:
                    tx_edit_phone.setText(data.getStringExtra("phone"));
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(this.getResources(), photo);
            set_head_img.setImageDrawable(drawable);
            picname = "avatar"+user.getUserId()+String.valueOf(new Date().getTime());
            storeImageToSDCARD(photo, picname, filepath);
            if(NetBaseUtils.isConnnected(mContext)){
                new MainRequest(mContext,handler).uploadavatar(head, KEY);
            }else {
                Toast.makeText(mContext,"网络问题，请稍后再试！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * storeImageToSDCARD 将bitmap存放到sdcard中
     */
    public void storeImageToSDCARD(Bitmap colorImage, String ImageName, String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        File imagefile = new File(file, ImageName + ".jpg");
        try {
            imagefile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imagefile);
            colorImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            head = imagefile.getPath();
            user.setUserImg(head);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
