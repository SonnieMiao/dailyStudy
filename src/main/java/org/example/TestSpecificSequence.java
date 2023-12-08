package org.example;

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
public class TestSpecificSequence {
    private static String toRun = "a";
    private static int time = 0;
    private static final Logger logger = LogManager.getLogger(TestSpecificSequence.class);

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (logger) {
                while (toRun.equals("a") && time < 5) {
                    logger.debug("a");
                    toRun = "b";
                    time++;
                }
                try {
                    logger.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        new Thread(()->{
            logger.debug("b");
        }).start();
        new Thread(()->{
            logger.debug("c");
        }).start();
    }
}
