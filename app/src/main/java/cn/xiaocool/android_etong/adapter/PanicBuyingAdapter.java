package cn.xiaocool.android_etong.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;

/**
 * Created by 潘 on 2016/8/8.
 */
public class PanicBuyingAdapter extends BaseAdapter {
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
            inflater.inflate(R.layout.activity_panic_buying_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }{
            holder = (ViewHolder)convertView.getTag();
        }
        return null;
    }

    static class ViewHolder {
        @BindView(R.id.everyday_choiceness_pic)
        ImageView everydayChoicenessPic;
        @BindView(R.id.everyday_choiceness_name)
        TextView everydayChoicenessName;
        @BindView(R.id.everyday_choiceness_desc)
        TextView everydayChoicenessDesc;
        @BindView(R.id.everyday_choiceness_oprice)
        TextView everydayChoicenessOprice;
        @BindView(R.id.everyday_choiceness_price)
        TextView everydayChoicenessPrice;
        @BindView(R.id.everyday_choiceness_btn_buy)
        Button everydayChoicenessBtnBuy;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
