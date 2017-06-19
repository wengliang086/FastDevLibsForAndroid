package com.mytest.fastdev;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.mytest.fastdev.mydatepicker.DatePicker;

import java.util.Calendar;

public class DatePickerActivity extends AppCompatActivity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        findViewById(R.id.id_ymd).setOnTouchListener(this);
        findViewById(R.id.id_ym).setOnTouchListener(this);
        findViewById(R.id.id_y).setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            switch (v.getId()) {
                case R.id.id_ymd:
                    showDatePickerDialog((TextView) v);
                    break;
                case R.id.id_ym:
                    showDatePickerDialog2((TextView) v);
                    break;
                case R.id.id_y:
                    showDatePickerDialog3((TextView) v);
                    break;
            }
        }
        return false;
    }

    private void showDatePickerDialog3(final TextView textView) {
        String dateStr = textView.getText().toString();

        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        if (dateStr.length() == 4) {
            year = Integer.parseInt(dateStr);
        }

        DatePicker picker = new DatePicker(this, DatePicker.YEAR);
        picker.setGravity(Gravity.CENTER);
        picker.setWidth((int) (picker.getScreenWidthPixels() * 0.6));
        picker.setRangeStart(2016, 10, 14);
        picker.setRangeEnd(2020, 11, 11);
        picker.setSelectedItem(year, 1);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                textView.setText(year);
            }
        });
        picker.show();
    }

    private void showDatePickerDialog2(final TextView textView) {
        String dateStr = textView.getText().toString();
        String[] array = dateStr.split("-");

        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        if (array.length == 2) {
            year = Integer.parseInt(array[0]);
            month = Integer.parseInt(array[1]);
        }

        DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH);
        picker.setGravity(Gravity.CENTER);
        picker.setWidth((int) (picker.getScreenWidthPixels() * 0.6));
        picker.setRangeStart(2016, 10, 14);
        picker.setRangeEnd(2020, 11, 11);
        picker.setSelectedItem(year, month);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                textView.setText(year + "-" + month);
            }
        });
        picker.show();
    }

    private void showDatePickerDialog(final TextView textView) {
        String dateStr = textView.getText().toString();
        String[] array = dateStr.split("-");

        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        if (array.length == 3) {
            year = Integer.parseInt(array[0]);
            month = Integer.parseInt(array[1]) - 1;
            dayOfMonth = Integer.parseInt(array[2]);
        }
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                textView.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        }, year, month, dayOfMonth);
        dialog.show();
    }
}
