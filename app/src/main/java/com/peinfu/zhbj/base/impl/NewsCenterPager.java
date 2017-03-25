package com.peinfu.zhbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.peinfu.zhbj.MainActivity;
import com.peinfu.zhbj.base.BaseMenuDetailPager;
import com.peinfu.zhbj.base.BasePager;
import com.peinfu.zhbj.base.impl.menu.InteractMenuDetailPager;
import com.peinfu.zhbj.base.impl.menu.NewsMenuDetailPager;
import com.peinfu.zhbj.base.impl.menu.PhotosMenuDetailPager;
import com.peinfu.zhbj.base.impl.menu.TopicMenuDetailPager;
import com.peinfu.zhbj.domain.NewsMenu;
import com.peinfu.zhbj.fragment.LeftMenuFragment;
import com.peinfu.zhbj.global.GlobalConstants;
import com.peinfu.zhbj.utils.CacheUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/20.
 */

public class NewsCenterPager extends BasePager {

    private ArrayList<BaseMenuDetailPager> mMenuDetailPagers;//菜单详情页的集合
    private NewsMenu mNewsData;// 分类信息网络数据

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        Log.d("mActivity","新闻中心初始化啦~");

        /*//要给帧布局填充布局对象
        TextView view = new TextView(mActivity);
        view.setText("新闻中心");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);

        flContent.addView(view);*/

        //修改页面标题
        tvTitle.setText("新闻");

        //显示菜单按钮
        btnMenu.setVisibility(View.VISIBLE);

        //先判断有没有缓存，如果有的话，就加载缓存
        String cache = CacheUtils.getCache(GlobalConstants.CATEGORY_URL,mActivity);
        if (!TextUtils.isEmpty(cache)){
            Log.d("mActivity","发现缓存啦~");
            processData(cache);
        }
            //请求服务器，获取数据
            //开源框架：XUtils
            getDataFromServer();



    }

    /*
    * 从服务器获取数据
    * 需要网络权限
    * */

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpMethod.GET, GlobalConstants.CATEGORY_URL, new RequestCallBack<Object>() {


            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                //请求成功
                String result = (String) responseInfo.result;//获取服务器返回结果
                Log.d("mActivity","服务器返回结果： "+result);

                //JsonObject,Gson
                processData(result);

                // 写缓存
                CacheUtils.setCache(GlobalConstants.CATEGORY_URL,result, mActivity);
            }

            @Override
            public void onFailure(HttpException error, String s) {
                //请求失败
                error.printStackTrace();
                Toast.makeText(mActivity,s,Toast.LENGTH_SHORT).show();

            }
        });
    }
    /*
    * 解析数据
    * */
    private void processData(String json) {

        //Gson ： Google Json
        Gson gson = new Gson();
        mNewsData = gson.fromJson(json, NewsMenu.class);

        Log.d("mActivity","解析结果: "+mNewsData);

        //获取侧边栏对象
        MainActivity mainUI = (MainActivity)mActivity;
        LeftMenuFragment fragment =  mainUI.getLeftMenuFragment();

        //给侧边栏设置数据
        fragment.setMenuData(mNewsData.data);


        // 初始化4个菜单详情页
        mMenuDetailPagers = new ArrayList<BaseMenuDetailPager>();
        mMenuDetailPagers.add(new NewsMenuDetailPager(mActivity));
        mMenuDetailPagers.add(new TopicMenuDetailPager(mActivity));
        mMenuDetailPagers.add(new PhotosMenuDetailPager(mActivity));
        mMenuDetailPagers.add(new InteractMenuDetailPager(mActivity));

        // 将新闻菜单详情页设置为默认页面
        setCurrentDetailPager(0);

    }
    //设置菜单详情页
    public void setCurrentDetailPager(int position){
        /// 重新给frameLayout添加内容
        BaseMenuDetailPager pager = mMenuDetailPagers.get(position);// 获取当前应该显示的页面
        View view = pager.mRootView;// 当前页面的布局

        // 清除之前旧的布局
        flContent.removeAllViews();

        flContent.addView(view);// 给帧布局添加布局

        // 初始化页面数据
        pager.initData();

        //更新标题
        tvTitle.setText(mNewsData.data.get(position).title);

    }
}
