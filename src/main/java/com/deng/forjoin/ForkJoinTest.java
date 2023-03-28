package com.deng.forjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * @author denglei
 * @date 2023/3/28 22:36
 */
public class ForkJoinTest {
    public static void main(String[] args) throws Exception {
//        test1();
        test2();
        test3();
    }

    //普通计算
    public static void test1() {
        Long sum = 0L;
        long start = System.currentTimeMillis();
        for (Long i = 1L; i <= 10_0000_0000; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("sum =" + sum + " 时间" + (end - start));
    }

    //通过 ForkJoIn
    public static void test2() throws Exception {

        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> forkJoinDemo = new ForkJoinDemo(0L, 10_0000_0000L);
        forkJoinPool.execute(forkJoinDemo);//提交任务
        long sum = forkJoinDemo.get();


        long end = System.currentTimeMillis();
        System.out.println("sum =" + sum + " 时间" + (end - start));
    }

    //通过Stream的并行流  可以看到用并行流处理速度最快
    public static void test3() {
        long start = System.currentTimeMillis();
        long sum = LongStream.rangeClosed(0L, 10_0000_0000L).parallel().reduce(0, Long::sum);
        long end = System.currentTimeMillis();
        System.out.println("sum =" + sum + " 时间" + (end - start));
    }
}
