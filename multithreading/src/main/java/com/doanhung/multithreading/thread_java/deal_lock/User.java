package com.doanhung.multithreading.thread_java.deal_lock;

public class User implements Runnable {

    private BankAccount mBankAccount;


    public void setBankAccount(BankAccount bankAccount) {
        this.mBankAccount = bankAccount;
    }

    @Override
    public void run() {
        sendNotify();
    }

    public synchronized void sendNotify() {
        System.out.println(Thread.currentThread().getName() + " User");

        System.out.println(Thread.currentThread().getName() + " Waiting for bankAccount ... ");

        mBankAccount.deposit();
    }

}
