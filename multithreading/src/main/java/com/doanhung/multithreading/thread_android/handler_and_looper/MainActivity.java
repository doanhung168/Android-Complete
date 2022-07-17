package com.doanhung.multithreading.thread_android.handler_and_looper;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.doanhung.multithreading.R;
import com.doanhung.multithreading.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding mBinding;

    private boolean mIsRunning;
    private int mCount;

    private Handler mHandler;

    private CounterAsyncTask mCounterAsyncTask;

    private LooperThread mLooperThread;
    private CustomHandlerThread mCustomHandlerThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Log.i(TAG, "Current thread: " + Thread.currentThread().getId()
                + " --- " + Thread.currentThread().getName());

        mBinding.btnStartThread.setOnClickListener(this);
        mBinding.btnStopThread.setOnClickListener(this);

//        mHandler = new Handler(Looper.getMainLooper());

        mLooperThread = new LooperThread();
        mLooperThread.start();

        mCustomHandlerThread = new CustomHandlerThread("CustomHandlerThread");
        mCustomHandlerThread.start();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartThread: {
                mIsRunning = true;

                // by the manual way
//                new Thread(() -> {
//                    while (mIsRunning) {
//                        Log.i(TAG, "Thread id in while loop: " + Thread.currentThread().getId()
//                                + " --- " + Thread.currentThread().getName());
//
//                        try {
//                            mCount++;
//                            //noinspection BusyWait
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                        // the first way
//                        mBinding.tvResult.post(() -> mBinding.tvResult.setText(String.valueOf(mCount)));
//
                        // second way
//                        mHandler.post(() -> mBinding.tvResult.setText(String.valueOf(mCount)));
//
//                        // third way
////                        runOnUiThread(() -> {
////                            mBinding.tvResult.setText(String.valueOf(mCount));
////                        });
//
//                    }
//                }).start();
//
//                // by using asyncTask
////                mCounterAsyncTask = new CounterAsyncTask();
////                mCounterAsyncTask.execute(0);

                Log.i(TAG, "onClick: " + Thread.currentThread().getName());
//                executeOnCustomLooper();
//                executeOnCustomLooperWithCustomHandler();
                executeOnCustomHandlerThread();
//                executeOnCustomHandlerThreadByRunnable();
                break;
            }
            case R.id.btnStopThread: {
                mIsRunning = false;
//                mCounterAsyncTask.cancel(true);
            }
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {

        if(mCustomHandlerThread != null) {
            mCustomHandlerThread.getLooper().quit();
        }

        if(mLooperThread != null && mLooperThread.isAlive()) {
            mLooperThread.mHandler.getLooper().quit();
        }
        mBinding = null;
        super.onDestroy();
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    private class CounterAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        private int mCustomCounter;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomCounter = 0;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            mCustomCounter = integers[0];
            while (mIsRunning) {
                try {
                    mCustomCounter++;
                    publishProgress(mCustomCounter);
                    //noinspection BusyWait
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isCancelled()) {
                    break;
                }
            }
            return mCustomCounter;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mBinding.tvProcess.setText(String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mBinding.tvResult.setText(String.valueOf(integer));
        }

        @Override
        protected void onCancelled(Integer integer) {
            super.onCancelled(integer);
            mBinding.tvResult.setText(String.valueOf(integer));
        }
    }

    public void executeOnCustomLooper() {
        new Thread(() -> {
            Log.i(TAG, "executeOnCustomLooper: " + Thread.currentThread().getName());
            while (mIsRunning) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(1000);
                    mCount++;
                    Message message = new Message();
                    message.obj = mCount;
                    mLooperThread.mHandler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void executeOnCustomLooperWithCustomHandler() {
        mLooperThread.mHandler.post(new Runnable() {
            @Override
            public void run() {
                while (mIsRunning) {
                    try {
                        //noinspection BusyWait
                        Thread.sleep(1000);
                        mCount++;

                        Log.i(TAG, "run: " + Thread.currentThread().getName());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(TAG, "run on Ui Thread: " + Thread.currentThread().getName());
                                mBinding.tvResult.setText(String.valueOf(mCount));
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void executeOnCustomHandlerThread() {
        new Thread(() -> {
            Log.i(TAG, "executeOnCustomLooper: " + Thread.currentThread().getName());
            while (mIsRunning) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(1000);
                    mCount++;
                    Message message = new Message();
                    message.obj = mCount;
                    mCustomHandlerThread.mHandler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void executeOnCustomHandlerThreadByRunnable() {
        mCustomHandlerThread.mHandler.post(new Runnable() {
            @Override
            public void run() {
                while (mIsRunning) {
                    try {
                        //noinspection BusyWait
                        Thread.sleep(1000);
                        mCount++;

                        Log.i(TAG, "run: " + Thread.currentThread().getName());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(TAG, "run on Ui Thread: " + Thread.currentThread().getName());
                                mBinding.tvResult.setText(String.valueOf(mCount));
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}