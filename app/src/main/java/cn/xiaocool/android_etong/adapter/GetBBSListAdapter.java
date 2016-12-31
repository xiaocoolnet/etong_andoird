package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.CityBBSBean;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;


/**
 * Created by wzh on 2016/12/31.
 */
public class GetBBSListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<CityBBSBean.DataBean> dataBeenList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;

    public GetBBSListAdapter(Context context, List<CityBBSBean.DataBean> dataBeenList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataBeenList = dataBeenList;
        this.context = context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return dataBeenList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final CityBBSBean.DataBean dataBean = dataBeenList.get(position);
        String picName = dataBean.getPic();
        String[] arrayPic = picName.split("[,]");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.prefecturemy_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + dataBean.getPhoto(),
                viewHolder.ivHead, displayImageOptions);
        switch (arrayPic.length) {
            case 0:
                viewHolder.picLL0.setVisibility(View.GONE);

                viewHolder.ivPic0.setVisibility(View.GONE);
                viewHolder.ivPic1.setVisibility(View.GONE);
                viewHolder.ivPic2.setVisibility(View.GONE);
                break;
            case 1:
                viewHolder.ivPic0.setVisibility(View.VISIBLE);
                viewHolder.ivPic1.setVisibility(View.GONE);
                viewHolder.ivPic2.setVisibility(View.GONE);
                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                        viewHolder.ivPic0, displayImageOptions);
                break;
            case 2:
                viewHolder.ivPic0.setVisibility(View.VISIBLE);
                viewHolder.ivPic1.setVisibility(View.VISIBLE);
                viewHolder.ivPic2.setVisibility(View.GONE);
                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                        viewHolder.ivPic0, displayImageOptions);
                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[1],
                        viewHolder.ivPic1, displayImageOptions);
                break;
            case 3:
                viewHolder.ivPic0.setVisibility(View.VISIBLE);
                viewHolder.ivPic1.setVisibility(View.VISIBLE);
                viewHolder.ivPic2.setVisibility(View.VISIBLE);
                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                        viewHolder.ivPic0, displayImageOptions);
                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[1],
                        viewHolder.ivPic1, displayImageOptions);
                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[2],
                        viewHolder.ivPic2, displayImageOptions);
                break;

        }


        viewHolder.tvName.setText(dataBean.getName());
        viewHolder.tvTitle.setText(dataBean.getTitle());
        viewHolder.tvContent.setText(dataBean.getContent());

        return convertView;
    }

    private class ViewHolder {
        TextView tvName, tvTitle, tvContent;
        ImageView ivHead, ivPic0, ivPic1, ivPic2;
        LinearLayout picLL0;

        public ViewHolder(View view) {
            ivHead = (ImageView) view.findViewById(R.id.iv_prefecture_head);
            tvName = (TextView) view.findViewById(R.id.tv_prefecture_name);
            tvTitle = (TextView) view.findViewById(R.id.tv_prefecture_title);
            tvContent = (TextView) view.findViewById(R.id.tv_prefecture_content);
            ivPic0 = (ImageView) view.findViewById(R.id.tx_prefecture_pic0);
            ivPic1 = (ImageView) view.findViewById(R.id.tx_prefecture_pic1);
            ivPic2 = (ImageView) view.findViewById(R.id.tx_prefecture_pic2);
            picLL0 = (LinearLayout) view.findViewById(R.id.prefecturemy_pic_ll);
        }

    }
}
