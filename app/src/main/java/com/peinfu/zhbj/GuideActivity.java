package com.peinfu.zhbj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.peinfu.zhbj.utils.PrefUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/6.
 *  新手引导页面
 */

public class GuideActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<ImageView> mImageViewList;
    private LinearLayout llContainer;
    private ImageView ivRedPoint;

    //引导页图片id数组
    private int[] mImageIds = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};

    //小红点移动距离
    private int mPointDis;

    private Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ActionBar actionBar =getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        mViewPager = (ViewPager)findViewById(R.id.vp_guide);
        //mViewPager.setAdapter(arg0);
        llContainer = (LinearLayout)findViewById(R.id.ll_container);
        ivRedPoint = (ImageView)findViewById(R.id.iv_red_point);
        btnStart =(Button)findViewById(R.id.btn_start);


        initData();//先初始化数据
        mViewPager.setAdapter(new GuideAdapter());//再设置数据

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {     //添加监听器 setOnPageChangeListener已过时
            @Override
            public void onPageSelected(int position) {
                //某个页面被选中
                if (position == mImageViewList.size()-1){//最后一个页面显示开始体验的按钮
                    btnStart.setVisibility(View.VISIBLE);
                }else {
                    btnStart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //当页面滑动过程中的回调

                //Log.d("GuideActivity","当前位置： "  +position+"； 移动偏移百分比： "+ positionOffset);

                //更新小红点距离
                int leftMargin = (int)(mPointDis * positionOffset)+position * mPointDis;//计算小红点左当前的左边距
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
                params.leftMargin = leftMargin;//修改左边距

                //重新设置布局参数
                ivRedPoint.setLayoutParams(params);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //页面状态发生变化的回调
            }
        });

        //计算两个圆点的距离
        //移动距离= 第二个圆点的left值-第一个圆点的left值
        //measure -->layout（确定位置） -->draw(activity的onCreate方法执行结束后才会走此流程
       /* mPointDis = llContainer.getChildAt(1).getLeft()-llContainer.getChildAt(0).getLeft();
        Log.d("GuideActivity","圆点距离： "+mPointDis);*/

        //监听layout方法结束的事件，位置确定好之后再获取圆点间距

        //视图树观察者
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //移除监听，避免重复回调
                ivRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //就是layout方法执行结束的回调
                mPointDis = llContainer.getChildAt(1).getLeft()-llContainer.getChildAt(0).getLeft();
                Log.d("GuideActivity","圆点距离： "+mPointDis);

            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新sp,已经不是第一次进入了
                PrefUtils.setBoolean(getApplicationContext(),"is_first_enter",false);

                //跳到主页面
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    //初始化数据
    private void initData(){
        mImageViewList = new ArrayList<ImageView>();
        for (int i = 0;i<mImageIds.length;i++){
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);//通过设置背景，可以让宽高填充布局
            //view.setImageResource(mImageIds[i]);
            mImageViewList.add(view);

            //初始化小圆点
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_gray);//设置图片（shape形状）

            //初始化布局参数，宽高包裹内容，父控件是谁，就是谁声明的布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);


            if (i>0){
                //设置左边距
                params.leftMargin =30;
            }

            point.setLayoutParams(params);
           /* if (i != 0)
                params.leftMargin = 20;
            point.setEnabled(false);
            llContainer.addView(point, params);
*/
            llContainer.addView(point);//给容器添加圆点

        }


    }


    class GuideAdapter extends PagerAdapter{

        //item的个数
        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;
        }

        //初始化item布局
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view =mImageViewList.get(position);
            container.addView(view);
            return view;
        }


        //销毁item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }


}
