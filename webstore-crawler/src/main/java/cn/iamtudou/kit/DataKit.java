package cn.iamtudou.kit;

import cn.iamtudou.entity.NewsRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;

public class DataKit {
    private static Logger log = LoggerFactory.getLogger(DataKit.class);

    public static void submit(List<NewsRecord> dataList) {
        if (dataList.isEmpty()) {
            log.warn("dataList is empty at the time of submission");
            return;
        }

        try (Jedis jedis = JedisKit.getJedis()) {
            jedis.set("news_list".getBytes(), SerializeKit.serialize(dataList));
            log.info("data save redis success. size: {}", dataList.size());
        }

    }
}
