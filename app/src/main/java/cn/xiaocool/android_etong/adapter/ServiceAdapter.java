package cn.xiaocool.android_etong.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;

/**
 * Created by 潘 on 2016/8/9.
 */
public class ServiceAdapter extends BaseAdapter {
    private LayoutInflater inflater;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_service_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_restaurant)
        ImageView imgRestaurant;
        @BindView(R.id.tx_restaurant_name)
        TextView txRestaurantName;
        @BindView(R.id.img_shop_pingxing)
        ImageView imgShopPingxing;
        @BindView(R.id.tx_restaurant_distance)
        TextView txRestaurantDistance;
        @BindView(R.id.tx_restaurant_price)
        TextView txRestaurantPrice;
        @BindView(R.id.tx_restaurant_menshijia)
        TextView txRestaurantMenshijia;
        @BindView(R.id.tx_restaurant_yishou)
        TextView txRestaurantYishou;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
