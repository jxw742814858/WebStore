package cn.iamtudou.service.impl;

import cn.iamtudou.dao.NewsDao;
import cn.iamtudou.kit.ParaKit;
import cn.iamtudou.service.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service("newsImpl")
public class NewsImpl extends Service {
    private Logger log = LoggerFactory.getLogger(NewsImpl.class);

    @Autowired
    private NewsDao newsDao;

    public JSONObject queryNews(String date) {
        JSONArray arr = null;
        try {
            String res = ParaKit.fomatStr(newsDao.query(date));
            if (StringUtils.isEmpty(res))
                return null;
            arr = JSON.parseArray(res);

            List<String> sites = new ArrayList<>();
            sites.add(arr.getJSONObject(0).getString("site"));
            for (int i = 0; i < arr.size(); i++) {
                JSONObject jobj = arr.getJSONObject(i);
                if (!sites.contains(jobj.getString("site")))
                    sites.add(jobj.getString("site"));
            }

            List<List<JSONObject>> news = new ArrayList<>();
            for (String site : sites) {
                List<JSONObject> parts = new ArrayList<>();
                for (int j = 0; j < arr.size(); j++) {
                    JSONObject jobj1 = arr.getJSONObject(j);
                    if (jobj1.getString("site").equals(site))
                        parts.add(jobj1);
                }
                news.add(parts);
            }

            JSONObject resObj = new JSONObject();
            for (int k = 0; k < news.size(); k++)
                resObj.put(sites.get(k), news.get(k));

            log.info("query data complete! para value is [{}]", date);
            return resObj;
        } catch (Exception e) {
            log.error("query data failed! msg:", e);
            return null;
        }
    }
}
