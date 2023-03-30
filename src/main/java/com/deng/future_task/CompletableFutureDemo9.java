package com.deng.future_task;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * applyToEither / acceptEither / runAfterEither 都表示：
 * 将两个CompletableFuture组合起来，只要其中一个执行完了,就会执行某个任务。
 * 区别：
 * applyToEither：会将已经执行完成的任务，作为方法入参，传递到指定方法中，且有返回值
 * acceptEither: 会将已经执行完成的任务，作为方法入参，传递到指定方法中，且无返回值
 * runAfterEither： 不会把执行结果当做方法入参，且没有返回值。
 */
public class CompletableFutureDemo9 {
    public static void main(String[] args) {
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("执行完第一个任务");
            return "第一个任务";
        });
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行完第二个任务");
            return "第二个任务";
        });
        CompletableFuture<Void> completableFuture = task2.acceptEither(task1, System.out::println);

        System.out.println(completableFuture.join());
    }
}
