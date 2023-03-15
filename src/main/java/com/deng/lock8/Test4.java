package com.deng.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 八锁问题
 * 7、1个静态同步方法 1个普通同步方法，先打印发短信还是打电话？  1打电话 2发短信
 * 8、两个对象分别调用 一个static 修饰的同步方法  一个普通同步方法，先打印哪个?  1打电话 2发短信
 */
public class Test4 {
    public static void main(String[] args) {
        //两个对象的Class类模板只有一个 锁定的是同一个class
        Phone4 phone1 = new Phone4();
        Phone4 phone2 = new Phone4();

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

class Phone4 {

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

    //锁的调用者
    public synchronized void call() {
        System.out.println("打电话");
    }
}