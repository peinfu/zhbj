package com.peinfu.zhbj;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.peinfu.zhbj.fragment.ContentFragment;
import com.peinfu.zhbj.fragment.LeftMenuFragment;

import org.xutils.x;

/**
 * Created by Administrator on 2017/3/6.
 *
 * 主页面
 */

public class MainActivity extends SlidingFragmentActivity{
    private static final String TAG_LEFT_MENU = "TAG_LEFT_MENU";
    private static final String TAG_CONTENT = "TAG_CONTENT";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.Ext.init(getApplication());
        setContentView(R.layout.activity_main);
        /*ActionBar actionBar =getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }*/

        setBehindContentView(R.layout.left_menu);

        SlidingMenu menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//全屏触摸
        menu.setBehindOffset(400);//屏幕预留400像素宽度
        //menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        initFragment();

    }

    private void initFragment(){
        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction transaction =fm.beginTransaction();//开始事务
        transaction.replace(R.id.fl_left_menu,new LeftMenuFragment(),"TAG_LEFT_MENU");
        //用fragment替换帧布局：参数1，帧布局容器的id，参数2是要替换的fragment  参数3,tag 标记(可加可不加）
        transaction.replace(R.id.fl_main,new ContentFragment(),"TAG_CONTENT");

        transaction.commit();//提交事务

        //Fragment fragment =fm.findFragmentByTag(TAG_LEFT_MENU);//根据标记找到对应的Fragment

    }

    //H获取侧边栏fragment对象
    public LeftMenuFragment getLeftMenuFragment(){
        FragmentManager fm =getSupportFragmentManager();
        LeftMenuFragment fragment = (LeftMenuFragment)fm.findFragmentByTag(TAG_LEFT_MENU);//根据标记找到对应的Fragment
        return fragment;
    }

    // 获取主页fragment对象
    public ContentFragment getContentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        ContentFragment fragment = (ContentFragment) fm.findFragmentByTag(TAG_CONTENT);// 根据标记找到对应的fragment
        return fragment;
    }
}

