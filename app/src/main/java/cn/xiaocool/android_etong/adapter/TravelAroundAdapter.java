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
 * Created by 潘 on 2016/8/10.
 */
public class TravelAroundAdapter extends BaseAdapter {
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
            convertView = inflater.inflate(R.layout.activity_travel_around_item, null);
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
        @BindView(R.id.tv_goods_pingfen)
        TextView tvGoodsPingfen;
        @BindView(R.id.tx_restaurant_price)
        TextView txRestaurantPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
