package com.deng.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author DengLei
 * @date 2023/03/21 10:54
 */

public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //new Thread(new  Runnable()).start();
        //new Thread(new  MyThread()).start();
        //等价于 new Thread(new FutureTask<V>( Callable  )).start();
        new Thread().start();//如何启动Callable

        MyThread myThread = new MyThread();
        FutureTask<String> futureTask = new FutureTask<>(myThread);
        //添加适配类绑定到Thread中

        new Thread(futureTask, "A").start();
        new Thread(futureTask, "B ").start();
        String o = futureTask.get();//get方法可能会产生阻塞 会等待线程返回结果
        System.out.println(o);

    }
}

class MyThread implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("call()");
        return "123456";
    }
}

