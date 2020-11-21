package com.example.autoding;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity2.class.getName();
    public static final String packageName = "com.alibaba.android.rimet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_yes).setOnClickListener(this);
        open(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                openDing(v.getContext());

        }
    }

    private void openDing(Context context) {
        PackageManager packageManager = getPackageManager();
        PackageInfo pi = null;
        try {
            pi = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);
        List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);
        ResolveInfo resolveInfo = apps.iterator().next();
        if (resolveInfo != null) {
            String className = resolveInfo.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            startActivity(intent);
        }
    }

    private void open(Context mainActivity2) {
        if (!OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(this,
                AccessSer_mrz.class.getName())) {// 判断服务是否开启
            OpenAccessibilitySettingHelper.jumpToSettingPage(this);// 跳转到开启页面
        } else {
            Log.e(TAG, "open: " + "Allery Started");
        }
    }

    public static class OpenAccessibilitySettingHelper {

        //跳转到设置页面无障碍服务开启自定义辅助功能服务
        public static void jumpToSettingPage(Context context) {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        //判断自定义辅助功能服务是否开启
        public static boolean isAccessibilitySettingsOn(Context context, String className) {
            if (context == null) {
                return false;
            }
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                List<ActivityManager.RunningServiceInfo> runningServices =
                        activityManager.getRunningServices(100);// 获取正在运行的服务列表
                if (runningServices.size() < 0) {
                    return false;
                }
                for (int i = 0; i < runningServices.size(); i++) {
                    ComponentName service = runningServices.get(i).service;
                    if (service.getClassName().equals(className)) {
                        return true;
                    }
                }
                return false;
            } else {
                return false;
            }
        }
    }
}