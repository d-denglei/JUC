package com.deng.unsafe;

import java.util.HashSet;
import java.util.UUID;

/**
 * java.util.ConcurrentModificationException 并发修改异常
 */
public class SetTest {
    public static void main(String[] args) {
        HashSet<String> list = new HashSet<>();
//        Set<String> list = Collections.synchronizedSet(new HashSet<>());
//        CopyOnWriteArraySet<String> list = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
