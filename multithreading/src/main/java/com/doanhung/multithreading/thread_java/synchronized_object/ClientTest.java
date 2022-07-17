package com.doanhung.multithreading.thread_java.synchronized_object;

public class ClientTest {
    public static void main(String[] args) {

        SharedData sharedData = new SharedData();

        Thread1 thread1 = new Thread1(sharedData);
        Thread2 thread2 = new Thread2(sharedData);

        thread1.start();
        thread2.start();

    }
}
