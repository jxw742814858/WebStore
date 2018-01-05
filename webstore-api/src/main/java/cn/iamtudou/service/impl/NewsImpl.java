package cn.iamtudou.service.impl;

import cn.iamtudou.dao.NewsDao;
import cn.iamtudou.kit.ParaKit;
import cn.iamtudou.service.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service("newsImpl")
public class NewsImpl implements Service {
    private Logger log = LoggerFactory.getLogger(NewsImpl.class);

    @Autowired
    private NewsDao newsDao;

    public JSONArray queryNews() {
        JSONArray arr = null;
        try {
            String res = ParaKit.fomatStr(newsDao.query(ParaKit.gnrTodayKey()));
            arr = JSON.parseArray(res);
            log.debug("query data complete!");
            return arr;
        } catch (Exception e) {
            log.error("query data failed! msg:", e);
            return null;
        }
    }
}
