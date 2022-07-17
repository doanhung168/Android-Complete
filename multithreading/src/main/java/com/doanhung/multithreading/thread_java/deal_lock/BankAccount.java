package com.doanhung.multithreading.thread_java.deal_lock;

public class BankAccount implements Runnable {

    private User mUser;

    public void setUser(User user) {
        this.mUser = user;
    }

    @Override
    public void run() {
        deposit();
    }

    public synchronized void deposit() {

        System.out.println(Thread.currentThread().getName() + ": BankAccount");
        System.out.println(Thread.currentThread().getName() + " Waiting for user ...");

        mUser.sendNotify();
    }
}
