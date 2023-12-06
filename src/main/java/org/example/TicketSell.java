package org.example;

import java.util.*;

/**
 * 项目名称：study
 * 类名称：
 * 类描述：
 * 创建人：王淞仪
 * 创建时间：2023/11/26 16:07
 * 修改人：王淞仪
 * 修改时间：2023/11/26 16:07
 * 修改备注：
 */
public class TicketSell {
    private static final Random RANDOM = new Random();
    private static int getRandom() {
        return RANDOM.nextInt(5) + 1;
    }

    public static void main(String[] args) {
        Counter counter = new Counter(2000000);
        List<Thread> consumers = new ArrayList<>();
        List<Integer> amountList = new Vector<>();
        for (int i = 0; i < 1000000; i++) {
            Thread thread = new Thread(()->{
                int a = counter.sell(getRandom());
                amountList.add(a);
            });
            consumers.add(thread);
            thread.start();
        }
        consumers.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(counter.getCount());
        System.out.println(amountList.stream().mapToInt(i->i).sum());
    }
}

class Counter {
    private int count;

    public Counter(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int sell(int amount) {
        if (this.count >= amount) {
            count = count - amount;
            return amount;
        } else {
            return 0;
        }
    }
}

