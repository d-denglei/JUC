package com.deng.callable;

import java.util.concurrent.*;

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
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        MyThread myThread = new MyThread();
        FutureTask<String> futureTask = new FutureTask<>(myThread); // new FutureTask参数可以用lambda表达式
        //添加适配类绑定到Thread中

        Future<String> submit = executorService.submit(myThread);
        String s1 = submit.get();
        System.out.println(s1);

//        String s = futureTask.get();
//        System.out.println(s);

//        new Thread(futureTask, "A").start();
//        new Thread(futureTask, "B ").start();
        
        String o = futureTask.get();//get方法可能会产生阻塞 会等待线程返回结果
        System.out.println(o);
        executorService.shutdown();
    }
}

class MyThread implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("call()");
        return "11111";
    }
}

