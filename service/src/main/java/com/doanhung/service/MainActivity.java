package com.doanhung.service;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView textView;
    private Button btnStart;
    private Button btnStop;
    private Button btnBind;
    private Button btnUnbind;
    private Button btnGetRandomNumber;


    private Intent serviceIntent;
    private Intent boundIntent;

    private BoundService mBoundService;
    private boolean isServiceBound;
    private ServiceConnection serviceConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intView();

        Log.i(TAG, "onCreate: on thread" + Thread.currentThread().getName());

        serviceIntent = new Intent(this, MyService.class);
        boundIntent = new Intent(this, BoundService.class);

//        btnStart.setOnClickListener(v -> startService(serviceIntent));
//        btnStop.setOnClickListener(v -> stopService(serviceIntent));

        btnStart.setOnClickListener(v -> startService(boundIntent));
        btnStop.setOnClickListener(v -> stopService(boundIntent));
        btnBind.setOnClickListener(v -> bindService());
        btnUnbind.setOnClickListener(v -> unBindService());
        btnGetRandomNumber.setOnClickListener(v -> getRandomNumber());


    }

    @SuppressLint("SetTextI18n")
    private void getRandomNumber() {
        if (isServiceBound) {
            textView.setText("Random Number: " + mBoundService.getRandomNumber());
        } else {
            textView.setText("Service not bound");
        }
    }

    private void unBindService() {
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }

    private void bindService() {
        if (serviceConnection == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    BoundService.MyBinder myBinder = (BoundService.MyBinder) service;
                    mBoundService = myBinder.getService();
                    isServiceBound = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    isServiceBound = false;
                }
            };
        }
        bindService(boundIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void intView() {
        textView = findViewById(R.id.textView);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnBind = findViewById(R.id.btnBind);
        btnUnbind = findViewById(R.id.btnUnbind);
        btnGetRandomNumber = findViewById(R.id.btnGetRandomNumber);
    }
}