package com.doanhung.multithreading.thread_java.synchronized_method;

public class ClientTest {
    public static void main(String[] args) {
        SharedData sharedData = new SharedData();
        CustomThread customThread1 = new CustomThread(sharedData);
        CustomThread customThread2 = new CustomThread(sharedData);
        CustomThread customThread3 = new CustomThread(sharedData);
        CustomThread customThread4 = new CustomThread(sharedData);
        customThread1.start();
        customThread2.start();
        customThread3.start();
        customThread4.start();
    }
}
