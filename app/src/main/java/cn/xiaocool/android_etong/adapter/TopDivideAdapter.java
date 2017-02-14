package cn.xiaocool.android_etong.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.HomePage.MenuTypeList;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.util.ImgLoadUtil;

/**
 * Created by xiaocool on 17/2/6.
 */

public class TopDivideAdapter extends ArrayAdapter<String>{

    private int mResourceId;
    private Context mContext;
    private List<MenuTypeList.ChildlistBeanX> childlistBeanXes;
    public TopDivideAdapter(Context context, int resource,List<MenuTypeList.ChildlistBeanX> childlistBeanXes) {
        super(context, resource);
        mContext = context;
        this.mResourceId = resource;
        this.childlistBeanXes = childlistBeanXes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(mResourceId, null);

        TextView textView = (TextView) view.findViewById(R.id.item_text);
        textView.setText(childlistBeanXes.get(position).getName());

        ImageView imageView = (ImageView) view.findViewById(R.id.item_img);
        ImgLoadUtil.display(WebAddress.GETAVATAR+childlistBeanXes.get(position).getPhoto(),imageView);
        return view;
    }

}
