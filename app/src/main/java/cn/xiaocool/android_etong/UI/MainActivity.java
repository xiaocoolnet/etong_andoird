package cn.xiaocool.android_etong.UI;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.fragment.HomepageFragment;
import cn.xiaocool.android_etong.fragment.LocalFragment;
import cn.xiaocool.android_etong.fragment.MineFragment;
import cn.xiaocool.android_etong.fragment.PrefectureFragment;
import cn.xiaocool.android_etong.fragment.ShoppingFragment;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button mTabs[];
    private HomepageFragment homepageFragment;
    private LocalFragment localFragment;
    private PrefectureFragment prefectureFragment;
    private ShoppingFragment shoppingFragment;
    private MineFragment mineFragment;
    private Fragment[] fragments;
    private FragmentManager fragmentManager;
    private Context context;
    private int index, currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        context = this;
        homepageFragment = new HomepageFragment();
        localFragment = new LocalFragment();
        prefectureFragment = new PrefectureFragment();
        shoppingFragment = new ShoppingFragment();
        mineFragment = new MineFragment();
        fragments = new Fragment[]{homepageFragment, localFragment,prefectureFragment,shoppingFragment,mineFragment};
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, homepageFragment);
        fragmentTransaction.commit();
        initBtn();
    }

    private void initBtn() {
        mTabs = new Button[5];
        mTabs[0] = (Button) findViewById(R.id.btn_home);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (Button) findViewById(R.id.btn_local);
        mTabs[1].setOnClickListener(this);
        mTabs[2] = (Button) findViewById(R.id.btn_prefecture);
        mTabs[2].setOnClickListener(this);
        mTabs[3] = (Button) findViewById(R.id.btn_shopping);
        mTabs[3].setOnClickListener(this);
        mTabs[4] = (Button) findViewById(R.id.btn_mine);
        mTabs[4].setOnClickListener(this);
        mTabs[0].setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                index = 0;
                break;
            case R.id.btn_local:
                index = 1;
                break;
            case R.id.btn_prefecture:
                index = 2;
                break;
            case R.id.btn_shopping:
                index = 3;
                break;
            case R.id.btn_mine:
                index = 4;
                break;
        }

        if (currentIndex != index) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()) {
                fragmentTransaction.add(R.id.fragment_container, fragments[index]);
            }
            fragmentTransaction.show(fragments[index]);
            fragmentTransaction.commit();
        }
        mTabs[currentIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentIndex = index;
    }
}