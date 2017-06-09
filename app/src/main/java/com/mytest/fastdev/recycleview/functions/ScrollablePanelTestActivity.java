package com.mytest.fastdev.recycleview.functions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kelin.scrollablepanel.library.ScrollablePanel;
import com.mytest.fastdev.R;

import java.util.ArrayList;
import java.util.List;

public class ScrollablePanelTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable_panel_test);

        TestPanelAdapter testPanelAdapter = new TestPanelAdapter(createData());
        ScrollablePanel scrollablePanel = (ScrollablePanel) findViewById(R.id.id_scrollable_panel);
        scrollablePanel.setPanelAdapter(testPanelAdapter);
    }

    private List<List<String>> createData() {
        List<List<String>> datas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                row.add(i + "-" + j);
            }
            datas.add(row);
        }
        return datas;
    }

}
