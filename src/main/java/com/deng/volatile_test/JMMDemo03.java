package com.deng.volatile_test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author DengLei
 * @date 2023/04/11 17:22
 */

//不保证原子性
public class JMMDemo03 {

    private volatile static AtomicInteger num = new AtomicInteger();

    public static void add() {
        num.getAndIncrement(); //+1 原子类的+1 CAS自旋
    }

    public static void main(String[] args) {
        //理论上num结果是2万
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount() > 2) { //main  gc
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + " " + num);
    }
}
