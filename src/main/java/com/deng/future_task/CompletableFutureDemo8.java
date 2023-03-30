package com.deng.future_task;


import java.util.concurrent.CompletableFuture;

/**
 * thenCombine / thenAcceptBoth / runAfterBoth都表示：
 * 将两个CompletableFuture组合起来，只有这两个都正常执行完了，才会执行某个任务。
 * 区别：  thenCombine：会将两个任务的执行结果作为方法入参，传递到指定方法中，且有返回值
 * thenAcceptBoth: 会将两个任务的执行结果作为方法入参，传递到指定方法中，且无返回值
 * runAfterBoth 不会把执行结果当做方法入参，且没有返回值。
 */
public class CompletableFutureDemo8 {
    public static void main(String[] args) {
        //第一个任务
        CompletableFuture<Integer> task1 = CompletableFuture.supplyAsync(() -> 11);

        //第二个任务
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> 22);

        //这是第三个任务去执行
        CompletableFuture<Integer> sum = task2.thenCombineAsync(task1, (s, w) -> {
            System.out.println(s);
            System.out.println(w);
            return s + w;
        });
        //返回的是任务一跟任务二组合值
        System.out.println(sum.join());
    }
}
