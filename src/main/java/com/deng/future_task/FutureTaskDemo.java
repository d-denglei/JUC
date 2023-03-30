package com.deng.future_task;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 异步调用
 */
public class FutureTaskDemo {
    public static void main(String[] args) {
        //可以自定义线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        //runAsync的使用 无返回值
        CompletableFuture<Void> runFuture = CompletableFuture.runAsync(
                () -> System.out.println("runAsync 你好"), executor);

        //supplyAsync的使用 有返回值
        CompletableFuture<String> supplyFuture = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        TimeUnit.SECONDS.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("supplyAsync 你好");
                    return "捡田螺的小男孩";
                });
        supplyFuture.thenRunAsync(() -> {
            System.out.println("第二个任务");
        });

        //runAsync的future没有返回值，输出null
        System.out.println("runFuture 结果：" + runFuture.join());

        // supplyFuture.join()也是获取结果值 区别是一个get需要手动处理异常 join不需要 但是碰到RuntimeException也会抛出
        //supplyAsync的future，有返回值
        System.out.println("supplyFuture 结果：" + supplyFuture.join());

        executor.shutdown(); // 线程池需要关闭

    }
}
