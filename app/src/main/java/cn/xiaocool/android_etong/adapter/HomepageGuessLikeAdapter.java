package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
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
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;


/**
 * Created by wzh on 2016/5/21.
 */
public class HomepageGuessLikeAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public HomepageGuessLikeAdapter(Context context, List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.newArrivalDataBeanList = newArrivalDataBeanList;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return newArrivalDataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return newArrivalDataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        String picName = newArrivalDataBeanList.get(position).getPicture();
        String[] arrayPic = picName.split("[,]");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.homepage_guess_ulike_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                    viewHolder.homepageGuessLikeImage, displayImageOptions);
            viewHolder.newArrivalGoodDesc.setText(newArrivalDataBeanList.get(position).getDescription());
            viewHolder.homepageGuessLikePrice.setText("¥" + newArrivalDataBeanList.get(position).getPrice());
            viewHolder.homepageGuessLikePayNum.setText("¥" + newArrivalDataBeanList.get(position).getPayNum());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                    viewHolder.homepageGuessLikeImage, displayImageOptions);
            viewHolder.newArrivalGoodDesc.setText(newArrivalDataBeanList.get(position).getDescription());
            viewHolder.homepageGuessLikePrice.setText("¥" + newArrivalDataBeanList.get(position).getPrice());
            viewHolder.homepageGuessLikePayNum.setText("¥" + newArrivalDataBeanList.get(position).getPayNum());
        }
        return convertView;
    }

    static



    class ViewHolder {
        @BindView(R.id.homepage_guess_like_image)
        ImageView homepageGuessLikeImage;
        @BindView(R.id.new_arrival_good_desc)
        TextView newArrivalGoodDesc;
        @BindView(R.id.homepage_guess_like_price)
        TextView homepageGuessLikePrice;
        @BindView(R.id.homepage_guess_like_payNum)
        TextView homepageGuessLikePayNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
