package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：study
 * 类名称：
 * 类描述：
 * 创建人：王淞仪
 * 创建时间：2023/11/26 15:23
 * 修改人：王淞仪
 * 修改时间：2023/11/26 15:23
 * 修改备注：
 */
public class Test02 {

    public void testThread() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            add(list);
            minus(list);
        }
    }

    public void add(List<String> list) {
        list.add("1");
    }

    public void minus(List<String> list) {
        list.remove(0);
    }
}

class Test02Sub extends Test02 {
    @Override
    public void minus(List<String> list) {
        new Thread(() -> {
            list.remove(0);
        }).start();
    }
}

class Test022 {
    public static void main(String[] args) {
        Test02Sub t = new Test02Sub();
        for (int i = 0; i < 2; i++) {
            t.testThread();
        }
    }
}