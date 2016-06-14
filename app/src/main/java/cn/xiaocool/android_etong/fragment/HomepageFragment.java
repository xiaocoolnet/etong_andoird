package cn.xiaocool.android_etong.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.lib.CycleViewPager;
import cn.xiaocool.android_etong.bean.ADInfo;

/**
 * Created by 潘 on 2016/6/12.
 */
public class HomepageFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();    private CycleViewPager cycleViewPager;

    private String[] imageUrls = {"http://hq.xiaocool.net/uploads/microblog/sp1.jpg",
            "http://hq.xiaocool.net/uploads/microblog/sp2.jpg",
            "http://hq.xiaocool.net/uploads/microblog/sp3.jpg",
            "http://hq.xiaocool.net/uploads/microblog/sp4.jpg"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage,container,false);
        context = getActivity();
//        configImageLoader();
//        initialize();
        return view;
    }

//    @SuppressLint("NewApi")
//    private void initialize() {
//
//        cycleViewPager = (CycleViewPager) getFragmentManager()
//                .findFragmentById(R.id.fragment_cycle_viewpager_content_homepage);
//
//        for(int i = 0; i < imageUrls.length; i ++){
//            ADInfo info = new ADInfo();
//            info.setUrl(imageUrls[i]);
//            info.setContent("图片-->" + i );
//            infos.add(info);
//        }
//
//        // 将最后一个ImageView添加进来
//        views.add(ViewFactory.getImageView(context, infos.get(infos.size() - 1).getUrl()));
//        for (int i = 0; i < infos.size(); i++) {
//            views.add(ViewFactory.getImageView(context, infos.get(i).getUrl()));
//        }
//        // 将第一个ImageView添加进来
//        views.add(ViewFactory.getImageView(context, infos.get(0).getUrl()));
//
//        // 设置循环，在调用setData方法前调用
//        cycleViewPager.setCycle(true);
//
//        // 在加载数据前设置是否循环
//        cycleViewPager.setData(views, infos, mAdCycleViewListener);
//        //设置轮播
//        cycleViewPager.setWheel(true);
//
//        // 设置轮播时间，默认5000ms
//        cycleViewPager.setTime(3000);
//        //设置圆点指示图标组居中显示，默认靠右
//        cycleViewPager.setIndicatorCenter();
//    }
//
//    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {
//
//        @Override
//        public void onImageClick(ADInfo info, int position, View imageView) {
//            if (cycleViewPager.isCycle()) {
//                position = position - 1;
//                Toast.makeText(context,
//                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
//                        .show();
//            }
//
//        }
//
//    };
//
//    /**
//     * 配置ImageLoder
//     */
//    private void configImageLoader() {
//        // 初始化ImageLoader
//        @SuppressWarnings("deprecation")
//        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
//                .showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
//                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
//                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
//                .build(); // 创建配置过得DisplayImageOption对象
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).defaultDisplayImageOptions(options)
//                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
//                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
//        ImageLoader.getInstance().init(config);
//    }
    @Override
    public void onClick(View v) {

    }
}
