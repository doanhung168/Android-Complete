package com.doanhung.multithreading.thread_java.deadlock2;

import androidx.annotation.NonNull;

public class BankAccount{

    private final String mAccountName;
    private int mAmount = 5000000;

    public BankAccount(String accountName) {
        this.mAccountName = accountName;
    }

    public synchronized void withdraw(long withdrawAmount) {
        System.out.println(mAccountName + " withdrawing...");

        mAmount -= withdrawAmount;
    }

    public synchronized void deposit(long depositAmount) {
        System.out.println(mAccountName + " depositing...");
        mAmount += depositAmount;
    }

    public synchronized void transferTo(@NonNull BankAccount toAccount, long transferAmount) {
        this.withdraw(transferAmount);

        toAccount.deposit(transferAmount);

        System.out.println("The amount of " + mAccountName + " is: " + mAmount);
    }


}
