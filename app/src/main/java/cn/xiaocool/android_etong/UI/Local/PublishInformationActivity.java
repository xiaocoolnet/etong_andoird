package cn.xiaocool.android_etong.UI.Local;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
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

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.EditGoodPicAdapter;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by hzh on 2016/12/29.
 */

public class PublishInformationActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;


    private List list = new ArrayList();
    private EditGoodPicAdapter editGoodPicAdapter;
    private GridView gridView;
    private ProgressDialog progressDialog;
    private boolean isShowDelete = false;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KEY:
                    String key = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                            Log.e("yes", "success0");
                            //刷新新加图片
                            list.add(storePicName + ".jpg");
                            editGoodPicAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "上传失败！", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.CHANGE_GOOD_PICS:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            ToastUtils.makeShortToast(context, "图片上传成功！");
                            finish();
                        } else {
                            ToastUtils.makeShortToast(context, "修改失败！请重试");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.PUBLISH_CITY_BBS:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")) {
                            ToastUtils.makeShortToast(context, "发布成功！");
                            finish();
                        } else {
                            ToastUtils.makeShortToast(context, "发布失败！请重试");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };


    private String filepath = "/sdcard/goodspic";
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private String storePicName;
    private String picPath;
    private static final int KEY = 0x0808;
    private EditText etTitle;
    private EditText etContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_publish_information);
        context = this;
        initView();


        editGoodPicAdapter = new EditGoodPicAdapter(this, list);
        gridView.setAdapter(editGoodPicAdapter);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (isShowDelete) {
                    isShowDelete = false;
                } else {
                    isShowDelete = true;
                }
                editGoodPicAdapter.setIsShowDelete(isShowDelete);
                return true;
            }
        });

    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        ImageView ivAddPic = (ImageView) findViewById(R.id.publish_iv_add_pic);
        ivAddPic.setOnClickListener(this);
        gridView = (GridView) findViewById(R.id.gridView_show__publish_pics);
        TextView tvConfirm = (TextView) findViewById(R.id.publish_btn_confirm);
        tvConfirm.setOnClickListener(this);
        etTitle = (EditText) findViewById(R.id.publish_et_title);
        etTitle.setOnClickListener(this);
        etContent = (EditText) findViewById(R.id.publish_et_content);
        etTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.publish_iv_add_pic:
                if (list.size() < 5) {
                    ShowPickDialog();
                }
                break;
            case R.id.publish_btn_confirm:
                String str = list.toString();
                String picAdd = str.replace("[", "").replace("]", "").replace(" ", "");//去除首尾[]和空格
                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();

                if (!(title.length() == 0) && !(content.length() == 0)) {
                    if (NetUtil.isConnnected(context)) {
                        new MainRequest(this, handler).publishCityBBS("1", "", "", "", title, content, picAdd, "", "");
                    } else {
                        ToastUtils.makeShortToast(context, "请检查网络！");
                    }
                } else {
                    ToastUtils.makeShortToast(context, "输入不能为空！");
                }
                break;
        }
    }


    //弹出选择相册 拍照
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

    //回调
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

    //保存裁剪后的数据
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(this.getResources(), photo);
//            if () {
//                img_lunbo_pic1.setImageDrawable(drawable);
//                lin_lunbo2.setVisibility(View.VISIBLE);
            storePicName = "picture" + String.valueOf(new Date().getTime());
            storeImageToSDCARD(photo, storePicName, filepath);
//            }
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
//            if (judge == 1) {
            picPath = imagefile.getPath();
            Log.e("pic now at", picPath);
//            }
            fos.flush();
            fos.close();

            if (NetUtil.isConnnected(context)) {
                if (!(picPath == null || picPath.equals(""))) {
                    Log.e("aaa", "aabb");
                    new MainRequest(context, handler).uploadavatar(picPath, KEY);
                } else {

                    Toast.makeText(context, "错误！图片地址为空！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
