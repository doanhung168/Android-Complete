package com.doanhung.multithreading.thread_java.initialization;

public class ClientTest {
    public static void main(String[] args) {

        MyThread1 myThread1 = new MyThread1();
        Thread t1 = new Thread(myThread1);
        t1.setName("MyThread 1");

        MyThread2 t2 = new MyThread2();
        t2.setName("MyThread 2");

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        });
        t3.setName("MyThread 3");

        t1.start();
        t2.start();
        t3.start();

    }
}
