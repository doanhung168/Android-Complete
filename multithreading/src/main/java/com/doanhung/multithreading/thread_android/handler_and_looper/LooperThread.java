package com.doanhung.multithreading.thread_android.handler_and_looper;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class LooperThread extends Thread{
    private static final String TAG = "LooperThread";

    public Handler mHandler;

    @SuppressLint("HandlerLeak")
    @Override
    public void run() {
        Looper.prepare();

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "Thread name: " + Thread.currentThread().getName() + " , count: " + msg.obj);
            }
        };

        Looper.loop();
    }
}
