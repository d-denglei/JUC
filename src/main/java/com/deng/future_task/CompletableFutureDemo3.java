package com.deng.future_task;

import java.util.concurrent.CompletableFuture;

/**
 * thenAccept 和 thenAcceptAsync有什么区别呢？
 * 第一个任务执行完以后，执行第二个回调方法任务，会把第一个任务的执行结果作为入参 传递到第二个任务方法中，
 * 但是回调方法无返回值 两者区别
 */
public class CompletableFutureDemo3 {
    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("先执行第一个任务");
            return 1024;
        });
        /**
         * 调用thenAccept方法执行第二个任务时，则第二个任务和第一个任务是共用同一个线程池。
         * 调用thenAcceptAsync执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是ForkJoin线程池
         */
        completableFuture.thenAcceptAsync(a -> {
            System.out.println("第一个方法执行的内容是 " + a);
        });
        //这里返回的值的内容是第一个方法的值 可见第二个回调方法是没有返回值的
        Integer join = completableFuture.join();
        System.out.println(join);
    }
}
