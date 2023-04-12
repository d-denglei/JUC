package com.deng.singleton;

import java.lang.reflect.Constructor;

/**
 * 懒汉式单例模式
 */
public class Lazy {
    private Lazy() {
        //判断是否通过反射去直接访问私有构造方法生成对象
        synchronized (Lazy.class) {
            if (lazy != null) {
                throw new RuntimeException("不允许通过反射去创建对象...");
            }
        }
        System.out.println(Thread.currentThread().getName() + " ok");
    }

    //通过添加volatile 来禁止防止指令重排导致的问题
    private volatile static Lazy lazy;

    //双重检测锁模式的 懒汉式单例 DCL懒汉式
    public static Lazy getInstance() {
        if (lazy == null) {
            synchronized (Lazy.class) {
                if (lazy == null) {
                    lazy = new Lazy();  // 不是一个原子操作
                    /**
                     * 1、分配内存空间
                     * 2、执行构造方法，初始化对象
                     * 3、把这个对象指向这个空间
                     *  正常流程是 123 如果发生指令重排 那么就可能会变成 132
                     * 线程b 132 执行  此时 对象还没被初始化
                     */
                }
            }
        }
        return lazy;
    }

    public static void main(String[] args) throws Exception {

//        Lazy lazy = Lazy.getInstance();
        Constructor<Lazy> declaredConstructors = Lazy.class.getDeclaredConstructor(null);
        //暴力反射
        declaredConstructors.setAccessible(true);
        Lazy lazy = declaredConstructors.newInstance();
        Lazy lazy1 = declaredConstructors.newInstance();
        System.out.println(lazy);
        System.out.println(lazy1);

    }
}
