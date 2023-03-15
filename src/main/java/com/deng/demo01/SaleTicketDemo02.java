package com.deng.demo01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock写法
 */
public class SaleTicketDemo02 {
    public static void main(String[] args) {
        //并发:多个线程操作同一个资源， 把资源类丢入线程
        Ticket2 ticket2 = new Ticket2();

        //@FunctionalInterface 匿名内部类 jdk1.8 lambda表达式 (参数)->{具体代码}
        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                ticket2.sale();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                ticket2.sale();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                ticket2.sale();
            }
        }, "C").start();
    }
}

//lock
//资源类
class Ticket2 {

    //票数
    private int number = 30;

    //官方文档 使用常用语法
    // Lock l = ...;
    // l.lock();
    // try { // access the resource protected by this lock }
    // finally { l.unlock(); }

    Lock lock = new ReentrantLock();

    //卖票的方式
    public void sale() {
        lock.lock();
//        lock.tryLock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName()
                        + "卖出了" + (number--) + "票:剩余：" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//解锁
        }

    }
}