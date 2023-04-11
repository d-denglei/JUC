package com.deng.volatile_test;

import java.util.concurrent.TimeUnit;

/**
 * @author DengLei
 * @date 2023/04/11 16:40
 */

public class JMMDemo {
    //没加 volatile 发现main方法并未停止会进入死循环
    
    private volatile static int num = 0;

    public static void main(String[] args) {

        new Thread(() -> { //线程1
            while (num == 0) {

            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        num = 1;
        System.out.println(Thread.currentThread().getName() + "" + num);
    }
}
