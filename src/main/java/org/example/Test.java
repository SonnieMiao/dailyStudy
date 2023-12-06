package org.example;

/**
 * 项目名称：Default (Template) Project
 * 类名称：
 * 类描述：
 * 创建人：王淞仪
 * 创建时间：2023/11/22 21:16
 * 修改人：王淞仪
 * 修改时间：2023/11/22 21:16
 * 修改备注：
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Basket basket = new Basket();
        Producer p1 = new Producer(basket);
        Consumer c1 = new Consumer(basket);
        Consumer c2 = new Consumer(basket);
        Consumer c3 = new Consumer(basket);
        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(c1);
        Thread t3 = new Thread(c2);
        Thread t4 = new Thread(c3);
        t1.setName("p1");
        t2.setName("c1");
        t3.setName("c2");
        t4.setName("c3");
        t1.start();
        Thread.sleep(3000);
        t2.start();
        t3.start();
        t4.start();
    }




}
class Basket {
    int index = 0;
    Mantou[] arr = new Mantou[9];

    public synchronized void put(Mantou mantou) {
        while (index == arr.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        arr[index] = mantou;
        index++;
        System.out.println(Thread.currentThread().getName() + "生产了" + mantou + "剩余：" + index);
        this.notify();
    }

    public synchronized Mantou get() {
        while (index == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        index--;
        System.out.println(Thread.currentThread().getName() + "消费了" + arr[index]+ "剩余：" + index);
        this.notify();
        return arr[index];
    }
}

class Producer implements Runnable {
    Basket basket;

    public Producer(Basket basket) {
        this.basket = basket;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            Mantou mantou = new Mantou(i);
            basket.put(mantou);
            try {
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Consumer implements Runnable {
    Basket basket;

    public Consumer(Basket basket) {
        this.basket = basket;
    }

    @Override
    public void run() {
        for (int i = 0; i < 60; i++) {
            Mantou mantou = basket.get();
            try {
                Thread.sleep((long) (Math.random() * 300));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

 class Mantou {
    int id;

    public Mantou(int id) {
        this.id = id;
    }

    public String toString() {
        return "馒头：" + id;
    }
}