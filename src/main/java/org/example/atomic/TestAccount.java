package org.example.atomic;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 项目名称：dailyStudy
 * 类名称：尝试使用无锁（AtomicInteger）的CAS操作实现锁
 * 类描述：
 * 创建人：王淞仪
 * 创建时间：2023/12/19 20:23
 * 修改人：王淞仪
 * 修改时间：2023/12/19 20:23
 * 修改备注：
 */
public class TestAccount {
    public static void main(String[] args) {
        Account account1 = new NormalAccount(100000);
        Account account2 = new SynchonizeAccount(100000);
        Account account3 = new LockAccount(100000);
        Account account4 = new AtomicAccount(100000);

        Account.demo(account1);
        Account.demo(account2);
        Account.demo(account3);
        Account.demo(account4);
    }
}

class AtomicAccount implements Account {
    private AtomicInteger balance;

    public AtomicAccount(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public int getBalance() {
        return this.balance.get();
    }

    @Override
    public void withdraw(int amount) {
        while (true) {
            int pre = this.balance.get();
            int aft = pre - amount;
            if (this.balance.compareAndSet(pre, aft)) {
                break;
            }
        }
    }
}

class LockAccount implements Account {
    private int balance;
    private final ReentrantLock lock = new ReentrantLock();

    public LockAccount(int balance) {
        this.balance = balance;
    }

    @Override
    public int getBalance() {
        lock.lock();
        try {
            return this.balance;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void withdraw(int amount) {
        lock.lock();
        try {
            this.balance -= amount;
        }finally {
            lock.unlock();
        }
    }
}
class SynchonizeAccount implements Account {
    private int balance;

    public SynchonizeAccount(int balance) {
        this.balance = balance;
    }

    @Override
    public synchronized int getBalance() {
        return this.balance;
    }

    @Override
    public synchronized void withdraw(int amount) {
        this.balance -= amount;
    }
}

class NormalAccount implements Account {
    private int balance;

    public NormalAccount(int balance) {
        this.balance = balance;
    }

    @Override
    public int getBalance() {
        return this.balance;
    }

    @Override
    public void withdraw(int amount) {
        this.balance -= amount;
    }
}

interface Account {
    int getBalance();

    void withdraw(int amount);

    static void demo(Account account) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            threads.add(new Thread(() -> {
                account.withdraw(10);
            }));
        }
        long start = System.nanoTime();
        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance() + "\t" + (end - start) / 1000_000 + "ms");
    }
}
