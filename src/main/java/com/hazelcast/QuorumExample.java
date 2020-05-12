package com.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.LockConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.QuorumConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;
import com.hazelcast.quorum.QuorumFunction;
import com.hazelcast.quorum.QuorumType;

import java.util.Collection;
import java.util.Map;

public class QuorumExample {
    public static void main(String[] args) {
        QuorumConfig quorumConfig = new QuorumConfig();
        quorumConfig.setName("max_two").setEnabled(true).setSize(2);
        quorumConfig.setType(QuorumType.WRITE);

        quorumConfig.setQuorumFunctionImplementation(new QuorumFunction() {
            @Override
            public boolean apply(Collection<Member> members) {
                return (members.size() >= 2);
            }
        });
        System.out.println("quorum object created name is:"+quorumConfig.getName()+"quorum size is:"+quorumConfig.getSize());
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName("max_two").setQuorumName("max_two");
        Config config = new Config();
        LockConfig lockConfig = new LockConfig();
        lockConfig.setName("myLock")
                .setQuorumName("max_two");
        config.addLockConfig(lockConfig);

        config.addMapConfig(mapConfig);
        config.addQuorumConfig(quorumConfig);
        HazelcastInstance instance1 = Hazelcast.newHazelcastInstance();
       // HazelcastInstance instance2 = Hazelcast.newHazelcastInstance();
        Map<Integer, String> map = instance1.getMap("max_two");
        map.put(1, "hii");
        map.put(2, "hello");

        for (String val : map.values()) {
            System.out.println(val);
        }
        //instance1.getLifecycleService().shutdown();
//        instance2.getLifecycleService().shutdown();
//        map.put(3, "hii");
//
//       for (String val : map.values()) {
//           System.out.println(val);
//       }
    }

}
