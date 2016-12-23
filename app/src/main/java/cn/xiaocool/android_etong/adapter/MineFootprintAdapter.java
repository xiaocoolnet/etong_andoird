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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.MineFootprintActivity;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;
import cn.xiaocool.android_etong.view.SwipeListLayout;

import static android.R.id.list;
import static cn.xiaocool.android_etong.R.id.sll_main;

/**
 * Created by 潘 on 2016/10/10.
 */

public class MineFootprintAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    private Set<SwipeListLayout> sets = new HashSet();

    public MineFootprintAdapter(Context context, List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.newArrivalDataBeanList = newArrivalDataBeanList;
        this.context = context;
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
        final ViewHolder viewHolder;
        final NewArrivalBean.NewArrivalDataBean bean = newArrivalDataBeanList.get(position);
        String picName = newArrivalDataBeanList.get(position).getPicture();
        String[] arrayPic = picName.split("[,]");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.minefootprint_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                viewHolder.ivGoodPic, displayImageOptions);
        viewHolder.tvGoodName.setText(newArrivalDataBeanList.get(position).getGoodsname());
        viewHolder.tvGoodDesc.setText(newArrivalDataBeanList.get(position).getDescription());
        viewHolder.tvGoodPrice.setText("¥" + newArrivalDataBeanList.get(position).getPrice());
//            viewHolder.tvGoodOprice.setText("¥" + newArrivalDataBeanList.get(position).getOprice());
        viewHolder.sll_main.setOnSwipeStatusListener(new MyOnSlipStatusListener(
                viewHolder.sll_main));
//        viewHolder.tv_top.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                viewHolder.sll_main.setStatus(SwipeListLayout.Status.Close, true);
////                String str = list.get(arg0);
////                list.remove(arg0);
////                list.add(0, str);
//                notifyDataSetChanged();
//            }
//        });
        viewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                viewHolder.sll_main.setStatus(SwipeListLayout.Status.Close, true);
//                list.remove(arg0);
                notifyDataSetChanged();
            }
        });
//


//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(
//                    R.layout.minefootprint_item, null);
//        }
//
//        final SwipeListLayout sll_main = (SwipeListLayout) convertView
//                .findViewById(sll_main);
//        TextView tv_top = (TextView) convertView.findViewById(R.id.tv_top);
//        TextView tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
//        sll_main.setOnSwipeStatusListener(new MyOnSlipStatusListener(
//                sll_main));
//        tv_top.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                sll_main.setStatus(SwipeListLayout.Status.Close, true);
////                String str = list.get(arg0);
////                list.remove(arg0);
////                list.add(0, str);
//                notifyDataSetChanged();
//            }
//        });
//        tv_delete.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                sll_main.setStatus(SwipeListLayout.Status.Close, true);
////                list.remove(arg0);
//                notifyDataSetChanged();
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        ImageView ivGoodPic;
        TextView tvGoodDesc, tvGoodPrice, tvGoodOprice, tvGoodName;
        SwipeListLayout sll_main;
//        private final TextView tv_top;
        private final TextView tv_delete;

        public ViewHolder(View view) {
            ivGoodPic = (ImageView) view.findViewById(R.id.everyday_choiceness_pic);
            tvGoodName = (TextView) view.findViewById(R.id.everyday_choiceness_name);
            tvGoodDesc = (TextView) view.findViewById(R.id.everyday_choiceness_desc);
            tvGoodPrice = (TextView) view.findViewById(R.id.everyday_choiceness_price);
            sll_main = (SwipeListLayout) view.findViewById(R.id.sll_main);
//            tv_top = (TextView) view.findViewById(R.id.tv_top);
            tv_delete = (TextView) view.findViewById(R.id.tv_delete);
        }

    }

    class MyOnSlipStatusListener implements SwipeListLayout.OnSwipeStatusListener {

        private SwipeListLayout slipListLayout;

        public MyOnSlipStatusListener(SwipeListLayout slipListLayout) {
            this.slipListLayout = slipListLayout;
        }

        @Override
        public void onStatusChanged(SwipeListLayout.Status status) {
            if (status == SwipeListLayout.Status.Open) {
                //若有其他的item的状态为Open，则Close，然后移除
                if (sets.size() > 0) {
                    for (SwipeListLayout s : sets) {
                        s.setStatus(SwipeListLayout.Status.Close, true);
                        sets.remove(s);
                    }
                }
                sets.add(slipListLayout);
            } else {
                if (sets.contains(slipListLayout))
                    sets.remove(slipListLayout);
            }
        }

        @Override
        public void onStartCloseAnimation() {

        }

        @Override
        public void onStartOpenAnimation() {

        }

    }


}

