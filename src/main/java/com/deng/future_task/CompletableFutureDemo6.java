package com.deng.future_task;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * whenComplete 方法
 * 此方法表示，当某个任务执行完成后，执行的回调方法，无返回值，并且whenComplete放回的CompletableFuture的result是上个任务的结果
 */
public class CompletableFutureDemo6 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程名称：" + Thread.currentThread().getName());
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "第一个方法的返回值";
        }, executorService);
        /**
         * 调用whenComplete方法执行第二个任务时，则第二个任务和第一个任务是共用同一个线程池。
         * 调用whenCompleteAsync执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是ForkJoin线程池
         */
        CompletableFuture<String> task2 = task1.whenCompleteAsync((a, throwable) -> {
            System.out.println("当前线程名称：" + Thread.currentThread().getName());
            System.out.println("上个任务的返回值是：" + a);
        });
        //可以看出第二个任务输出的内容是第一个任务的返回值
        System.out.println(task2.join());
        executorService.shutdown();
    }
}
