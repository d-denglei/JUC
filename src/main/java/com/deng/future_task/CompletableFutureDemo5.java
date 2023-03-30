package com.deng.future_task;

import java.util.concurrent.CompletableFuture;

/**
 * exceptionally 用法
 * 某个任务执行异常时，执行的回调方法;并且有抛出异常作为参数，传递到回调方法
 */
public class CompletableFutureDemo5 {
    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程名称：" + Thread.currentThread().getName());
            throw new RuntimeException();
//            return 1;
        });
        //如果没抛出异常那么不会走下面
        CompletableFuture<Integer> exceptionally = completableFuture.exceptionally(e -> {
            e.printStackTrace();
            return 1024;
        });
        //当前
        System.out.println(exceptionally.join());
    }
}
