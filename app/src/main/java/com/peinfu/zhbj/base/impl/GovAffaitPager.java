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

public class GovAffaitPager extends BasePager {

    public GovAffaitPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        Log.d("mActivity","政务初始化啦~");

        //要给帧布局填充布局对象
        TextView view = new TextView(mActivity);
        view.setText("政务");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);

        flContent.addView(view);

        //修改页面标题
        tvTitle.setText("人口管理");

        //显示菜单按钮
        btnMenu.setVisibility(View.VISIBLE);
    }
}
