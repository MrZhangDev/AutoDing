package com.example.autoding;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class AccessSer_mrz extends AccessibilityService {

    private static final String TAG = MainActivity2.class.getName();
    private String aid;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int action = event.getEventType();
        switch (action) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                AccessibilityNodeInfo rootWin = this.getRootInActiveWindow();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    aid = MainActivity2.packageName + ":id/buttom";
                    List<AccessibilityNodeInfo> accessList = rootWin.findAccessibilityNodeInfosByViewId(aid);
                    for (AccessibilityNodeInfo a : accessList) {
                        Log.e(TAG, "onAccessibilityEvent: " + a.getText());
                    }

                }
        }

    }

    @Override
    public void onInterrupt() {

    }
}
