## **多线程进阶->JUC并发编程**

​	

## 1、什么是JUC

**业务:普通的的线程代码 Thread**

**Runnable** 没有返回值、**效率**相比Callable相对较低

## 2、线程和进程

进程:一个程序，例如：QQ.exe,WECHAT.exe

一个进程包含多个线程，至少包含一个线程！

**Java默认有几个线程？2个：main线程、GC**

线程：比如当前开启的typora进程，然后写入内容自动保存（ 通过线程去负责）

对于Java而言：Thread、Runnable、Callabe

**Java是否可以开启线程么？**不可以



```java
    public synchronized void start() {
        /**
         * This method is not invoked for the main method thread or "system"
         * group threads created/set up by the VM. Any new functionality added
         * to this method in the future may have to also be added to the VM.
         *
         * A zero status value corresponds to state "NEW".
         */
        if (threadStatus != 0)
            throw new IllegalThreadStateException();

        /* Notify the group that this thread is about to be started
         * so that it can be added to the group's list of threads
         * and the group's unstarted count can be decremented. */
        group.add(this);

        boolean started = false;
        try {
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
                /* do nothing. If start0 threw a Throwable then
                  it will be passed up the call stack */
            }
        }
    }
	
	// 本地方法,底层的C++ java无法直接操作硬件
    private native void start0();
```



并发编程：并发、并行

并发：多个线程操作同一个资源

- ​	CPU 一核,模拟出来多条线程，是因为速度太快，看起来像是同时操作

  ```
  package com.deng.demo01;
  
  
  public class Test1 {
      public static void main(String[] args) {
          //获取cpu核数
          //CPU 密集型,IO密集型
          System.out.println(Runtime.getRuntime().availableProcessors());
      }
  }
  ```

  **并发编程的本质：充分利用CPU的资源**

并行：多个程序同时执行



线程有几个状态？

```java
public enum State {
		//新建
        NEW,
		//运行
        RUNNABLE,
		//阻塞
        BLOCKED,
		//等待，死死等待
        WAITING,
		//超时等待
        TIMED_WAITING,
		//中止
        TERMINATED;
    }
```



**wait跟sleep的区别是？**

**1、来自不同的类**

wait=>Object

sleep=>Thread

**2、关于锁的释放**

wait会释放锁，sleep是抱着锁睡觉，不会释放！

**3、使用的范围是不同的**

wait:只能在同步代码块中使用(需要先获取锁 才知道释放的锁)

sleep:可以在任何地方使用

**4、是否需要捕获异常**

wait不需要捕获异常 

sleep需要捕获一个超时等待异常

 都需要捕获一个 中断等待异常

## 3、Lock锁（重点）

>在大多数情况下，应使用以下惯用语：

```JAVA
   Lock l = ...;
	l.lock(); 
	try { 
        // access the resource protected by this lock 		} finally {
        l.unlock();
    } 
```

当在不同范围内发生锁定和解锁时，必须注意确保在锁定时执行的所有代码由try-finally或try-catch保护，以确保在必要时释放锁定。

有三个实现类 

**ReentrantLock:可重入锁(常用)**

**ReentrantReadWriteLock.ReadLock:读锁**

**ReentrantReadWriteLock.WriteLock:写锁**

```JAVA

	//公平锁
    public ReentrantLock() {
        sync = new NonfairSync();
    }

	//非公平锁
    public ReentrantLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
    }
```



**公平锁：**不允许插队，必须等待完毕

**非公平锁（java默认）**：十分不公平 可以允许插队(默认)

```JAVA
package com.deng.demo01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SaleTicketDemo02 {
    public static void main(String[] args) {
        //并发:多个线程操作同一个资源， 把资源类丢入线程
        Ticket2 ticket2 = new Ticket2();

        //@FunctionalInterface 匿名内部类 jdk1.8 lambda表达式 (参数)->{具体代码}
        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                ticket2.sale();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                ticket2.sale();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                ticket2.sale();
            }
        }, "C").start();
    }
}

//lock
//资源类
class Ticket2 {

    //票数
    private int number = 30;

    //官方文档 使用常用语法
    // Lock l = ...;
    // l.lock();
    // try { // access the resource protected by this lock }
    // finally { l.unlock(); }

    Lock lock = new ReentrantLock();

    //卖票的方式
    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName()
                        + "卖出了" + (number--) + "票:剩余：" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//解锁
        }

    }
}
```

### Synchronized跟Lock的区别？

1、Synchorized	是内置的关键字； Lock是接口类

2、Synchorized	无法判断锁状态；Lock可以判断是否获取到了锁

3、Synchorized	会自动释放锁；Lock加锁解锁都是手动释放！如果不释放锁，会出现**死锁**

4、Synchorized	线程1获取锁， 线程二(等待) 当线程一阻塞，线程二会一直等待；Lock不一定会一直等待(tryLock 尝试获取锁)

5、Synchorized	可重入锁，不可以中断的，非公平；Lock，可重入锁，可以判断锁，可自级设置是否非公平(构造方法)

6、Synchorized	适合锁少量的代码同步问题，Lock 适合锁大量的同步代码！



### 问题： 锁是什么？如何判断锁的是谁？

把单例模式，排序算法，死锁，生产者和消费者 手写伪代码出来



## 4、生产者和消费者的问题

### 用Synchorized 实现

```JAVA
package com.deng.product_consumer;

/**
 * 线程之间的通信问题：生产者和消费者问题
 * 线程交替执行 A B操作同一个变量 num=0
 * A: num+1
 * B: num-1
 */

public class A {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
    }
}

//资源类
//判断等待，业务，通知
class Data {
    private int number = 0;

    //+1
    public synchronized void increment() throws InterruptedException {
        if (number != 0) {
            //等待
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        //通知其他线程 我+1完毕
        this.notifyAll();
    }

    //-1
    public synchronized void decrement() throws InterruptedException {
        if (number == 0) {
            //等待
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        //通知其他线程 我-1完毕
        this.notifyAll();
    }
}
```

 

> 数字异常问题: 有A、B两个线程的时候 发现并不会出现一次的问题会正常执行，但是当出现 A、B、C、 D四个线程的时候会出现数字异常的问题！线程的虚假唤醒(理解：当一个条件满足时，很多线程都被唤醒了，但是只需要部分线程唤醒，其他线程不需要唤醒导致)

造成问题的原因是：当num = 0时候，AC线程同时执行，A线程未进入if判断 直接+1，通知其他线程，C线程这个时候执行 进入if判断，条件成立 释放当前锁，并且等待，这个时候如果B线程或者D线程执行 -1操作并且成功，那么当前number =0  此时C线程还在等待 当A线程继续执行 然后+1通知正在等待的C线程 此时 number = 1 并且C线程已经走进入if语句 所以不会再判断，直接往下走，number再+1 =2.

**总结：用if判断 wait方法醒来之后不会继续判断当前条件而是直接往下走，如果用while 方法即使wait醒来以后还会再继续判断当前条件不成立那么会继续wait**

下面是JDK1.8源码文档解释

```
public final void wait()
                throws InterruptedException
导致当前线程等待，直到另一个线程调用该对象的notify()方法或notifyAll()方法。 换句话说，这个方法的行为就好像简单地执行呼叫wait(0) 。
当前的线程必须拥有该对象的显示器。 该线程释放此监视器的所有权，并等待另一个线程通知等待该对象监视器的线程通过调用notify方法或notifyAll方法notifyAll 。 然后线程等待，直到它可以重新获得监视器的所有权并恢复执行。

像在一个参数版本中，中断和虚假唤醒是可能的，并且该方法应该始终在循环中使用：

  synchronized (obj) {
         while (<condition does not hold>)
             obj.wait();
         ... // Perform action appropriate to condition
     } 
该方法只能由作为该对象的监视器的所有者的线程调用。 有关线程可以成为监视器所有者的方式的说明，请参阅notify方法。
异常
IllegalMonitorStateException - 如果当前线程不是对象监视器的所有者。
InterruptedException - 如果任何线程在当前线程等待通知之前或当前线程中断当前线程。 当抛出此异常时，当前线程的中断状态将被清除。
另请参见：
notify() ， notifyAll()
```

把if 改成while 可以使得 ABCD四个线程就正常了





Synchronized -> wait等待 notify 通知

Lock -> await 等待   signal 通知

### 用JUC实现

```JAVA
一个Condition实例本质上绑定到一个锁。 要获得特定Condition实例的Condition实例，请使用其newCondition()方法。

例如，假设我们有一个有限的缓冲区，它支持put和take方法。 如果在一个空的缓冲区尝试一个take ，则线程将阻塞直到一个项目可用; 如果put试图在一个完整的缓冲区，那么线程将阻塞，直到空间变得可用。 我们希望在单独的等待集中等待put线程和take线程，以便我们可以在缓冲区中的项目或空间可用的时候使用仅通知单个线程的优化。 这可以使用两个Condition实例来实现。

  class BoundedBuffer {
   final Lock lock = new ReentrantLock();
   final Condition notFull  = lock.newCondition(); 
   final Condition notEmpty = lock.newCondition(); 

   final Object[] items = new Object[100];
   int putptr, takeptr, count;

   public void put(Object x) throws InterruptedException {
     lock.lock(); try {
       while (count == items.length)
         notFull.await();
       items[putptr] = x;
       if (++putptr == items.length) putptr = 0;
       ++count;
       notEmpty.signal();
     } finally { lock.unlock(); }
   }

   public Object take() throws InterruptedException {
     lock.lock(); try {
       while (count == 0)
         notEmpty.await();
       Object x = items[takeptr];
       if (++takeptr == items.length) takeptr = 0;
       --count;
       notFull.signal();
       return x;
     } finally { lock.unlock(); }
   }
 } 
```



## 5、如何判断锁的是谁？8锁现象

如何判断到底是锁谁？本质就是 锁对象 或者锁类class

```JAVA
package com.deng.lock8;


import java.util.concurrent.TimeUnit;

/**
 * 八锁问题
 * 1、标准情况下，两个线程先打印 发短信还是打电话？ 1发短信 2打电话
 * 2、sendSms延迟4秒，两个线程先打印 发短信还是打电话？ 1发短信 2打电话
 */
public class Test1 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendSms();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone.call();
        }, "B").start();

    }
}

class Phone {

    //synchronized 锁的是对象是方法的调用者
    //两个方法用的是同一个锁，谁先拿到谁就执行！
    public synchronized void sendSms() {

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public synchronized void call() {
        System.out.println("打电话");
    }
}

```



```JAVA
package com.deng.lock8;

import java.util.concurrent.TimeUnit;


/**
 * 八锁问题
 * 3、增加了一个普通方法 hello 输出发短信还是hello? 1hello 2发短信
 * 4、两个对象,两个同步方法 先输出打电话还是发短信？  1打电话 2发短信
 */
public class Test2 {
    public static void main(String[] args) {
        //两个对象
        Phone2 phone1 = new Phone2();
        Phone2 phone2 = new Phone2();

        new Thread(() -> {
            phone1.sendSms();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone2.call();
        }, "B").start();

    }
}

class Phone2 {

    //synchronized 锁的是对象是方法的调用者
    //两个方法用的是同一个锁，谁先拿到谁就执行！
    public synchronized void sendSms() {

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public synchronized void call() {
        System.out.println("打电话");
    }

    //这里没有锁,不是同步方法,不受锁的影响
    public void hello() {
        System.out.println("hello");
    }
}

```



```JAVA
package com.deng.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 八锁问题
 * 5、增加两个静态的同步方法，只有一个对象 先打印发短信还是打电话？ 1发短信2打电话
 * 6、两个对象，两个静态方法 会先发短信还是打电话？  1发短信 2打电话
 */
public class Test3 {
    public static void main(String[] args) {
        //两个对象的Class类模板只有一个 锁定的是同一个class
        Phone3 phone1 = new Phone3();
        Phone3 phone2 = new Phone3();

        new Thread(() -> {
            phone1.sendSms();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone2.call();
        }, "B").start();

    }
}

class Phone3 {

    //synchronized 锁的是对象是方法的调用者
    //static 修饰以后 静态方法
    //类初始化加载就存在了！锁的是类class对象
    public static synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public static synchronized void call() {
        System.out.println("打电话");
    }
}
```



```JAVA
package com.deng.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 八锁问题
 * 7、1个静态同步方法 1个普通同步方法，先打印发短信还是打电话？  1打电话 2发短信
 * 8、两个对象分别调用 一个static 修饰的同步方法  一个普通同步方法，先打印哪个?  1打电话 2发短信
 */
public class Test4 {
    public static void main(String[] args) {
        //两个对象的Class类模板只有一个 锁定的是同一个class
        Phone4 phone1 = new Phone4();
        Phone4 phone2 = new Phone4();

        new Thread(() -> {
            phone1.sendSms();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone2.call();
        }, "B").start();

    }
}

class Phone4 {

    //synchronized 锁的是对象是方法的调用者
    //static 修饰以后 静态方法
    //类初始化加载就存在了！锁的是类class对象
    public static synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    //锁的调用者
    public synchronized void call() {
        System.out.println("打电话");
    }
}
```



### 八锁小结：

new this 锁的是当前调用对象

static 锁的是类 Class模板对象



## 6、集合类不安全

```JAVA
package com.deng.unsafe;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListTest {
    public static void main(String[] args) {
        //并发下 ArrayList 是不安全的
        /**
         * 解决方案：
         * 1. List<String> list = new Vector<>();
         * 2. List<String> list = Collections.synchronizedList(new ArrayList<>());
         * 3. List<String> list = new CopyOnWriteArrayList<>();
         */
        //CopyOnWrite 写入时复制
        //多个线程调用,读取是固定的，写入的时候避免覆盖造成数据问题
        //读写分离 写入的时候复制一份然后将指向原数组地址指向新数组地址
        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}

```



CopyOnWriteArraylist 底层维护了一个Reentranklock 可冲入锁跟一个被voliate 修饰的数组

一般用户多读少写，写入时候复制一份原有数据并且新增数据到新集合中然后替换原有数据引用地址





