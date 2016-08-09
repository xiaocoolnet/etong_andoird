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
import cn.xiaocool.android_etong.bean.HomePage.EveryDayGoodShopBean;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;


/**
 * Created by wzh on 2016/5/21.
 */
public class EverydayGoodShopAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<EveryDayGoodShopBean.DataBean> dataBeenList;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public EverydayGoodShopAdapter(Context context, List<EveryDayGoodShopBean.DataBean> dataBeenList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataBeenList = dataBeenList;
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
        String picName = dataBeenList.get(position).getPhoto();
//        String[] arrayPic = picName.split("[,]");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.homepage_everyday_goodshop_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + picName,
                    viewHolder.shopPic, displayImageOptions);
            viewHolder.shopTvName.setText(dataBeenList.get(position).getShopname());
            viewHolder.likeTvPeopleNum.setText(dataBeenList.get(position).getFavorite());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + picName,
                    viewHolder.shopPic, displayImageOptions);
            viewHolder.shopTvName.setText(dataBeenList.get(position).getShopname());
            viewHolder.likeTvPeopleNum.setText(dataBeenList.get(position).getFavorite());
        }
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.shop_pic)
        ImageView shopPic;
        @BindView(R.id.shop_tv_name)
        TextView shopTvName;
        @BindView(R.id.like_tv_people_num)
        TextView likeTvPeopleNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
