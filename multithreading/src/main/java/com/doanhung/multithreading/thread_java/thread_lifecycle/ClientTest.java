package com.doanhung.multithreading.thread_java.thread_lifecycle;

public class ClientTest {

    public static void main(String[] args) {
        MyThread t1 = new MyThread("t1");
        MyThread t2 = new MyThread("t2");

        System.out.println("T1 state: " + t1.getState());


        t1.start();
        t2.start();

        System.out.println("T1 state: " + t1.getState());



    }

}
