package com.hazelcast.builder;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.HashMap;
import java.util.Map;

public class HazelCastInstanceBuilder {

    private static Map<String, Config> configMap = new HashMap<>();

    public static HazelcastInstance build(String configName) {
        Config config = getConfig(configName);
        return Hazelcast.newHazelcastInstance(config);
    }

    private static Config getConfig(String configName) {
        if (configMap.containsKey(configName)) {
            return configMap.get(configName);
        } else {
            Config config = new Config();
            config.getGroupConfig().setName(configName);
            configMap.put(configName, config);
            return config;
        }
    }
}
