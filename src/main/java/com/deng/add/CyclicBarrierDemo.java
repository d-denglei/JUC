package com.deng.add;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 计数器 +1
 *
 * @author DengLei
 * @date 2023/03/21 15:48
 */

public class CyclicBarrierDemo {
    //lol游戏开始 模拟
//    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);
//    private final static CyclicBarrier BARRIER = new CyclicBarrier(10, () -> {
//        System.out.println("游戏开始");
//    });
//
//    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            final String name = "玩家" + i;
//            EXECUTOR_SERVICE.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(2000);
//                        System.out.println(name + "已准备,等待其他玩家准备...");
//                        BARRIER.await();
//                        Thread.sleep(1000);
//                        System.out.println(name + "已加入游戏");
//                    } catch (InterruptedException e) {
//                        System.out.println(name + "离开游戏");
//                    } catch (BrokenBarrierException e) {
//                        System.out.println(name + "离开游戏");
//                    }
//                }
//            });
//        }
//        EXECUTOR_SERVICE.shutdown();
//    }


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
