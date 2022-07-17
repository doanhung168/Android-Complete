package com.doanhung.multithreading.thread_java.synchronized_object;

import java.util.Random;

public class Thread1 extends Thread{

    private final SharedData mShareData;

    public Thread1(SharedData mShareData) {
        this.mShareData = mShareData;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            synchronized (mShareData) {
                System.out.println("T1 > i > " + i);
                mShareData.mData = random.nextInt(100) + 1;
                System.out.println("T1 > " + mShareData.mData);
                mShareData.notifyAll();

                if(i != 99999) {
                    try {
                        mShareData.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
