package com.deng.future_task;

import java.util.concurrent.CompletableFuture;

/**
 * AnyOf 任意一个任务执行完 就返回 如果执行任务出现异常 那么就会get方法需要抛出
 */
public class CompletableFutureDemo11 {
    public static void main(String[] args) {

        CompletableFuture<Void> a = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我执行完了");
        });
        CompletableFuture<Void> b = CompletableFuture.runAsync(() -> {
            System.out.println("我也执行完了");
        });
        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(a, b).whenComplete((m, k) -> {
            System.out.println("finish");
//            return "捡田螺的小男孩";
        });
        anyOfFuture.join();


    }
}
