package com.peinfu.zhbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.peinfu.zhbj.base.BasePager;

/**
 * Created by Administrator on 2017/3/20.
 */

public class SmartServicePager extends BasePager {
    public SmartServicePager(Activity activity) {
        super(activity);
    }
    @Override
    public void initData() {
        Log.d("mActivity","智慧服务初始化啦~");

        //要给帧布局填充布局对象
        TextView view = new TextView(mActivity);
        view.setText("智慧服务");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);

        flContent.addView(view);

        //修改页面标题
        tvTitle.setText("生活");

        //隐藏菜单按钮
        btnMenu.setVisibility(View.VISIBLE);
    }
}
