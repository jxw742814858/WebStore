package cn.iamtudou.kit;

import redis.clients.jedis.Jedis;

import java.util.Properties;

public class JedisKit {
    private static Jedis jedis;
    private static Properties prop = PropKit.getProp("config.properties");
    private static boolean isDevMode = Boolean.valueOf(prop.getProperty("isDevMode"));
    private static String host = isDevMode ? prop.getProperty("redis.local.host") : prop.getProperty("redis.host");

    public static Jedis getJedis() {
        jedis = new Jedis(host, Integer.valueOf(prop.getProperty("redis.port")));
        if (!isDevMode)
            jedis.auth(prop.getProperty("redis.auth"));
        return jedis;
    }
}
