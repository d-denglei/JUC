package com.deng.demo01;


//基本的卖票的例子


/**
 * 真正的多线程开发，公司中的开发,降低耦合性
 * 线程就是一个单独的资源类，没有任何附属操作！
 * 1、 属性、方法
 */
public class SaleTicketDemo01 {
    public static void main(String[] args) {
        //并发:多个线程操作同一个资源， 把资源类丢入线程
        Ticket ticket = new Ticket();

        //@FunctionalInterface 匿名内部类 jdk1.8 lambda表达式 (参数)->{具体代码}
        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                ticket.sale();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                ticket.sale();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                ticket.sale();
            }
        }, "C").start();
    }
}

//资源类
class Ticket {
    //票数
    private int number = 30;

    //卖票的方式
    public synchronized void sale() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName()
                    + "卖出了" + (number--) + "票:剩余：" + number);
        }
    }
}