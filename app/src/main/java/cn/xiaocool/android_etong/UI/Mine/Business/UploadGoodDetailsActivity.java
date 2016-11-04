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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.cPicAdapter;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/10/22.
 */

public class UploadGoodDetailsActivity extends Activity implements View.OnClickListener {
    private cPicAdapter cPicAdapter;
    private UserInfo user;
    private Context context;
    private List<String> lists;
    private String picName, picPath;
    private String picStr = "";
    // 保存的文件的路径
    @SuppressLint("SdCardPath")
    private String filepath = "/sdcard/goodspic";
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private static final int KEY = 0x777;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KEY:
                    Log.e("upload goodspic", "success");
                    String key = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            picName = picName + ".jpg";
                            lists.add(picName);
                            cPicAdapter = new cPicAdapter(context, lists);
                            list_pic.setAdapter(cPicAdapter);
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.top_title_text)
    TextView topTitleText;
    @BindView(R.id.upload_good_details_et)
    EditText uploadGoodDetailsEt;
    @BindView(R.id.upload_good_btn)
    RelativeLayout uploadGoodBtn;
    @BindView(R.id.list_pic)
    ListView list_pic;
    @BindView(R.id.rl_upload_cpic)
    RelativeLayout rl_upload_cpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_good_details);
        context = this;
        user = new UserInfo();
        user.readData(context);
        ButterKnife.bind(this);
        topTitleText.setText("输入宝贝详情");
        Intent intent = getIntent();
        final String content = intent.getStringExtra("tv_content");
        uploadGoodDetailsEt.setText(content);
        uploadGoodDetailsEt.setSelection(content.length());//光标置后
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        lists = new ArrayList<>();
        list_pic.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(context).setTitle("删除").setMessage("确认删除？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lists.remove(position);
                                cPicAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("已取消","");
                    }
                }).show();
                return false;
            }
        });
    }


    @OnClick({R.id.btn_back, R.id.upload_good_btn, R.id.rl_upload_cpic})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                Intent intent0 = new Intent();
                intent0.putExtra("good_details", "");
                setResult(1000, intent0);
                finish();
                break;
            case R.id.upload_good_btn:
                Intent intent = new Intent();
                intent.putExtra("good_details", uploadGoodDetailsEt.getText().toString());//携带数据返回
                if (lists == null) {
                    intent.putExtra("picStr", "");
                } else {
                    for (int i = 0; i < lists.size(); i++) {
                        picStr = picStr + lists.get(i) + ",";
                    }
                    picStr= picStr.substring(0, picStr.length() - 1);
                    Log.e("picStr=", picStr);
                    intent.putExtra("picStr", picStr);
                }
                setResult(1000, intent);
                finish();
                break;
            case R.id.rl_upload_cpic:
                ShowPickDialog();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent0 = new Intent();
            intent0.putExtra("good_details", "");
            setResult(1000, intent0);
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
        intent.setDataAndType(uri, "image/*");        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 2);
        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 340);
//        intent.putExtra("outputY", 340);
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
            picName = user.getUserId() + String.valueOf(new Date().getTime());
            storeImageToSDCARD(photo, picName, filepath);
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
            picPath = imagefile.getPath();
            Log.e("path=", picPath);
            fos.flush();
            fos.close();
            if (NetUtil.isConnnected(context)) {
                progressDialog.setMessage("正在加载...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new MainRequest(context, handler).uploadavatar(picPath, KEY);
            } else {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
