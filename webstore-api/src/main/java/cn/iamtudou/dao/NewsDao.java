package cn.iamtudou.dao;

import cn.iamtudou.kit.PropKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.Properties;

@Repository
public class NewsDao {
    private Logger log = LoggerFactory.getLogger(NewsDao.class);
    private Properties prop = PropKit.getProp("config.properties");
    private boolean isDevMode = Boolean.valueOf(prop.getProperty("isDevMode"));
    private String redisHost = isDevMode ? prop.getProperty("redis.local.host") : prop.getProperty("redis.host");
    private int redisPort = Integer.valueOf(prop.getProperty("redis.port"));
    private String redisAuth = prop.getProperty("redis.auth");

    public String query(String key) {
        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            if (!isDevMode)
                jedis.auth(redisAuth);

            String res = jedis.get(key);
            return res;
        } catch (Exception e) {
            log.error("redis query failed, msg:", e);
            return null;
        }
    }
}
