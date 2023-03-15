package com.deng.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 八锁问题
 * 5、增加两个静态的同步方法，只有一个对象 先打印发短信还是打电话？ 1发短信2打电话
 * 6、两个对象，两个静态方法 会先发短信还是打电话？  1发短信 2打电话
 */
public class Test3 {
    public static void main(String[] args) {
        //两个对象的Class类模板只有一个 锁定的是同一个class
        Phone3 phone1 = new Phone3();
        Phone3 phone2 = new Phone3();

        new Thread(() -> {
            phone1.sendSms();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone2.call();
        }, "B").start();

    }
}

class Phone3 {

    //synchronized 锁的是对象是方法的调用者
    //static 修饰以后 静态方法
    //类初始化加载就存在了！锁的是类class对象
    public static synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public static synchronized void call() {
        System.out.println("打电话");
    }
}