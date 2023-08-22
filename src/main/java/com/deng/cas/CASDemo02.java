package com.deng.cas;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 原子引用
 */
public class CASDemo02 {

    //正常业务这里泛型是一个业务对象
    //如果泛型是包装类 注意对象的引用问题, compareAndSet 都是用 == 去匹配
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1, 1);

    //CAS CompareAndSet  比较并交换
    public static void main(String[] args) {


        new Thread(() -> {
            //获取版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println("a1=>" + stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(1, 2, stamp, stamp + 1));
            stamp = atomicStampedReference.getStamp();
            System.out.println("a2=>" + stamp);

            System.out.println(atomicStampedReference.compareAndSet(2, 1, stamp, stamp + 1));
            stamp = atomicStampedReference.getStamp();
            System.out.println("a3=>" + stamp);
        }, "a").start();

        new Thread(() -> {
            //获取版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println("b1=>" + stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(1, 3, stamp, stamp + 1));
            stamp = atomicStampedReference.getStamp();
            System.out.println("b2=>" + stamp);
        }, "b").start();
    }
}
