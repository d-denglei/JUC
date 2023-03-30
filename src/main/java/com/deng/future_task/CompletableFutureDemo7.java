package com.deng.future_task;

import java.util.concurrent.CompletableFuture;

/**
 * handle方法
 * 某个任务执行完成后，执行回调方法，并且是有返回值的;并且handle方法返回的CompletableFuture的result是回调方法执行的结果
 */
public class CompletableFutureDemo7 {
    public static void main(String[] args) {
        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("当前线程名称：" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "第一个方法的返回值";
                }
        );

        CompletableFuture<String> rstFuture = orgFuture.handle((a, throwable) -> {

            System.out.println("上个任务执行完啦，还把" + a + "传过来");

            return "第二个方法的返回值";
        });
        //返回的是第二个方法的返回值
        System.out.println(rstFuture.join());

    }
}
