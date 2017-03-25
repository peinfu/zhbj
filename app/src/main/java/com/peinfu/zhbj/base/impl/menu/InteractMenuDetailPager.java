package com.peinfu.zhbj.base.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.peinfu.zhbj.base.BaseMenuDetailPager;

/**
 * Created by Administrator on 2017/3/25.
 */

public class InteractMenuDetailPager extends BaseMenuDetailPager {
    public InteractMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {

        //要给帧布局填充布局对象
        TextView view = new TextView(mActivity);
        view.setText("菜单详情页-组合");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        return view;
    }
}
