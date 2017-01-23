package cn.xiaocool.android_etong.fragment;


//import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.HomePage.SearchActivity;
//import cn.xiaocool.android_etong.UI.HomePage.TypeListActivity;
//import cn.xiaocool.android_etong.UI.Main.EverydayBargainActivity;
//import cn.xiaocool.android_etong.UI.Main.EverydayChoicenessActivity;
//import cn.xiaocool.android_etong.UI.Main.FlashSaleActivity;
//import cn.xiaocool.android_etong.UI.Main.NewArrivalActivity;
//import cn.xiaocool.android_etong.UI.Main.QualityLifeActivity;
//import cn.xiaocool.android_etong.UI.Main.ZeroActivity;
//import cn.xiaocool.android_etong.util.IntentUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTabFragment extends Fragment implements View.OnClickListener{



    private TabLayout tab_FindFragment_title;
    private ViewPager vp_FindFragment_pager;

    private FragmentPagerAdapter fAdapter;

    private List<Fragment> list_fragment;
    private List<String> list_title;

    private TextView et_search;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
    }

    private void initView(View view) {

        et_search = (TextView) view.findViewById(R.id.et_search);
        et_search.clearFocus();
        et_search.setOnClickListener(this);

        tab_FindFragment_title = (TabLayout)view.findViewById(R.id.tab_FindFragment_title);
        vp_FindFragment_pager = (ViewPager)view.findViewById(R.id.vp_FindFragment_pager);

        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(new HomepageFragment());
        list_fragment.add(new HomeDividerFragment());
        list_fragment.add(new HomeDividerFragment());
        list_fragment.add(new HomeDividerFragment());

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("首页");
        list_title.add("热门收藏");
        list_title.add("本月热榜");
        list_title.add("今日热榜");

        //设置TabLayout的模式
        tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(2)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(3)));

        fAdapter = new Find_tab_Adapter(getActivity().getSupportFragmentManager(),list_fragment,list_title);

        //viewpager加载adapter
        vp_FindFragment_pager.setAdapter(fAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.et_search:
                Intent intent = new Intent();
                intent.setClass(context, SearchActivity.class);
                intent.putExtra("city", "homepage");
                startActivity(intent);
                break;
        }

    }


    public class Find_tab_Adapter extends FragmentPagerAdapter {

        private List<Fragment> list_fragment;                         //fragment列表
        private List<String> list_Title;                              //tab名的列表



        public Find_tab_Adapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title) {
            super(fm);
            this.list_fragment = list_fragment;
            this.list_Title = list_Title;
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }

        @Override
        public int getCount() {
            return list_Title.size();
        }

        //此方法用来显示tab上的名字
        @Override
        public CharSequence getPageTitle(int position) {

            return list_Title.get(position % list_Title.size());
        }
    }

}
