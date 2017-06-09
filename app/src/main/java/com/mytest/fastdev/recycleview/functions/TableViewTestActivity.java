package com.mytest.fastdev.recycleview.functions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mytest.fastdev.R;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;

public class TableViewTestActivity extends AppCompatActivity {

    private static final String[][] DATA_TO_SHOW = {
            {"This", "is", "a", "test"},
            {"and", "a", "second", "test"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_view_test);

        TableView tableView = (TableView) findViewById(R.id.id_tableView);
        tableView.setColumnCount(4);
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
    }
}
