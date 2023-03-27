package com.deng.function;

import java.util.function.Consumer;

/**
 * Consumer 消费型接口 : 只要输入 没有返回值
 *
 * @author denglei
 * @date 2023/3/27 22:54
 */
public class Demo03 {
    public static void main(String[] args) {
//        Consumer<String> consumer = new Consumer<String>() {
//            @Override
//            public void accept(String str) {
//                System.out.println(str);
//            }
//        };
        Consumer<String> consumer = str -> System.out.println(str);
        consumer.accept("123");
    }
}
