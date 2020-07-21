package com.example.day9homework;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.example.day9homework.fragment.DownLoadFragment;
import com.example.day9homework.fragment.HomeFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.fl)
    FrameLayout fl;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        toolBar.setTitle("首页");
        setSupportActionBar(toolBar);
//获得碎片管理器
        fm = getSupportFragmentManager();

        //获得fragment对象
        HomeFragment homeFragment = new HomeFragment();
        DownLoadFragment downLoadFragment = new DownLoadFragment();

        //提交
        fm.beginTransaction().add(R.id.fl, homeFragment).add(R.id.fl, downLoadFragment).hide(downLoadFragment).commit();

        tab.addTab(tab.newTab().setText("首页"));
        tab.addTab(tab.newTab().setText("我的"));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    fm.beginTransaction().show(homeFragment).hide(downLoadFragment).commit();
                    toolBar.setTitle("首页");
                } else {
                    fm.beginTransaction().hide(homeFragment).show(downLoadFragment).commit();
                    toolBar.setTitle("下载");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
