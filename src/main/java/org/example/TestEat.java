package org.example;


import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 项目名称：study
 * 类名称：
 * 类描述：
 * 创建人：王淞仪
 * 创建时间：2023/12/05 23:12
 * 修改人：王淞仪
 * 修改时间：2023/12/05 23:12
 * 修改备注：
 */
public class TestEat {
    public static void main(String[] args) {
        Chop c1 = new Chop("c1");
        Chop c2 = new Chop("c2");
        Chop c3 = new Chop("c3");
        Chop c4 = new Chop("c4");
        Chop c5 = new Chop("c5");
        Ph ph1 = new Ph("ph1", c1, c2);
        Ph ph2 = new Ph("ph2", c2, c3);
        Ph ph3 = new Ph("ph3", c3, c4);
        Ph ph4 = new Ph("ph4", c4, c5);
        Ph ph5 = new Ph("ph5", c5, c1);
        ph1.start();
        ph2.start();
        ph3.start();
        ph4.start();
        ph5.start();
    }
}

class Chop extends ReentrantLock {
    String name;

    public Chop() {

    }

    public Chop(String name) {
        this.name = name;
    }
}

class Ph extends Thread {
    private Chop left;
    private Chop right;

    private static final Logger logger = LogManager.getLogger(Ph.class);

    public Ph(String name, Chop left, Chop right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        logger.debug("Thread {} started.", super.getName());

        if (left.tryLock()) {
            try {
                logger.debug("Thread {} acquired left lock.", super.getName());
                if (right.tryLock()) {
                    try {
                        logger.debug("Thread {} acquired right lock.", super.getName());
                        eat();
                    } finally {
                        right.unlock();
                        logger.debug("Thread {} released right lock.", super.getName());
                    }
                } else {
                    logger.debug("Thread {} failed to acquire right lock.", super.getName());
                }
            } finally {
                left.unlock();
                logger.debug("Thread {} released left lock.", super.getName());
            }
        } else {
            logger.debug("Thread {} failed to acquire left lock.", super.getName());
        }
    }


    private void eat() {
        while (true) {
            logger.info(super.getName() + " eating...");
            try {
                Thread.sleep(new Random().nextInt(5) * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}