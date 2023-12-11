package org.example.sequencePrint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 项目名称：dailyStudy
 * 类名称：
 * 类描述：
 * 创建人：王淞仪
 * 创建时间：2023/12/8 19:04
 * 修改人：王淞仪
 * 修改时间：2023/12/8 19:04
 * 修改备注：
 */
public class TestSynchronized {
    private static String toRun = "a";
    private static int time = 0;
    private static final Logger logger = LogManager.getLogger(TestSynchronized.class);

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (logger) {
                while (time < 5) {
                    while (!toRun.equals("a")) {
                        try {
                            logger.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    logger.debug("a");
                    toRun = "b";
                    time++;
                    logger.notifyAll();
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (logger) {
                while (time < 5) {
                    while (!toRun.equals("b")) {
                        try {
                            logger.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    logger.debug("b");
                    toRun = "c";
                    logger.notifyAll();
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (logger) {
                while (time < 5) {
                    while (!toRun.equals("c")) {
                        try {
                            logger.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    logger.debug("c");
                    toRun = "a";
                    logger.notifyAll();
                }
            }
        }).start();
    }
}
class waitNotify{

}