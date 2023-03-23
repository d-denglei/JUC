package com.deng.block_queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 *
 * @author dengl
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws Exception {
//        test1();
//        test2();
//        test3();
        test4();
    }

    /**
     * 抛出异常
     */
    public static void test1() {
        //队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        //进队 add
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));

        //java.util.IllegalStateException: Queue full 队列已满异常
//        System.out.println(blockingQueue.add("e"));

        //出队 remove
        System.out.println(blockingQueue.remove());
        //查看对首元素是谁
        System.out.println(blockingQueue.element());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        //java.util.NoSuchElementException 队列中没有元素异常！
//        System.out.println(blockingQueue.remove());
    }

    /**
     * 不抛出异常
     */
    public static void test2() {
        //队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));

        //如果队列满了 就加入不进去  false 不抛出异常
//        System.out.println(blockingQueue.offer("d"));

        //检测队首元素
//        System.out.println(blockingQueue.peek());
//        System.out.println(blockingQueue.element());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        //如果队列空了则 不返回null
        System.out.println(blockingQueue.peek());
    }

    /**
     * 等待,阻塞(一直阻塞)
     */
    public static void test3() throws InterruptedException {
        //队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        //一直阻塞
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
//        blockingQueue.put("d"); //阻塞存, 队列没位置了,进入一直等待阻塞中
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());//阻塞取 当没存在元素会一直阻塞
    }

    /**
     * 等待,阻塞(超时等待)
     */
    public static void test4() throws Exception {
        //队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        //等待超时,如果超出时间就会直接超时返回 false
        System.out.println(blockingQueue.offer("d", 1, TimeUnit.SECONDS));

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        //超时等待，如果从队列中拿不到元素 会等待两秒后返回null
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
    }
}
