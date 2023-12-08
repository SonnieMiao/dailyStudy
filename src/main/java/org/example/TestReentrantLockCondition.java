package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 项目名称：dailyStudy
 * 类名称：
 * 类描述：
 * 创建人：王淞仪
 * 创建时间：2023/12/7 20:33
 * 修改人：王淞仪
 * 修改时间：2023/12/7 20:33
 * 修改备注：
 */

public class TestReentrantLockCondition {
    private static final Logger LOGGER = LogManager.getLogger(TestReentrantLockCondition.class);
    private static final Object room = new Object();
    private static boolean hasCigarette = false;
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (room) {
                while (!hasCigarette) {
                    LOGGER.info("no cigarette");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (hasCigarette) {
                    LOGGER.info("working");
                    hasCigarette = false;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    LOGGER.debug("nothing can do");
                }
            }
        }, "person").start();
        new Thread(() -> {
            synchronized (room) {
                while (!hasCigarette) {
                    LOGGER.info("set cigarette");
                    hasCigarette = true;
                }
                room.notify();
            }
        }, "provider").start();
    }
}
