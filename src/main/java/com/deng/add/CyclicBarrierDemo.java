package com.deng.add;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author DengLei
 * @date 2023/03/21 15:48
 */

//计数器 +1
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        /**
         * 集齐七颗龙珠召唤神龙 线程
         */
        //召唤龙珠线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(6, () -> {
            System.out.println("召唤神龙成功！");
        });


        for (int i = 1; i <= 6; i++) {

            final int temp = i;
            //lambda 无法操作匿名内部类外面的值
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "收集了" + temp + "龙珠");
                try {
                    cyclicBarrier.await();//等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
