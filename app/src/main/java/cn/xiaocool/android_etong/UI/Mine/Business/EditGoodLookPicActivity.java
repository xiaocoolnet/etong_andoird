package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;
import cn.xiaocool.android_etong.net.constant.request.MineRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/7/28.
 */
public class EditGoodLookPicActivity extends Activity implements View.OnClickListener {
    private String picName;
    private RelativeLayout rlBack;
    private TextView tvTitle;
    private ImageView ivPic0,ivPic1,ivPic2;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_good_look_pic);
        initView();
        initPic();
        Intent intent = getIntent();
        picName = intent.getStringExtra("picName");
        String picArray[] = picName.split("[,]");
        ImageView[] ivPicArr = {ivPic0,ivPic1,ivPic2};
        for (int i = 0; i < picArray.length; i++) {
            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + picArray[i], ivPicArr[i], displayImageOptions);
        }
    }

    private void initPic() {
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    private void initView() {
        ivPic0 = (ImageView) findViewById(R.id.editGood_iv_pic0);
        ivPic1 = (ImageView) findViewById(R.id.editGood_iv_pic1);
        ivPic2 = (ImageView) findViewById(R.id.editGood_iv_pic2);
        ivPic0.setOnClickListener(this);
        ivPic1.setOnClickListener(this);
        ivPic2.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("修改轮播图");
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
