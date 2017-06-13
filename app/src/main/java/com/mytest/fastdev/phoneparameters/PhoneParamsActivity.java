package com.mytest.fastdev.phoneparameters;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import com.mytest.fastdev.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class PhoneParamsActivity extends AppCompatActivity {

    private static final String TAG = "PhoneParamsActivity";
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
                new MyAsyncTask(PhoneParamsActivity.this).execute();
                sendEmptyMessageDelayed(0, 500);
            }
        }.sendEmptyMessageDelayed(0, 500);
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        private long sdAvailableSize;
        private long systemAvailableSize;

        public MyAsyncTask(PhoneParamsActivity activity) {
            this.activity = activity;
        }

        PhoneParamsActivity activity;
        long maxAppMemory;
        long sdCardMemory;
        String cpuName;
        String cpuRateDesc;
        long totalMemory;
        int[] runningAppProcessInfo;
        String ratio;

        @Override
        protected Void doInBackground(Void... params) {
            String TAG = "infoaa";
            Log.i(TAG, "getPhoneDatas: " + getTotalMemory());
            Log.i(TAG, "getPhoneDatas: " + getAvailMemory());
            Log.i(TAG, "getPhoneDatas: " + getNumCores());
            Log.i(TAG, "getPhoneDatas: " + getCpuFrequence());
            Log.i(TAG, "getPhoneDatas: " + getRomMemroy());
            Log.i(TAG, "getPhoneDatas: " + getVersion());
            Log.i(TAG, "getPhoneDatas: " + getSDCardMemory());
            Log.i(TAG, "getPhoneDatas: " + getCpuName());
            Log.i(TAG, "getPhoneDatas: " + getMinCpuFreq());
            Log.i(TAG, "getPhoneDatas: " + getCurCpuFreq());

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
            activity.show(cpuName, cpuRateDesc, sdCardMemory, sdAvailableSize, totalMemory, maxAppMemory, runningAppProcessInfo, ratio);
        }
    }

    void show(String cpuName, String cpuRateDesc, long sdCardMemory, long sdAvailableSize, long totalMemory, long maxAppMemory, int[] runningAppProcessInfo, String ratio) {
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
        phone_ratio.setText(ratio);
    }

    public static long getCpuFrequence() {
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = reader.readLine();
//            return StringUtils.parseLongSafe(line, 10, 0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public long[] getRomMemroy() {
        long[] romInfo = new long[2];
        //Total rom memory
        romInfo[0] = getTotalInternalMemorySize();

        //Available rom memory
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        romInfo[1] = blockSize * availableBlocks;
        getVersion();
        return romInfo;
    }

    public String[] getVersion() {
        String[] version = {"null", "null", "null", "null"};
        String str1 = "/proc/version";
        String str2;
        String[] arrayOfString;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            version[0] = arrayOfString[2];//KernelVersion
            localBufferedReader.close();
        } catch (IOException e) {
        }
        version[1] = Build.VERSION.RELEASE;// firmware version
        version[2] = Build.MODEL;//model
        version[3] = Build.DISPLAY;//system version
        return version;
    }

    public long[] getSDCardMemory() {
        long[] sdCardInfo = new long[2];
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long bSize = sf.getBlockSize();
            long bCount = sf.getBlockCount();
            long availBlocks = sf.getAvailableBlocks();

            sdCardInfo[0] = bSize * bCount;//总大小
            sdCardInfo[1] = bSize * availBlocks;//可用大小
        }
        return sdCardInfo;
    }

    public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getAvailMemory() {// 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存

        return Formatter.formatFileSize(getBaseContext(), mi.availMem);// 将获取的内存大小规格化
    }

    //CPU个数
    private int getNumCores() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
//            Log.d(TAG, "CPU Count: "+files.length);
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            //Print exception
//            Log.d(TAG, "CPU Count: Failed.");
            e.printStackTrace();
            //Default to return 1 core
            return 1;
        }
    }

    private String getTotalMemory() {
        try {
            String str1 = "/proc/meminfo";// 系统内存信息文件
            String str2;
            String[] arrayOfString;
            long initial_memory = 0;
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("//s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "/t");
            }
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
            return Formatter.formatFileSize(getBaseContext(), initial_memory);// Byte转换为KB或者MB，内存大小规格化
        } catch (Exception e) {
            Log.e(TAG, "getTotalMemory: ", e);
        }
        return "00";
    }

}
