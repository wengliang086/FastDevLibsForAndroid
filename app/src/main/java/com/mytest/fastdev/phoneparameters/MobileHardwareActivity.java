package com.mytest.fastdev.phoneparameters;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.mytest.fastdev.R;

public class MobileHardwareActivity extends AppCompatActivity {

    private TextView phone_cpu;
    private TextView phone_cpuload;
    private TextView phone_maxdisk;
    private TextView phone_freedisk;
    private TextView phone_mem;
    private TextView phone_ratio;
    private TextView phone_maxmem;
    private TextView phone_freemem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_hardware);
        phone_cpu = ((TextView) findViewById(R.id.phone_cpu));
        phone_cpuload = ((TextView) findViewById(R.id.phone_cpuload));
        phone_maxdisk = ((TextView) findViewById(R.id.phone_maxdisk));
        phone_freedisk = ((TextView) findViewById(R.id.phone_freedisk));
        phone_mem = ((TextView) findViewById(R.id.phone_mem));
        phone_maxmem = ((TextView) findViewById(R.id.phone_maxmem));
        phone_freemem = ((TextView) findViewById(R.id.phone_freemem));
        phone_ratio = ((TextView) findViewById(R.id.phone_ratio));

        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                new MyAsyncTask(MobileHardwareActivity.this).execute();
                sendEmptyMessageDelayed(0, 500);
            }
        }.sendEmptyMessageDelayed(0, 500);
    }

    public static class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        private long sdAvailableSize;
        private long systemAvailableSize;

        public MyAsyncTask(MobileHardwareActivity activity) {
            this.activity = activity;
        }

        MobileHardwareActivity activity;
        static long maxAppMemory;
        static long sdCardMemory;
        static String cpuName;
        static String cpuRateDesc;
        static long totalMemory;
        static int[] runningAppProcessInfo;
        static String ratio;

        @Override
        protected Void doInBackground(Void... params) {
            Application application = activity.getApplication();
            maxAppMemory = SysUtil.getMaxAppMemory(application);
            sdCardMemory = SysUtil.getSDCardMemory();
            systemAvailableSize = MemorySpaceCheck.getSystemAvailableSize();
            cpuName = SysUtil.getCpuInfo();
            cpuRateDesc = SysUtil.getCPURateDesc();
            totalMemory = SysUtil.getTotalMemory(application);
            runningAppProcessInfo = SysUtil.getRunningAppProcessInfo(application);
            ratio = SysUtil.getRatio(activity.getWindowManager());
            sdAvailableSize = MemorySpaceCheck.getSDAvailableSize();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            activity.show(cpuName, cpuRateDesc, sdCardMemory, sdAvailableSize, totalMemory, maxAppMemory, runningAppProcessInfo);
        }
    }

    void show(String cpuName, String cpuRateDesc, long sdCardMemory, long sdAvailableSize, long totalMemory, long maxAppMemory, int[] runningAppProcessInfo) {
        Log.i("SysUtil", "内存" + totalMemory);
        phone_cpu.setText(cpuName);
        phone_cpuload.setText(cpuRateDesc);
        phone_maxdisk.setText(SysUtil.formatSize(sdCardMemory));
        phone_freedisk.setText(SysUtil.formatSize(sdAvailableSize));
        phone_mem.setText(SysUtil.formatSize(totalMemory));
        phone_maxmem.setText(maxAppMemory + "MB");
        phone_freemem.setText(SysUtil.formatSize(runningAppProcessInfo[0])
                + "||" + SysUtil.formatSize(runningAppProcessInfo[1])
                + "||" + SysUtil.formatSize(runningAppProcessInfo[2])
                + "||" + SysUtil.formatSize(runningAppProcessInfo[3])
                + "||" + SysUtil.formatSize(runningAppProcessInfo[4])
                + "||" + SysUtil.formatSize(runningAppProcessInfo[5])
                + "||" + SysUtil.formatSize(runningAppProcessInfo[6])
                + "||" + SysUtil.formatSize(runningAppProcessInfo[7])
                + "||" + SysUtil.formatSize(runningAppProcessInfo[8]));
        phone_ratio.setText(MyAsyncTask.ratio);
    }
}
