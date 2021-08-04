package com.lee.jedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterDemo {
    public static void main(String[] args) {
        JedisCluster jedisCluster = new JedisCluster(HostAndPort.parseString("192.168.1.155:6379"));
        jedisCluster.set("k2", "lee");
        System.out.println(jedisCluster.get("k2"));
    }
}
