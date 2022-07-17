package com.doanhung.multithreading.thread_android.handler_and_looper;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class CustomHandlerThread extends HandlerThread {

    private static final String TAG = "CustomHandlerThread";

    public Handler mHandler;

    public CustomHandlerThread(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Log.i(TAG, "handleMessage: " + Thread.currentThread().getName()  + " " + msg.obj);
                return false;
            }
        });
    }
}
