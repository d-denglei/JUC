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
