package com.deng.thread_pool;


import java.util.concurrent.*;

/**
 * Executors 工具类 三大方法
 * new ThreadPoolExecutor.AbortPolicy()  //默认的拒绝策略  抛出异常
 * new ThreadPoolExecutor.CallerRunsPolicy()   拿来的去哪里
 * new ThreadPoolExecutor.DiscardPolicy() 队列满了就会丢掉任务 不会抛出异常
 * new ThreadPoolExecutor.DiscardOldestPolicy() 队列满了，尝试竞争最早的线程  没抢到也不会抛出异常
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {

        /**
         * 三大方法
         */
        //单例模式  单线程
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();
//        固定的线程池的大小
//        ExecutorService threadPool = Executors.newFixedThreadPool(5);
//        //可扩建的线程池
//        ExecutorService threadPool = Executors.newCachedThreadPool();

        /**
         * TODO 最大线程该如何去定义？
         * 1、CPU 密集型  读取几核CPU 就是几 可以保证CPU效率最高
         * 2、IO  密集型  判断你程序中十分耗IO的线程的个数(一般设置大于两倍)
         *       例如-程序中有 15个任务正在进行 io十分占用资源
         */

        //获取CPU核数
        System.out.println(Runtime.getRuntime().availableProcessors());


        /**
         * 七大参数  四种拒绝策略
         */
        // 工作中建议用这个方法去创建 用Executors 创建不太安全
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,      //核心线程数
                Runtime.getRuntime().availableProcessors(),  //最大线程数
                3,      //超时释放时间
                TimeUnit.SECONDS,   //秒
                new LinkedBlockingQueue<>(3),   //阻塞队列大小
                Executors.defaultThreadFactory(),       //默认的线程工厂
                new ThreadPoolExecutor.AbortPolicy()    //默认的拒绝策略  抛出异常
        );
        try {
            //最大线程数： maxSize + Deque
            for (int i = 0; i < 6; i++) {
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
