package cn.iamtudou.kit;

import cn.iamtudou.entity.NewsRecord;
import cn.iamtudou.service.Service;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Calendar;
import java.util.List;

public class DataKit {
    private static Logger log = LoggerFactory.getLogger(DataKit.class);

    public static void submit(List<NewsRecord> dataList) {
        if (dataList.isEmpty()) {
            log.warn("dataList is empty at the time of submission");
            return;
        }

        String keyName = null;
        Calendar cal = Calendar.getInstance();
        keyName = String.format("%s-%s-%s", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));

        try (Jedis jedis = JedisKit.getJedis()) {
            if (jedis.exists(keyName))
                jedis.append(keyName, JSON.toJSONString(dataList));
            else
                jedis.set(keyName, JSON.toJSONString(dataList));
            log.info("data save redis success. size: {}", dataList.size());
        } catch (Exception e) {
            log.error("", e);
            return;
        }

        for (NewsRecord record : dataList)
            Service.addUniqueUrl(record.getId());

    }
}
