package com.hazelcast;


import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Groups {
    public static void main(String[] args) {
        Config config1=new Config();
        config1.getGroupConfig().setName("group1").setPassword("psw1");
        Config config2=new Config();
        config2.getGroupConfig().setName("group2").setPassword("psw2");
        HazelcastInstance instance1= Hazelcast.newHazelcastInstance(config1);
        HazelcastInstance instance2= Hazelcast.newHazelcastInstance(config1);
        HazelcastInstance instance3= Hazelcast.newHazelcastInstance(config2);
        System.out.println("size of group1: "+instance1.getCluster().getMembers().size());
        System.out.println("size of group2: "+instance3.getCluster().getMembers().size());
    }
}
