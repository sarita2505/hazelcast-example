package com.hazelcast;


import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

import java.util.Queue;

public class Client {
    public static void main(String[] args) {
        ClientConfig clientConfig=new ClientConfig();
        HazelcastInstance instance= HazelcastClient.newHazelcastClient();
        Queue<String> queue=instance.getQueue("queue");
        queue.add("hii");
        queue.add("hello");
        instance.shutdown();
    }
}
