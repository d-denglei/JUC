package com.deng.add;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author DengLei
 * @date 2023/03/22 16:11
 */
//模拟停车位  6个车 三个车位 1一个车位空出来了就进去一个车
public class SemaphoreDemo {
    public static void main(String[] args) {
        //线程数量：停车位 //做限流可以用到这个
        //Semaphore的参数代表资源数
        Semaphore semaphore = new Semaphore(3);
        //可以用  Executors.newFixedThreadPool(10) 来模拟十个车

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                //acquire() 得到车位
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到车位");
                    TimeUnit.SECONDS.sleep(2);
                    //停了两秒后 离开
                    System.out.println(Thread.currentThread().getName() + "离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //释放
                    semaphore.release();
                }

            }, String.valueOf(i)).start();
        }

    }
}
