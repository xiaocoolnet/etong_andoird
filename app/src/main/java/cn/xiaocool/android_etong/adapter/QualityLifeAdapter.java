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

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;


/**
 * Created by wzh on 2016/5/21.
 */
public class QualityLifeAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public QualityLifeAdapter(Context context, List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList) {
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
            convertView = layoutInflater.inflate(R.layout.quality_life_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                    viewHolder.ivGoodPic, displayImageOptions);
            viewHolder.tvGoodDesc.setText(newArrivalDataBeanList.get(position).getDescription());
            viewHolder.tvGoodPrice.setText("¥" + newArrivalDataBeanList.get(position).getPrice());
            viewHolder.tvGoodOprice.setText("¥" + newArrivalDataBeanList.get(position).getOprice());
            viewHolder.tvPayNUm.setText("¥" + newArrivalDataBeanList.get(position).getPayNum());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                    viewHolder.ivGoodPic, displayImageOptions);
            viewHolder.tvGoodDesc.setText(newArrivalDataBeanList.get(position).getDescription());
            viewHolder.tvGoodPrice.setText("¥" + newArrivalDataBeanList.get(position).getPrice());
            viewHolder.tvGoodOprice.setText("¥" + newArrivalDataBeanList.get(position).getOprice());
            viewHolder.tvPayNUm.setText("¥" + newArrivalDataBeanList.get(position).getPayNum());
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView ivGoodPic;
        TextView tvGoodDesc, tvGoodPrice,tvGoodOprice,tvPayNUm;
        public ViewHolder(View view) {
            ivGoodPic = (ImageView) view.findViewById(R.id.new_arrival_good_img);
            tvGoodDesc = (TextView) view.findViewById(R.id.new_arrival_good_desc);
            tvGoodPrice = (TextView) view.findViewById(R.id.new_arrival_good_price);
            tvGoodOprice = (TextView) view.findViewById(R.id.new_arrival_good_oprice);
            tvPayNUm = (TextView) view.findViewById(R.id.quality_life_pauNum);
            tvGoodOprice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        }

    }
}