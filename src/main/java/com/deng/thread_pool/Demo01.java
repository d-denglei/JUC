package com.deng.thread_pool;


import java.util.concurrent.*;

/**
 * Executors 工具类 三大方法
 */
public class Demo01 {
    String a;
    String b;

    public Demo01() {
        this("A", "b");
    }

    public Demo01(String a, String b) {
        this.a = a;
        this.b = b;
    }

    public static void main(String[] args) {


        //单例模式  单线程
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();
//        固定的线程池的大小
//        ExecutorService threadPool = Executors.newFixedThreadPool(5);
//        //可扩建的线程池
//        ExecutorService threadPool = Executors.newCachedThreadPool();

        //工作中建议用这个方法去创建 用Executors 创建不太安全
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,      //核心线程数
                5,  //最大线程数
                3,      //超时释放时间
                TimeUnit.SECONDS,   //秒
                new LinkedBlockingQueue<>(3),   //阻塞队列大小
                Executors.defaultThreadFactory(),       //默认的线程工厂
                new ThreadPoolExecutor.AbortPolicy()  //默认的拒绝策略
        );
        try {
            for (int i = 0; i < 9; i++) {
                //通过线程池来创建线程
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " OK");
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //线程池用完接收
            threadPool.shutdown();
        }

    }
}
