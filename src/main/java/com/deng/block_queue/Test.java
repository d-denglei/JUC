package com.deng.block_queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author dengl
 */
public class Test {
    public static void main(String[] args) {
        test1();
    }

    /**
     * 抛出异常
     */
    public static void test1() {
        //队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("b"));
    }
}
