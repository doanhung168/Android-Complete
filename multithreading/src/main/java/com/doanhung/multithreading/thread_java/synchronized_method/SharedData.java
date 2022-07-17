package com.doanhung.multithreading.thread_java.synchronized_method;

public class SharedData {
    private int x = 0;

    public synchronized void add(int plus) {
        x += plus;
        System.out.println("\n umber sequence of x: " + x);
        for (int i = 0; i < x; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
    }
}
