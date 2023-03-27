package com.deng.function;

import java.util.function.Predicate;

/**
 * Predicate 断定型接口，输入一个参数 返回值只能是布尔值
 */
public class Demo02 {
    public static void main(String[] args) {
        //传入一个数据返回一个布尔值
        //可用做字符串判断
//        Predicate<String> predicate = new Predicate<String>() {
//            @Override
//            public boolean test(String str) {
//                return str.isEmpty();
//            }
//        };
        Predicate<String> predicate = String::isEmpty;

        System.out.println(predicate.test(""));
    }
}
