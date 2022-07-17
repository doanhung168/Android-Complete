package com.doanhung.multithreading.thread_java.join_thread;

public class ClientTest {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        MyThread t3 = new MyThread();

        t1.setName("t1");
        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.setName("t2");
        t3.setName("t3");

        t2.start();
        t3.start();
    }
}
