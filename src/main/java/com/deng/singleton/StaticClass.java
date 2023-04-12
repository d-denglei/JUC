package com.deng.singleton;

/**
 * 静态内部类
 */
public class StaticClass {

    private StaticClass() {

    }

    public static StaticClass getInstance() {
        return InnerClass.STATIC_CLASS;
    }

    public static class InnerClass {
        private static final StaticClass STATIC_CLASS = new StaticClass();
    }
}
