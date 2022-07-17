package com.doanhung.multithreading.thread_java.synchronized_method;

import java.util.Random;

public class CustomThread extends Thread{
    private SharedData sharedData;

    public CustomThread(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            int randomNumber = random.nextInt(5) + 1;
            sharedData.add(randomNumber);
        }
    }
}
