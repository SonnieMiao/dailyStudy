package org.example.balking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 项目名称：dailyStudy
 * 类名称：犹豫模式（balking）
 * 类描述：
 * 创建人：王淞仪
 * 创建时间：2023/12/12 20:16
 * 修改人：王淞仪
 * 修改时间：2023/12/12 20:16
 * 修改备注：
 */
public class test {
    public static void main(String[] args) {
        Monitor.getInstance().startMonitor();
        Monitor.getInstance().startMonitor();
        Monitor.getInstance().startMonitor();
        try {
            Thread.sleep(2200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Monitor.getInstance().stop();
    }
}


class Monitor {
    private static Monitor instance = null;
    private final Logger logger = LogManager.getLogger(Monitor.class);
    private static boolean started = false;
    private Thread monitorThread;
    private static volatile boolean end = false;

    private Monitor() {

    }

    //单例模式
    public static Monitor getInstance() {
        if (instance == null) {
            synchronized (Monitor.class) {
                if (instance == null) {
                    instance = new Monitor();
                }
            }
        }
        return instance;
    }

    void startMonitor() {
       monitorThread= new Thread(() -> {
            //保证读写的原子性
            synchronized (Monitor.class) {
                if (started) {
                    return;
                }
                started = true;
            }
            while (!end) {
                logger.info("monitoring");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignore) {
                }
            }
            logger.debug("monitor exited");
        },"ano");
        monitorThread.start();
    }

    void stop() {
        end = true;
        monitorThread.interrupt();
        logger.debug("interrupt ano");
    }
}