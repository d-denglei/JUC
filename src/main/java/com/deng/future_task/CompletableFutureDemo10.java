package com.deng.future_task;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * AllOf
 * 所有任务都执行完成后，才执行 allOf返回的CompletableFuture。
 * 如果任意一个任务异常，allOf的CompletableFuture，执行get方法，会抛出异常
 */
public class CompletableFutureDemo10 {
    public static void main(String[] args) {
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务一执行完了");
        });
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务二执行完了");
        });

        try {
            CompletableFuture.allOf(task1, task2).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
