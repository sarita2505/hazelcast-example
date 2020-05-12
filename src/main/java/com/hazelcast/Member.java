package com.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.concurrent.BlockingQueue;

public class Member {
    public static void main(String[] args) {
        HazelcastInstance instance=Hazelcast.newHazelcastInstance();
        System.out.println("hazelcast started");
        BlockingQueue<String> strings=instance.getQueue("queue");
        try {
            for (;;)
                System.out.println("values are :"+strings.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
