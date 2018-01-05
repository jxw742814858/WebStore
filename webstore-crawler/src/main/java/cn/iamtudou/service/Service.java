package cn.iamtudou.service;

import cn.iamtudou.entity.NewsRecord;
import cn.iamtudou.kit.DataKit;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Service {
    protected static Map<String, String> UNIQUE_URL = new HashMap<>();

    public static void addUniqueUrl(List<NewsRecord> dataList) {
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (NewsRecord nr : dataList) {
                try {
                    UNIQUE_URL.put(nr.getId(), null);
                } catch (Exception e) {
                }
            }
        }
    }

    public static void delUniqueUrl() {
        UNIQUE_URL.clear();
    }

    /**
     * 加载数据库中已有数据，不抓取重复数据
     */
    public static void loadData() {
        Calendar cal = Calendar.getInstance();
        String key = String.format("%s-%s-%s", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        DataKit dataKit = new DataKit();
        List<NewsRecord> dataList = dataKit.query(key);
        addUniqueUrl(dataList);
    }

    /**
     * 重复数据判断
     *
     * @param id 数据url MD5加密字符串
     * @return
     */
    protected static boolean existsId(String id) {
        return UNIQUE_URL.keySet().contains(id);
    }

    public void parse() {
    }
}
