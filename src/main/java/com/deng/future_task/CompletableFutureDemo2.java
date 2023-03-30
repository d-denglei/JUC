package com.deng.future_task;

import java.util.concurrent.CompletableFuture;

/**
 * thenRun/thenRunAsync 方法:通俗点讲就是做完第一个任务然后去执行第二个任务，可以用
 * 做某个方法执行完以后 的回调方法。前后两个任务没参数传递 第二个任务也没返回值
 */
public class CompletableFutureDemo2 {
    public static void main(String[] args) {
        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("先执行第一个CompletableFuture方法任务");
                    return "捡田螺的小男孩";
                }
        );
        /**
         * 调用thenRun方法执行第二个任务时，则第二个任务和第一个任务是共用同一个线程池。
         * 调用thenRunAsync执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是ForkJoin线程池
         */
        CompletableFuture<Void> thenRunFuture = orgFuture.thenRunAsync(() -> {
            System.out.println("接着执行第二个任务");
        });

        System.out.println(thenRunFuture.join());
    }
}
