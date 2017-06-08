package com.mytest.fastdev.toolbarandtitle.functions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mytest.fastdev.R;

/**
 * inflateMenu 和 setNavigationOnClickListener 没有生效，之后再研究
 */
public class ToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setTitle("这里是Title");
//        toolbar.setSubtitle("这里是子标题");
//        toolbar.setLogo(R.drawable.ic_home_black_24dp);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ToolbarActivity.this, "NavigationOnClick", Toast.LENGTH_SHORT).show();
//            }
//        });
        toolbar.inflateMenu(R.menu.navigation);
        
        setSupportActionBar(toolbar);
    }
}
