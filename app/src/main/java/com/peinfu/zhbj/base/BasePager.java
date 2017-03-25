package com.peinfu.zhbj.base;

import android.app.Activity;
import android.media.Image;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.peinfu.zhbj.MainActivity;
import com.peinfu.zhbj.R;

/**
 * Created by Administrator on 2017/3/20.
 *
 * 五个标签页的基本类
 */

public class BasePager {

    public  Activity mActivity;
    public TextView tvTitle;
    public ImageButton btnMenu;
    public FrameLayout flContent;//空的帧布局对象，要添加动态布局

    public View mRootView;//当前页面的布局对象

    public BasePager(Activity activity){
        mActivity = activity;
        mRootView = initView();
    }

    //初始化布局
    public View initView(){
        View view = View.inflate(mActivity, R.layout.base_pager,null);
        tvTitle = (TextView)view.findViewById(R.id.tv_title);
        btnMenu = (ImageButton)view.findViewById(R.id.btn_menu);
        flContent = (FrameLayout)view.findViewById(R.id.fl_content);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        return view;
    }

    private void toggle() {
        MainActivity mainUI =(MainActivity)mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        slidingMenu.toggle();//如果当前状态是开，调用后就关，反之亦然

    }

    //初始化数据
    public void initData(){

    }
}
