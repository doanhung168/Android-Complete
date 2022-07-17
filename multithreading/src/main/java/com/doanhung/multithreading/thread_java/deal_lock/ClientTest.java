package com.doanhung.multithreading.thread_java.deal_lock;

public class ClientTest {

    public static void main(String[] args) {
        User user = new User();
        BankAccount bankAccount = new BankAccount();

        user.setBankAccount(bankAccount);
        bankAccount.setUser(user);


        Thread t1 = new Thread(user);
        t1.setName("T1");

        Thread t2 = new Thread(bankAccount);
        t2.setName("T2");

        t1.start();
        t2.start();
    }
}
