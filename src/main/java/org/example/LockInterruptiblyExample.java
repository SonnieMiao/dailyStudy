package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptiblyExample {
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            try {
                lock.lockInterruptibly(); // 可中断方式获取锁
                System.out.println("Thread 1 acquired the lock.");
                Thread.sleep(5000); // 模拟线程执行任务，持有锁5秒
            } catch (InterruptedException e) {
                System.out.println("Thread 1 was interrupted.");
            } finally {
                lock.unlock();
                System.out.println("Thread 1 released the lock.");
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                lock.lockInterruptibly(); // 可中断方式获取锁
                System.out.println("Thread 2 acquired the lock.");
            } catch (InterruptedException e) {
                System.out.println("Thread 2 was interrupted.");
            } finally {
                lock.unlock();
                System.out.println("Thread 2 released the lock.");
            }
        });

        thread1.start();
        thread2.start();

        // 让主线程休眠一段时间后中断线程1
        try {
            Thread.sleep(1000);
            thread1.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}