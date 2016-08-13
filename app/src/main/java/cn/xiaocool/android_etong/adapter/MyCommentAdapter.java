package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.Mine.MyCommentBean;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;


/**
 * Created by wzh on 2016/8/13.
 */
public class MyCommentAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<MyCommentBean.DataBean> dataBeanList;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public MyCommentAdapter(Context context, List<MyCommentBean.DataBean> dataBeanList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataBeanList = dataBeanList;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return dataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        String picName = dataBeanList.get(position).getPicture();
        String[] arrayPic = picName.split("[,]");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.my_comment_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                    viewHolder.myCommentGoodPic, displayImageOptions);
            viewHolder.myCommentGoodName.setText(dataBeanList.get(position).getGoodsname());//商品名字
            viewHolder.myCommentGoodComment.setText(dataBeanList.get(position).getContent());//获取评价内容
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                    viewHolder.myCommentGoodPic, displayImageOptions);
            viewHolder.myCommentGoodName.setText(dataBeanList.get(position).getGoodsname());//商品名字
            viewHolder.myCommentGoodComment.setText(dataBeanList.get(position).getContent());//获取评价内容
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.my_comment_good_pic)
        ImageView myCommentGoodPic;
        @BindView(R.id.my_comment_good_name)
        TextView myCommentGoodName;
        @BindView(R.id.my_comment_good_comment)
        TextView myCommentGoodComment;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
