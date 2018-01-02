package cn.iamtudou.service;

import java.util.HashMap;
import java.util.Map;

public abstract class Service {
    protected static Map<String, String> UNIQUE_URL = new HashMap<>();

    public static void addUniqueUrl(String id) {
        try {
            UNIQUE_URL.put(id, null);
        } catch (Exception e) {
        }
    }

    /**
     * 重复数据判断
     * @param id 数据url MD5加密字符串
     * @return
     */
    protected static boolean existsId(String id) {
        return UNIQUE_URL.keySet().contains(id);
    }

    public void parse() {}
}
