package org.example;

/**
 * 项目名称：study
 * 类名称：
 * 类描述：
 * 创建人：王淞仪
 * 创建时间：2023/11/25 22:51
 * 修改人：王淞仪
 * 修改时间：2023/11/25 22:51
 * 修改备注：
 */
public class Test01 {

    public static void main(String[] args) throws InterruptedException {
        T1 t11 = new T1();
        T2 t2 = new T2();
        Thread thread2 = new Thread(t2);
        Thread thread1 = new Thread(t11);
        long a = System.currentTimeMillis();
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        System.out.println(System.currentTimeMillis() - a);
    }
}

class T1 implements Runnable {

    @Override
    public void run() {
        System.out.println("t1 start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("T1 end");
    }
}
class T2 implements Runnable {

    @Override
    public void run() {
        System.out.println("t2 start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("T2 end");

    }
}