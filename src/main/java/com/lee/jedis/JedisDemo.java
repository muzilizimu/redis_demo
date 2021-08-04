package com.lee.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Random;

public class JedisDemo {

    private static Jedis jedis = new Jedis("192.168.89.128",6379);

    public static void sendCode(String phone){
        String countKey = phone + "-count";
        String codeKey = phone + "-code";
        String count = jedis.get(countKey);
        if (count == null) {
            jedis.setex(countKey, 24*6*60, "1");
        }else if ( Integer.parseInt(count) <= 2 ){
            jedis.incr(countKey);
        }else {
            System.out.println("can not deliver");
        }
        jedis.setex(codeKey,120,getCode());

    }

    public static void verifyCode(String phone,String code){
        String codeKey = phone + "-code";
        String redisCode = jedis.get(codeKey);
        if (redisCode != null){
            if (redisCode.equals(code)){
                System.out.println("验证码正确");
            }else {
                System.out.println("验证码错误");
            }
        } else {
            System.out.println("验证码已过期");
        }
    }

   public static String getCode(){
        StringBuilder code = new StringBuilder("");
        for (int i = 0; i < 6; i++) {
            int rand = new Random().nextInt(10);
            code.append(rand);
        }
        return code.toString();
    }

    @Test
    public void test02(){
        sendCode("15528153965");
        verifyCode("15528153965","541310");
    }

    @Test
    public void test01(){
        //set
        jedis.sadd("name","lucy");
        System.out.println(jedis.smembers("name"));
        //hash
        jedis.hset("user","id","10");
        System.out.println(jedis.hget("user","id"));
        //zet
        jedis.zadd("dragon",1500,"vagaries");
        System.out.println(jedis.zrange("dragon", 0, -1));
        //
        jedis.setex("k2",1,"v2");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String s = jedis.get("k2");
        System.out.println(s);
        System.out.println("hello,world");
        System.out.println("hot-fix111");
        System.out.println("master");
    }
}
