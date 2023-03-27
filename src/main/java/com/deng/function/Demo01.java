package com.deng.function;

import java.util.function.Function;

/**
 * Function 函数数接口，有一个输入参数，有一个输出参数
 * 只要是函数型接口 可以用lambda表达式简化
 */
public class Demo01 {
    public static void main(String[] args) {
        // 点开Function<T,R> 发现传入参数 T,返回的类型是R
//        Function function = new Function<String, String>() {
//            @Override
//            public String apply(String str) {
//                return str;
//            }
//        };
        //用lambda写法
//        Function function = (str) ->{
//            return str;
//        };
        //更精简的写法
        Function function = str -> str;

        System.out.println(function.apply("123"));
    }
}
