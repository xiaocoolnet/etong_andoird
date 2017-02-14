package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.HomePage.TypeGoodsList;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.util.ImgLoadUtil;
import cn.xiaocool.android_etong.util.StringJoint;

/**
 * Created by 潘 on 2016/8/8.
 */
public class DivideListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<TypeGoodsList> typeGoodsLists;
    public DivideListAdapter(Context context,List<TypeGoodsList> typeGoodsLists ) {

        this.typeGoodsLists = typeGoodsLists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return typeGoodsLists.size();
    }

    @Override
    public Object getItem(int position) {
        return typeGoodsLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_divide_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TypeGoodsList good = typeGoodsLists.get(position);

        String[] imgs = good.getPicture().split("[,]");
        if (imgs.length>0){
            ImgLoadUtil.display(WebAddress.GETAVATAR + imgs[0],holder.itemImg);
        }



        holder.itemDescrpTv.setText(good.getGoodsname());
        holder.itemPriceTv.setText("¥" + good.getPrice());
        holder.itemNumTv.setText("已购" + good.getPaynum() + "件");
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.item_descrp_tv)
        TextView itemDescrpTv;
        @BindView(R.id.item_price_tv)
        TextView itemPriceTv;
        @BindView(R.id.item_num_tv)
        TextView itemNumTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
