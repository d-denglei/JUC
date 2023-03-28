package com.deng.forjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 求和计算
 * 如何使用forkjoin？
 * 1、使用 forkjoinPool 通过这个执行
 * 2、计算任务 forkjoinPool.execute(ForkJoinTask task)
 */
public class ForkJoinDemo extends RecursiveTask<Long> {

    private Long start;

    private Long end;

    //临界值
    private Long temp = 10000L;

    ForkJoinDemo(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    //具体业务执行
    @Override
    protected Long compute() {
        if ((end - start) > temp) {
            //通过分支合并去计算
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            //中间值
            long middle = (start + end) / 2;

            ForkJoinDemo task1 = new ForkJoinDemo(start, middle);
            task1.fork();//把任务压入线程队列

            ForkJoinDemo task2 = new ForkJoinDemo(middle + 1, end);
            task2.fork();
            //join 阻塞当前线程获取返回结果
            long l = task1.join() + task2.join();
            return l;

        } else {
            long sum = 0L;
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
