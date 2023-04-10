package com.deng.add;

import java.util.concurrent.CountDownLatch;

/**
 * 计数器 -1
 *
 * @author DengLei
 * @date 2023/03/21 15:25
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        //总数是6 必须要等6执行任务的时候再使用!
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 8; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "Go out");
                countDownLatch.countDown();//数量-1
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();//等待计数器归零，然后继续操作
        System.out.println("close Door");
    }
}
