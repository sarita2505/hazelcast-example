package com.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;

public class HzServer {
    public static void main(String[] args) {
        HazelcastInstance instance1 = Hazelcast.newHazelcastInstance();
        HazelcastInstance instance2 = Hazelcast.newHazelcastInstance();
     //   HazelcastInstance instance = Hazelcast.getOrCreateHazelcastInstance();
        Map<Integer, String> stringMap = instance1.getMap("stringMap");
        stringMap.put(1, "hii");
        stringMap.put(2, "hello");
        stringMap.put(3, "bye");
        System.out.println("size of hazelcast instance" + Hazelcast.getAllHazelcastInstances().size());
        System.out.println("get instance ny name" + Hazelcast.getHazelcastInstanceByName("instance1"));

    }


}
