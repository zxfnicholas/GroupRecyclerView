package com.che.grouprecycleview.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * Created by yutianran on 16/2/27.
 */
public class ThreadUtil {

    public static Handler uiHandler = new Handler(Looper.getMainLooper());
    public static HandlerThread bgThread = new HandlerThread(ThreadUtil.class.getName(), 10);
    public static Handler bgHandler = new Handler(bgThread.getLooper());

    static {
        uiHandler = new Handler(Looper.getMainLooper());
        bgThread = new HandlerThread(ThreadUtil.class.getName(), 10);
        bgHandler = new Handler(bgThread.getLooper());
    }

}
