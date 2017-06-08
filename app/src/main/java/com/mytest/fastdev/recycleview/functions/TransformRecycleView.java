package com.mytest.fastdev.recycleview.functions;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mytest.fastdev.FunModule;
import com.mytest.fastdev.R;
import com.mytest.fastdev.toolbarandtitle.functions.base.TopBarBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/8.
 */

public class TransformRecycleView extends TopBarBaseActivity {

    private RecyclerView recyclerView;
    private List<FunModule> funModuleList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_function_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("变换测试");

        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                TransformRecycleView.this.finish();
            }
        });

        setTopRightButton("Button", R.drawable.ic_mine_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(TransformRecycleView.this));
                    recyclerView.setAdapter(new AAA(funModuleList, 1));
                    updateMenuItemIcon(R.drawable.ic_mine_white_24dp);
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(TransformRecycleView.this, 3));
                    recyclerView.setAdapter(new AAA(funModuleList, 3));
                    updateMenuItemIcon(R.drawable.ic_return_white_24dp);
                }
            }
        });
        initDatas();

        recyclerView = (RecyclerView) findViewById(R.id.id_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AAA(funModuleList, 1));
    }

    private int getImageItemWidth(int rowCount) {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        return screenWidth / rowCount;
    }

    class AAA extends RecyclerView.Adapter<MyViewHolder> {

        private List<FunModule> funModuleList;
        private int rowCount;

        AAA(List<FunModule> funModuleList, int rowCount) {
            this.rowCount = rowCount;
            this.funModuleList = funModuleList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(TransformRecycleView.this).inflate(rowCount > 1 ? R.layout.view_tab_item : R.layout.item_function, parent, false);
            return new MyViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final FunModule m = funModuleList.get(position);
            holder.textView.setText(m.getTitle());
        }

        @Override
        public int getItemCount() {
            return funModuleList.size();
        }
    }

    private void initDatas() {
        for (int i = 0; i < 10; i++) {
            funModuleList.add(new FunModule(('a' + i) + "", TransformRecycleView.class));
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.section_label);
            if (textView == null) {
                textView = (TextView) itemView.findViewById(R.id.tvTitle);
                ViewGroup.LayoutParams lp = itemView.getLayoutParams();
                lp.height = getImageItemWidth(3);
                itemView.setLayoutParams(lp);
            }
        }
    }
}
