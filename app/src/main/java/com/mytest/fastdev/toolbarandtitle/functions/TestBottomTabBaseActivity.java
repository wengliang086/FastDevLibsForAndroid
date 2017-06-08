package com.mytest.fastdev.toolbarandtitle.functions;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mytest.fastdev.R;
import com.mytest.fastdev.toolbarandtitle.functions.base.BottomTabBaseActivity;
import com.mytest.fastdev.toolbarandtitle.functions.base.TabFragment1;
import com.mytest.fastdev.toolbarandtitle.functions.base.TabFragment2;
import com.mytest.fastdev.toolbarandtitle.functions.base.TabFragment3;
import com.mytest.fastdev.toolbarandtitle.functions.base.TabFragment4;
import com.mytest.fastdev.toolbarandtitle.functions.base.view.BottomTabView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TestBottomTabBaseActivity extends BottomTabBaseActivity {

    @Override
    protected List<BottomTabView.TabItemView> getTabViews() {
        List<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
        tabItemViews.add(new BottomTabView.TabItemView(this, "标题1", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        tabItemViews.add(new BottomTabView.TabItemView(this, "标题2", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        tabItemViews.add(new BottomTabView.TabItemView(this, "标题3", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        tabItemViews.add(new BottomTabView.TabItemView(this, "标题4", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        return tabItemViews;
    }

    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TabFragment1());
        fragments.add(new TabFragment2());
        fragments.add(new TabFragment3());
        fragments.add(new TabFragment4());
        return fragments;
    }

    @Override
    protected View getCenterView() {
        ImageView centerView = new ImageView(this);
        centerView.setImageResource(R.mipmap.ic_launcher_round);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
        layoutParams.leftMargin = 60;
        layoutParams.rightMargin = 60;
        layoutParams.bottomMargin = 0;
        centerView.setLayoutParams(layoutParams);
        centerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestBottomTabBaseActivity.this, "centerView 点击了", Toast.LENGTH_SHORT).show();
            }
        });
//        return centerView;
        return null;
    }
}
