package org.example;

import java.util.Random;

/**
 * 项目名称：study
 * 类名称：
 * 类描述：
 * 创建人：王淞仪
 * 创建时间：2023/11/26 17:15
 * 修改人：王淞仪
 * 修改时间：2023/11/26 17:15
 * 修改备注：
 */
public class Transfer {
    private static final Random RANDOM = new Random();

    private static int getRandom() {
        return RANDOM.nextInt(100) + 1;
    }

    public static void main(String[] args) {
        Account account1 = new Account(1000);
        Account account2 = new Account(1000);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                account1.transfer(account2, getRandom());
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                account2.transfer(account1, getRandom());
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(account1.getBalance() + account2.getBalance());
    }
}

class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public  void transfer(Account account, int amount) {
        synchronized (Transfer.class) {
            if (this.balance >= amount) {
                this.balance -= amount;
                account.setBalance(account.getBalance() + amount);
            }
        }

    }
}