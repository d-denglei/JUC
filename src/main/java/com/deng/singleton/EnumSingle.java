package com.deng.singleton;


import java.lang.reflect.Constructor;

/**
 * enum 默认也是一个Class类
 */
public enum EnumSingle {

    INSTANCE;

    public EnumSingle getInstance() {
        return INSTANCE;
    }
}

class Test {
    public static void main(String[] args) throws Exception {
        EnumSingle instance = EnumSingle.INSTANCE;
        //这里传入会发现 报无法找到无惨构造器，把这个枚举类反编译回来以后发现 只有一个有参构造
        Constructor<EnumSingle> declaredConstructor = EnumSingle.class.getDeclaredConstructor(String.class, int.class);
        declaredConstructor.setAccessible(true);

        EnumSingle instance1 = declaredConstructor.newInstance();

        System.out.println(instance);
        System.out.println(instance1);
    }
}
