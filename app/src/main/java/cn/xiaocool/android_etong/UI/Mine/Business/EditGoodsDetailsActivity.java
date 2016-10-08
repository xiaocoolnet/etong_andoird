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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
 * Created by SJL on 2016/9/25.
 */
public class EditGoodsDetailsActivity extends Activity implements View.OnClickListener{

    private EditText editText;
    private RelativeLayout rl_back;
    private Context context;
    private TextView tx_upload;
    private UserInfo user;
    private LinearLayout lin_lunbo2, lin_lunbo3, lin_lunbo4, lin_lunbo5;
    private ImageView img_lunbo_pic1, img_lunbo_pic2, img_lunbo_pic3, img_lunbo_pic4, img_lunbo_pic5;
    // 保存的文件的路径
    @SuppressLint("SdCardPath")
    private String filepath = "/sdcard/goodspic";
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private static final int KEY1 = 0x666;
    private static final int KEY2 = 0x667;
    private static final int KEY3 = 0x668;
    private static final int KEY4 = 0x669;
    private static final int KEY5 = 0x660;

    private String plist1, plist2, plist3, plist4, plist5;
    private String nlist1, nlist2, nlist3, nlist4, nlist5,xiangqing;
    private int judge;
    private int state_xq = 0;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KEY1:
                    Log.e("upload goodspic", "success");
                    String key = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            state_xq = 1;
                            if (!(plist2 == null || plist2.equals(""))) {
                                progressDialog.setMessage("正在上传第二张照片");
                                new MainRequest(context, handler).uploadavatar(plist2, KEY2);
                            } else {
                                Log.e("path2", "not set");
                                progressDialog.dismiss();
                                Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case KEY2:
                    Log.e("upload goodspic", "success");
                    String key2 = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key2);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            state_xq = 1;
                            if (!(plist3 == null || plist3.equals(""))) {
                                progressDialog.setMessage("正在上传第三张照片");
                                new MainRequest(context, handler).uploadavatar(plist3, KEY3);
                            } else {
                                Log.e("path3", "not set");
                                progressDialog.dismiss();
                                Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case KEY3:
                    Log.e("upload goodspic", "success");
                    String key3 = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key3);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            state_xq = 1;
                            if (!(plist4 == null || plist4.equals(""))) {
                                progressDialog.setMessage("正在上传第四张照片");
                                new MainRequest(context, handler).uploadavatar(plist4, KEY4);
                            } else {
                                Log.e("path4", "not set");
                                progressDialog.dismiss();
                                Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case KEY4:
                    Log.e("upload goodspic", "success");
                    String key4 = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key4);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            state_xq = 1;
                            if (!(plist5 == null || plist5.equals(""))) {
                                progressDialog.setMessage("正在上传第五张照片");
                                new MainRequest(context, handler).uploadavatar(plist5, KEY5);
                            } else {
                                Log.e("path4", "not set");
                                progressDialog.dismiss();
                                Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case KEY5:
                    String key5 = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key5);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            state_xq = 1;
                            if (!(plist5 == null || plist5.equals(""))) {
                                Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                progressDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;



//                    Log.e("upload goodspic", "success");
//                    String key5 = (String) msg.obj;
//                    try {
//                        JSONObject json = new JSONObject(key5);
//                        String state1 = json.getString("status");
//                        if (state1.equals("success")) {
//                            Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//                        } else {
//                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_goods_details);
        context = this;
        user = new UserInfo(context);
        user.readData(context);
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        intiview();
        xiangqing = editText.getText().toString();
        Log.e("xiangqing",xiangqing);
    }

    public void intiview(){
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);

        lin_lunbo2 = (LinearLayout) findViewById(R.id.lin_lunbo2);
        lin_lunbo2.setVisibility(View.GONE);
        lin_lunbo3 = (LinearLayout) findViewById(R.id.lin_lunbo3);
        lin_lunbo3.setVisibility(View.GONE);
        lin_lunbo4 = (LinearLayout) findViewById(R.id.lin_lunbo4);
        lin_lunbo4.setVisibility(View.GONE);
        lin_lunbo5 = (LinearLayout) findViewById(R.id.lin_lunbo5);
        lin_lunbo5.setVisibility(View.GONE);


        img_lunbo_pic1 = (ImageView) findViewById(R.id.img_pic1);
        img_lunbo_pic1.setOnClickListener(this);
        img_lunbo_pic2 = (ImageView) findViewById(R.id.img_pic2);
        img_lunbo_pic2.setOnClickListener(this);
        img_lunbo_pic3 = (ImageView) findViewById(R.id.img_pic3);
        img_lunbo_pic3.setOnClickListener(this);
        img_lunbo_pic4 = (ImageView) findViewById(R.id.img_pic4);
        img_lunbo_pic4.setOnClickListener(this);
        img_lunbo_pic5 = (ImageView) findViewById(R.id.img_pic5);
        img_lunbo_pic5.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.et_txt);
        tx_upload = (TextView) findViewById(R.id.tx_upload);
        tx_upload.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                Intent intent_data = new Intent();
                intent_data.putExtra("1111", "111");
                if (plist1 == null || plist1.equals("")) {
                    plist1 = "";
                }
                if (plist2 == null || plist2.equals("")) {
                    plist2 = "";
                }
                if (plist3 == null || plist3.equals("")) {
                    plist3 = "";
                }
                if (plist4 == null || plist4.equals("")) {
                    plist4 = "";
                }
                if (plist5 == null || plist5.equals("")) {
                    plist5 = "";
                }
                if (nlist1 == null || nlist1.equals("")) {
                    nlist1 = "";
                }
                if (nlist2 == null || nlist2.equals("")) {
                    nlist2 = "";
                }
                if (nlist3 == null || nlist3.equals("")) {
                    nlist3 = "";
                }
                if (nlist4 == null || nlist4.equals("")) {
                    nlist4 = "";
                }
                if (nlist5 == null || nlist5.equals("")) {
                    nlist5 = "";
                }
                intent_data.putExtra("state_xq", state_xq);
                intent_data.putExtra("plist1", plist1);
                intent_data.putExtra("plist2", plist2);
                intent_data.putExtra("plist3", plist3);
                intent_data.putExtra("plist4", plist4);
                intent_data.putExtra("plist5", plist5);


                intent_data.putExtra("nlist1", nlist1);
                intent_data.putExtra("nlist2", nlist2);
                intent_data.putExtra("nlist3", nlist3);
                intent_data.putExtra("nlist4", nlist4);
                intent_data.putExtra("nlist5", nlist5);
                intent_data.putExtra("content", xiangqing);


                setResult(RESULT_OK, intent_data);
                finish();
                break;
            case R.id.img_pic1:
                judge = 1;
                ShowPickDialog();
                break;
            case R.id.img_pic2:
                judge = 2;
                ShowPickDialog();
                break;
            case R.id.img_pic3:
                judge = 3;
                ShowPickDialog();
                break;
            case R.id.img_pic4:
                judge = 4;
                ShowPickDialog();
                break;
            case R.id.img_pic5:
                judge = 5;
                ShowPickDialog();
                break;

            case R.id.tx_upload:
                upload();
                break;
        }
    }
    private void upload() {
        if (plist1 == null || plist1.equals("")) {
            Toast.makeText(context, "请至少设置一张照片", Toast.LENGTH_SHORT).show();
        } else if (NetUtil.isConnnected(context)) {
            progressDialog.setMessage("正在上传第一张图片");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            new MainRequest(context, handler).uploadavatar(plist1, KEY1);
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
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
                img_lunbo_pic1.setImageDrawable(drawable);
                lin_lunbo2.setVisibility(View.VISIBLE);
               nlist1 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, nlist1, filepath);
            } else if (judge == 2) {
                img_lunbo_pic2.setImageDrawable(drawable);
                lin_lunbo3.setVisibility(View.VISIBLE);
                nlist2 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, nlist2, filepath);
            } else if (judge == 3) {
                img_lunbo_pic3.setImageDrawable(drawable);
                lin_lunbo4.setVisibility(View.VISIBLE);
                nlist3 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, nlist3, filepath);
            } else if (judge == 4) {
                img_lunbo_pic4.setImageDrawable(drawable);
                lin_lunbo5.setVisibility(View.VISIBLE);
                nlist4 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, nlist4, filepath);
            } else if (judge == 5) {
                img_lunbo_pic5.setImageDrawable(drawable);
                nlist5 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, nlist5, filepath);
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
                plist1 = imagefile.getPath();
                Log.e("path=", plist1);
            } else if (judge == 2) {
                plist2 = imagefile.getPath();
                Log.e("path=", plist2);
            } else if (judge == 3) {
                plist3 = imagefile.getPath();
                Log.e("path=", plist3);
            } else if (judge == 4) {
                plist4 = imagefile.getPath();
                Log.e("path=", plist4);
            } else if (judge == 5) {
                plist5 = imagefile.getPath();
                Log.e("path=", plist5);
            }
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent_data = new Intent();
            intent_data.putExtra("1111", "111");
            if (plist1 == null || plist1.equals("")) {
                plist1 = "";
            }
            if (plist2 == null || plist2.equals("")) {
                plist2 = "";
            }
            if (plist3 == null || plist3.equals("")) {
                plist3 = "";
            }
            if (plist4 == null || plist4.equals("")) {
                plist4 = "";
            }
            if (plist5 == null || plist5.equals("")) {
                plist5 = "";
            }
            if (nlist1 == null || nlist1.equals("")) {
                nlist1 = "";
            }
            if (nlist2 == null || nlist2.equals("")) {
                nlist2 = "";
            }
            if (nlist3 == null || nlist3.equals("")) {
                nlist1 = "";
            }
            if (nlist4 == null || nlist4.equals("")) {
                nlist4 = "";
            }
            if (nlist5 == null || nlist5.equals("")) {
                nlist5 = "";
            }

            intent_data.putExtra("plist1", plist1);
            intent_data.putExtra("plist2", plist2);
            intent_data.putExtra("plist3", plist3);
            intent_data.putExtra("plist4", plist4);
            intent_data.putExtra("plist5", plist5);

            intent_data.putExtra("nlist1", nlist1);
            intent_data.putExtra("nlist2", nlist2);
            intent_data.putExtra("nlist3", nlist3);
            intent_data.putExtra("nlist4", nlist4);
            intent_data.putExtra("nlist5", nlist5);
            intent_data.putExtra("content",xiangqing);


            setResult(RESULT_OK, intent_data);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
