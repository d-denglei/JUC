package com.deng.future_task;

import java.util.concurrent.CompletableFuture;

/**
 * 异步调用
 */
public class FutureTaskDemo {
    public static void main(String[] args) throws Exception {
        //没有返回值的 runAsync 异步回调
//        //发起一个请求
//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName() + "runAsync=>Void");
//        });
//        System.out.println("1111");
//        completableFuture.get();// 获取阻塞执行结果

        //有返回值的异步回调
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "supplyAsync=>Integer");
            return 1024;
        });
        completableFuture.whenComplete((t, u) -> {
            System.out.println("t=" + t);
            System.out.println("u=" + u);
        }).exceptionally(e -> {
            e.printStackTrace();
            return 111;
        });
        Integer integer = completableFuture.get();
        System.out.println(integer);
    }
}
