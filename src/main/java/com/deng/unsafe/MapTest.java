package com.deng.unsafe;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {
    public static void main(String[] args) {
        //map 日常工作如果要用map切保证安全 尽量用ConCurrentHashMap
        //默认等价于   new HashMap<>(16,0.75f)
//        HashMap<String, String> map = new HashMap<>();
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 5));
                System.out.println(map);
            }).start();
        }
    }
}
