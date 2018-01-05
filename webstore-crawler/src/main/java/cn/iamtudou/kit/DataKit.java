package cn.iamtudou.kit;

import cn.iamtudou.entity.NewsRecord;
import cn.iamtudou.service.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class DataKit {
    private Logger log = LoggerFactory.getLogger(DataKit.class);
    private Jedis jedis = null;
    private Properties prop = PropKit.getProp("config.properties");
    private boolean isDevMode = Boolean.valueOf(prop.getProperty("isDevMode"));
    private String redisHost = isDevMode ? prop.getProperty("redis.local.host") : prop.getProperty("redis.host");
    private int redisPort = Integer.valueOf(prop.getProperty("redis.port"));
    private String redisAuth = prop.getProperty("redis.auth");

    public Jedis getJedis() {
        jedis = new Jedis(redisHost, redisPort);
        if (!isDevMode)
            jedis.auth(redisAuth);

        return jedis;
    }

    /**
     * 数据保存
     *
     * @param dataList
     */
    public void save(List<NewsRecord> dataList, String siteName) {
        if (dataList.isEmpty()) {
            log.warn("dataList is empty at the time of submission");
            return;
        }

        Calendar cal = Calendar.getInstance();
        String keyName = String.format("%s-%s-%s", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));

        try (Jedis jedis = getJedis()) {
            if (jedis.exists(keyName))
                jedis.append(keyName, "," + stringSub(JSON.toJSONString(dataList)));
            else { //在存入新一天的数据时，先删除两天之前的数据
                Set<String> keys = jedis.keys("*");
                Set<String> days = ParaKit.getLastDays();
                for (String k : keys) {
                    if (!days.contains(k))
                        jedis.del(k);
                }
                jedis.set(keyName, stringSub(JSON.toJSONString(dataList)));
            }
            log.info("{}'s data save redis success. size: {}", siteName, dataList.size());
        } catch (Exception e) {
            log.error("", e);
            return;
        }

        Service.addUniqueUrl(dataList);
    }

    /**
     * 查询
     *
     * @param key
     * @return
     */
    public List<NewsRecord> query(String key) {
        if (StringUtil.isBlank(key)) {
            log.warn("the key in the query is empty");
            return null;
        }

        try (Jedis jedis = getJedis()) {
            String res = jedis.get(key);
            if (StringUtil.isBlank(res))
                return null;

            res = "[" + res + "]";
            List<NewsRecord> dataList = JSONObject.parseArray(res, NewsRecord.class);
            return dataList;
        } catch (Exception e) {
            log.error("query redis failed, msg:", e);
            return null;
        }
    }

    private String stringSub(String context) {
        return context.replace("[", "").replace("]", "");
    }
}
