package com.doanhung.multithreading.thread_java.deadlock2;

public class ClientTest {
    public static void main(String[] args) {
        BankAccount husbandAccount = new BankAccount("Husband's Account");
        BankAccount wifeAccount = new BankAccount("Wife's Account");

        Thread husbandThread = new Thread(new Runnable() {
            @Override
            public void run() {
                husbandAccount.transferTo(wifeAccount, 3000000);
            }
        });

        Thread wifeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                wifeAccount.transferTo(husbandAccount, 2000000);
            }
        });

        husbandThread.start();
        wifeThread.start();
    }
}
