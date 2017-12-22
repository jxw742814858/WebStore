package cn.iamtudou.kit;

import redis.clients.jedis.Jedis;

import java.util.Properties;

public class JedisKit {
    private static Jedis jedis;
    private static Properties prop = PropKit.getProp("config.properties");

    public static Jedis getJedis() {
        jedis = new Jedis(prop.getProperty("redis.host"), Integer.valueOf(prop.getProperty("redis.port")));
        return jedis;
    }
}
