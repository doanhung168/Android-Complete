package com.doanhung.multithreading.thread_java.synchronized_object;

public class Thread2 extends Thread {

    private final SharedData mShareData;

    public Thread2(SharedData mShareData) {
        this.mShareData = mShareData;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            synchronized (mShareData) {
                mShareData.notifyAll();
                try {
                    mShareData.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("T2 > i > " + i);
                int result = mShareData.mData * mShareData.mData;
                System.out.println("T2 > " + result);

            }
        }

    }
}
