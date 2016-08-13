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
 * Created by 潘 on 2016/8/10.
 */
public class NewUsersAdapter extends BaseAdapter {
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
            convertView = inflater.inflate(R.layout.activity_new_user_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.everyday_choiceness_pic)
        ImageView everydayChoicenessPic;
        @BindView(R.id.everyday_choiceness_name)
        TextView everydayChoicenessName;
        @BindView(R.id.tv_distence)
        TextView tvDistence;
        @BindView(R.id.everyday_choiceness_desc)
        ImageView everydayChoicenessDesc;
        @BindView(R.id.everyday_choiceness_price)
        TextView everydayChoicenessPrice;
        @BindView(R.id.btn_lijian)
        Button btnLijian;
        @BindView(R.id.everyday_choiceness_btn_buy)
        Button everydayChoicenessBtnBuy;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
