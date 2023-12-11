package org.example.sequencePrint;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 项目名称：dailyStudy
 * 类名称：
 * 类描述：
 * 创建人：王淞仪
 * 创建时间：2023/12/11 20:34
 * 修改人：王淞仪
 * 修改时间：2023/12/11 20:34
 * 修改备注：
 */
public class TestReentrantLock {

}
class Printer extends ReentrantLock {
    private Condition curr;
    private Condition next;
    private String print;

    public Printer(Condition curr, Condition next, String print) {
        this.curr = curr;
        this.next = next;
        this.print = print;
    }
    public void doPrint() {
        this.lock();
        try {

        }finally {
            this.unlock();
        }
    }
}