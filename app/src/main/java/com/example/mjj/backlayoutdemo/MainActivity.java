package com.example.mjj.backlayoutdemo;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.slidingfinishactivity.SwipeBackActivity;
import com.example.slidingfinishactivity.utils.PreferenceUtils;
import com.example.slidingfinishactivity.view.SwipeBackLayout;

/**
 * Description：仿微信、简书向右滑动关闭Activity
 * <p>
 * Created by Mjj on 2016/12/11.
 */
public class MainActivity extends SwipeBackActivity implements View.OnClickListener {

    private SwipeBackLayout mSwipeBackLayout;
    private int SlideFlag = SwipeBackLayout.EDGE_LEFT;
    private String mKeyTrackingMode = "key_sliding_close_act"; // 保存设置的滑动关闭activity的模式
    private Toolbar mToolbar;
    private Button btnStart;

    private int[] mBgColors; // 每次启动新界面,改变Toolbar的颜色
    private static int mBgIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        changeActionBarColor();
        // 获得实例
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动模式
        mSwipeBackLayout.setEdgeTrackingEnabled(SlideFlag);
        // 保存滑动模式
        saveTrackingMode(SlideFlag);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreTrackingMode();
    }

    private void saveTrackingMode(int flag) {
        PreferenceUtils.setPrefInt(getApplicationContext(), mKeyTrackingMode, flag);
    }

    /**
     * 恢复模式
     */
    private void restoreTrackingMode() {
        int flag = PreferenceUtils.getPrefInt(getApplicationContext(), mKeyTrackingMode,
                SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setEdgeTrackingEnabled(flag);
    }

    /**
     * 改变状态栏颜色
     */
    private void changeActionBarColor() {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColors()[mBgIndex]));
        mBgIndex++;
        if (mBgIndex >= getColors().length) {
            mBgIndex = 0;
        }
    }

    private int[] getColors() {
        if (mBgColors == null) {
            mBgColors = new int[]{
                    ContextCompat.getColor(MainActivity.this, R.color.androidColorA),
                    ContextCompat.getColor(MainActivity.this, R.color.androidColorB),
                    ContextCompat.getColor(MainActivity.this, R.color.androidColorC),
                    ContextCompat.getColor(MainActivity.this, R.color.androidColorD),
                    ContextCompat.getColor(MainActivity.this, R.color.androidColorE),
            };
        }
        return mBgColors;
    }

    @Override
    public void onClick(View view) {
        // 启动新的界面
        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

}
