package com.deng.read_write_lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 独占锁(写锁) 一次只能一个线程占有
 * 共享锁(读锁) 多个线程可以同时占有
 * ReadWriteLock
 * 读-读 共存！
 * 读-写 不共存！
 * 写-写 不共存！
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
//        MyCache myCache = new MyCache();
        MyCacheLock myCache = new MyCacheLock();
        for (int i = 1; i <= 10; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.put(temp + "", temp + "");
            }, String.valueOf(i)).start();
        }
        for (int i = 1; i <= 10; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.get(temp + "");
            }, String.valueOf(i)).start();
        }

    }
}

class MyCache {

    private volatile Map<String, Object> map = new HashMap<>();

    //存,写
    public void put(String key, Object value) {
        System.out.println(Thread.currentThread().getName() + "写入" + key);
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + "写入ok");
    }

    //取,读
    public void get(String key) {
        System.out.println(Thread.currentThread().getName() + "读取" + key);
        Object o = map.get(key);
        System.out.println(Thread.currentThread().getName() + "读取ok");

    }
}

//加锁的
class MyCacheLock {
    private volatile Map<String, Object> map = new HashMap<>();
    //加读写锁:更加细粒度的控制
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReentrantLock lock = new ReentrantLock();

    //存,写
    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + "写入" + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入ok");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    //如果读不加锁的话 可能会造成幻读问题 开始读到一个内容 然后读取到另外一个内容
    //取,读
    public void get(String key) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "读取" + key);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取ok");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}