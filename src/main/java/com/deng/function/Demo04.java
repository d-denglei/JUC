package com.deng.function;

import java.util.function.Supplier;

/**
 * Supplier 没有参数只有返回值 供给形接口
 *
 * @author denglei
 * @date 2023/3/27 22:58
 */
public class Demo04 {
    public static void main(String[] args) {
        Supplier<Integer> supplier = () -> 1024;
        Integer integer = supplier.get();
        System.out.println(integer);
    }
}
