package com.deng.cas;


import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {

    //JAVA无法操作内存
    //JAVA可以调用C++  native本地方法
    //通过C++操作内存

    //valueOffset 内存地址偏移值

    //CAS CompareAndSet  比较并交换
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(2020);

        //期望、更新
        //public final boolean compareAndSet(int expect, int update)
        //如果我期望的值达到了，那么就更新，否则 就不更新 CAS是CPU的并发原理！
        atomicInteger.compareAndSet(2020, 2021);
        atomicInteger.compareAndSet(2021, 2020);


        atomicInteger.compareAndSet(2020, 6666);
        System.out.println(atomicInteger.get());
    }
}
