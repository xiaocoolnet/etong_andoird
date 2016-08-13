package cn.xiaocool.android_etong.UI.Mine.Business;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/7/8.
 */
public class AuthenticationShopActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private EditText et_name, et_phone, et_id_card, et_address, et_city;
    private ImageView img_ren, img_shenfenzheng, img_zhizhao;
    private TextView tx_next;
    private int judge = 0, state1 = 0, state2 = 0, state3 = 0;
    private ProgressDialog progressDialog;
    ArrayAdapter<String> adapter01;
    private String city;
    private Spinner spinner_shop_type;
    private String[] type = new String[]{"美食", "电影", "酒店", "外卖", "生活娱乐", "周边游", "生活服务", "KTV", "手机充值"};
    private UserInfo user;
    // 保存的文件的路径
    @SuppressLint("SdCardPath")
    private String filepath = "/sdcard/myheader";
    private String positive_pic, opposite_pic, licences_pic;
    private String positive_path, opposite_path, licences_path;
    private String show_type = "";
    String name, id_card, phone, address;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private static final int KEY1 = 4;
    private static final int KEY2 = 5;
    private static final int KEY3 = 6;
    private static final int KEY4 = 7;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //上传头像
                case KEY1:
                    Log.e("upavatar", "success");
                    String key = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            Log.e("success", "positive_pic");
                            progressDialog.setMessage("正在上传身份证照片");
                            Toast.makeText(context, "头像上传成功", Toast.LENGTH_SHORT).show();
                            new MainRequest(context, handler).uploadavatar(opposite_path, KEY2);
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case KEY2:
                    Log.e("upavatar", "success");
                    String key2 = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key2);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            Log.e("success", "opposite_pic");
                            progressDialog.setMessage("正在上传营业执照照片");
                            Toast.makeText(context, "身份证上传成功", Toast.LENGTH_SHORT).show();
                            new MainRequest(context, handler).uploadavatar(licences_path, KEY3);
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case KEY3:
                    Log.e("upavatar", "success");
                    String key3 = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key3);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            Log.e("success", "license_pic");
                            progressDialog.setMessage("正在上传资料");
                            Toast.makeText(context, "营业执照上传成功", Toast.LENGTH_SHORT).show();
                            new MainRequest(context, handler).CreateShop(city,show_type, name, phone, id_card, address, positive_pic + ".jpg", opposite_pic + ".jpg", licences_pic + ".jpg", KEY4);
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case KEY4:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            progressDialog.dismiss();
                            Log.e("success", "createshop");
                            Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(AuthenticationShopActivity.this, AuditShopActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.business_authentication_shop);
        context = this;
        user = new UserInfo(context);
        user.readData(context);
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        initview();
    }

    private void initview() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_address = (EditText) findViewById(R.id.et_address);
        et_id_card = (EditText) findViewById(R.id.et_id_card);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_city = (EditText) findViewById(R.id.et_city);
        img_ren = (ImageView) findViewById(R.id.img_ren);
        img_ren.setOnClickListener(this);
        img_shenfenzheng = (ImageView) findViewById(R.id.img_shenfenzheng);
        img_shenfenzheng.setOnClickListener(this);
        img_zhizhao = (ImageView) findViewById(R.id.img_zhizhao);
        img_zhizhao.setOnClickListener(this);
        tx_next = (TextView) findViewById(R.id.tx_next);
        tx_next.setOnClickListener(this);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        spinner_shop_type = (Spinner) findViewById(R.id.spinner_shop_type);
        adapter01 = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, type);
        spinner_shop_type.setAdapter(adapter01);
        spinner_shop_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                show_type = spinner_shop_type.getSelectedItem().toString();
                Log.e("show=", show_type);
                if (show_type.equals("美食")) {
                    show_type = "1";
                    Log.e("show=", show_type);
                } else if (show_type.equals("电影")) {
                    show_type = "2";
                    Log.e("show=", show_type);
                } else if (show_type.equals("酒店")) {
                    show_type = "3";
                    Log.e("show=", show_type);
                } else if (show_type.equals("外卖")) {
                    show_type = "4";
                    Log.e("show=", show_type);
                } else if (show_type.equals("生活娱乐")) {
                    show_type = "5";
                    Log.e("show=", show_type);
                } else if (show_type.equals("周边游")) {
                    show_type = "6";
                    Log.e("show=", show_type);
                } else if (show_type.equals("生活服务")) {
                    show_type = "7";
                    Log.e("show=", show_type);
                } else if (show_type.equals("KTV")) {
                    show_type = "8";
                    Log.e("show=", show_type);
                } else if (show_type.equals("手机充值")) {
                    show_type = "9";
                    Log.e("show=", show_type);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_ren:
                judge = 1;
                ShowPickDialog();
                break;
            case R.id.img_shenfenzheng:
                judge = 2;
                ShowPickDialog();
                break;
            case R.id.img_zhizhao:
                judge = 3;
                ShowPickDialog();
                break;
            case R.id.tx_next:
                next();
                break;
            case R.id.rl_back:
                finish();
                break;
        }
    }

    private void next() {
        name = et_name.getText().toString();
        id_card = et_id_card.getText().toString();
        phone = et_phone.getText().toString();
        address = et_address.getText().toString();
        city = et_city.getText().toString();
        if (!name.equals("")) {
            if (phone.length() == 11) {
                if (id_card.length() == 18) {
                    if (!address.equals("")) {
                        if (!TextUtils.isEmpty(city)) {
                            if (state1 == 1 && state2 == 1 && state3 == 1) {
                                if (!TextUtils.isEmpty(show_type)) {
                                    if (NetUtil.isConnnected(context)) {
                                        progressDialog.setMessage("正在上传资料");
                                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                        progressDialog.show();
                                        new MainRequest(context, handler).uploadavatar(positive_path, KEY1);
                                    } else {
                                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(context, "请选择一种店铺类型", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "请完善图片信息", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "请输入城市", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "请输入地址", Toast.LENGTH_SHORT).show();
                        et_address.requestFocus();
                    }
                } else {
                    Toast.makeText(context, "请输入正确身份证", Toast.LENGTH_SHORT).show();
                    et_id_card.requestFocus();
                }
            } else {
                Toast.makeText(context, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                et_phone.requestFocus();
            }
        } else {
            Toast.makeText(context, "请输入姓名", Toast.LENGTH_SHORT).show();
            et_name.requestFocus();
        }
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
            if (judge == 1) {
                img_ren.setImageDrawable(drawable);
                state1 = 1;
                positive_pic = "positive_pic" + user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, positive_pic, filepath);
            } else if (judge == 2) {
                img_shenfenzheng.setImageDrawable(drawable);
                state2 = 1;
                opposite_pic = "positive_pic" + user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, opposite_pic, filepath);
            } else if (judge == 3) {
                img_zhizhao.setImageDrawable(drawable);
                state3 = 1;
                licences_pic = "positive_pic" + user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, licences_pic, filepath);
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
            if (judge == 1) {
                positive_path = imagefile.getPath();
            } else if (judge == 2) {
                opposite_path = imagefile.getPath();
            } else if (judge == 3) {
                licences_path = imagefile.getPath();
            }
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
