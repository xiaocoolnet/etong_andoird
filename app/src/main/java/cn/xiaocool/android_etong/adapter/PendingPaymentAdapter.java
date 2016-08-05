package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.Mine.PendingPayment;
import cn.xiaocool.android_etong.net.constant.WebAddress;

/**
 * Created by 潘 on 2016/8/1.
 */
public class PendingPaymentAdapter extends BaseAdapter {
    private List<PendingPayment.DataBean> dataBeans;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private Context context;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public PendingPaymentAdapter(Context context, List<PendingPayment.DataBean> dataBeans) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.dataBeans = dataBeans;
        Log.e("222", "222");
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return dataBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final PendingPayment.DataBean product = dataBeans.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.pendingpayment_item, null);
            holder = new ViewHolder();
            holder.img_shopping_chanpin = (ImageView) convertView.findViewById(R.id.img_shopping_chanpin);
            holder.tx_shopping_cloth_name = (TextView) convertView.findViewById(R.id.tx_shopping_cloth_name);
            holder.tx_shopping_cloth_price = (TextView) convertView.findViewById(R.id.tx_shopping_cloth_price);
            holder.tx_goods_count = (TextView) convertView.findViewById(R.id.tx_goods_count);
            holder.tx_shopping_cloth_color = (TextView) convertView.findViewById(R.id.tx_shopping_cloth_color);
            holder.tx_shopping_cloth_size = (TextView) convertView.findViewById(R.id.tx_shopping_cloth_size);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String pic = product.getPicture();
        String[] arraypic = pic.split("[,]");
        Log.e("pic=", arraypic[0]);
        imageLoader.displayImage(WebAddress.GETAVATAR + arraypic[0], holder.img_shopping_chanpin, displayImageOptions);
        holder.tx_shopping_cloth_price.setText(product.getMoney());
        holder.tx_shopping_cloth_name.setText(product.getGoodsname());
        holder.tx_goods_count.setText(product.getNumber());
        return convertView;
    }

    class ViewHolder {
        ImageView img_shopping_chanpin;
        TextView tx_shopping_cloth_name, tx_shopping_cloth_price, tx_shopping_cloth_oldprice, tx_goods_count;
        CheckBox cb_select;
        RelativeLayout rl_select;
        Button btn_down, btn_up;
        TextView tv_number, tx_shopping_cloth_color, tx_shopping_cloth_size;
    }
}
