package com.mytest.fastdev.recycleview.functions;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mytest.fastdev.R;

import java.util.Comparator;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;

public class TableViewTestActivity extends AppCompatActivity {

    private static final String[][] DATA_TO_SHOW = {
            {"This", "is", "a", "test"},
            {"and", "a", "second", "test"},
            {"This", "is", "a", "test"},
            {"and", "a", "second", "test"},
            {"This", "is", "a", "test"},
            {"and", "a", "second", "test"},
            {"This", "is", "a", "test"},
            {"and", "a", "second", "test"},
            {"This", "is", "a", "test"},
            {"and", "a", "second", "test"},
            {"This", "is", "a", "test"},
            {"and", "a", "second", "test"},
            {"This", "is", "a", "test"},
            {"and", "a", "second", "test"},
            {"This", "is", "a", "test"},
            {"and", "a", "second", "test"},
            {"This", "is", "a", "test"},
            {"and", "a", "second", "test"},
            {"This", "is", "a", "test"},
            {"and", "a", "second", "test"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_view_test);

        SortableTableView<String[]> tableView = (SortableTableView) findViewById(R.id.id_tableView);

        SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(this, "h1", "h2", "h3", "h4");
        simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(this, R.color.table_header_text));
        tableView.setHeaderAdapter(simpleTableHeaderAdapter);

        final int rowColorEven = ContextCompat.getColor(this, R.color.table_data_row_even);
        final int rowColorOdd = ContextCompat.getColor(this, R.color.table_data_row_odd);
        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(rowColorEven, rowColorOdd));
        tableView.setHeaderSortStateViewProvider(SortStateViewProviders.brightArrows());

        tableView.setColumnComparator(0, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return o1[0].compareTo(o2[0]);
            }
        });

        tableView.setColumnCount(4);
//        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
        tableView.setDataAdapter(new StringAdapter(this, DATA_TO_SHOW));
    }

    private class StringAdapter extends TableDataAdapter<String[]> {

        private static final int TEXT_SIZE = 14;

        public StringAdapter(Context context, String[][] data) {
            super(context, data);
        }

        @Override
        public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
            return renderString(getRowData(rowIndex)[columnIndex]);
        }

        private View renderString(final String value) {
            final TextView textView = new TextView(getContext());
            textView.setText(value);
            textView.setPadding(20, 10, 20, 10);
            textView.setTextSize(TEXT_SIZE);
            return textView;
        }
    }
}
