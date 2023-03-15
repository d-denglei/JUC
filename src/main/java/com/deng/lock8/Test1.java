package com.deng.lock8;


import java.util.concurrent.TimeUnit;

/**
 * 八锁问题
 * 1、标准情况下，两个线程先打印 发短信还是打电话？ 1发短信 2打电话
 * 2、sendSms延迟4秒，两个线程先打印 发短信还是打电话？ 1发短信 2打电话
 */
public class Test1 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendSms();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone.call();
        }, "B").start();

    }
}

class Phone {

    //synchronized 锁的是对象是方法的调用者
    //两个方法用的是同一个锁，谁先拿到谁就执行！
    public synchronized void sendSms() {

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public synchronized void call() {
        System.out.println("打电话");
    }
}
