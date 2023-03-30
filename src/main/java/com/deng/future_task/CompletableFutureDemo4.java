package com.deng.future_task;

import java.util.concurrent.CompletableFuture;

/**
 * thenApply/thenApplyAsync ：
 * 第一个任务执行完成后，执行第二个回调方法任务，并将改任务的执行结果传入回调方法中，回调方法有返回值
 */
public class CompletableFutureDemo4 {
    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("先执行第一个任务");
            return 1024;
        });
        /**
         * 调用thenApply方法执行第二个任务时，则第二个任务和第一个任务是共用同一个线程池。
         * 调用thenApplyAsync执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是ForkJoin线程池
         */
        CompletableFuture<Integer> completableFuture1 = completableFuture.thenApplyAsync(a -> {
            System.out.println("第一个方法执行的内容是 " + a);
            return a + 1;
        });
        //第二个回调方法返回值
        Integer join = completableFuture1.join();
        System.out.println(join);
    }
}
