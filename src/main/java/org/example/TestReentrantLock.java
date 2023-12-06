package org.example;

import org.apache.logging.log4j.LogManager;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * 项目名称：study
 * 类名称：
 * 类描述：
 * 创建人：王淞仪
 * 创建时间：2023/12/05 22:45
 * 修改人：王淞仪
 * 修改时间：2023/12/05 22:45
 * 修改备注：
 */
public class TestReentrantLock {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                if (!lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                    System.out.println("unable to get lock");
                    return;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("t1 acquired lock");
            lock.unlock();
        }, "t1");
        lock.lock();
        System.out.println("main locked");
        t1.start();
        Thread.sleep(1000);
        System.out.println("release lock");
        lock.unlock();
    }
}
