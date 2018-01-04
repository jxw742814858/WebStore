package cn.iamtudou.service.impl;

import cn.iamtudou.entity.NewsRecord;
import cn.iamtudou.kit.DataKit;
import cn.iamtudou.kit.DigestKit;
import cn.iamtudou.kit.ReqKit;
import cn.iamtudou.service.Service;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Hao6vParse extends Service {
    private Logger log = LoggerFactory.getLogger(Hao6vParse.class);
    private DataKit dataKit = new DataKit();

    public void parse() {
        String siteName = "Hao6v电影网";
        Map<String, String> urls = new LinkedHashMap<>();
        urls.put("http://www.hao6v.com/gvod/zx.html", "电影");
        urls.put("http://www.hao6v.com/gvod/dsj.html", "电视剧");

        List<NewsRecord> newsList = pageParse(urls, siteName);
        if (CollectionUtils.isNotEmpty(newsList))
            dataKit.save(newsList, siteName);
    }

    private List<NewsRecord> pageParse(Map<String, String> urls, String siteName) {
        List<NewsRecord> dataList = new ArrayList<>();
        String html = null;
        for (Map.Entry<String, String> entry : urls.entrySet()) {
            try {
                html = ReqKit.reqRetry(entry.getKey(), false, "GBK", "HTML");
            } catch (Exception e) {
                log.error("", e);
                continue;
            }

            Document doc = Jsoup.parse(html);
            Elements elms = doc.select(".list li a");
            Map<String, String> links = new LinkedHashMap<>();
            for (Element et : elms) {
                try {
                    links.put(et.attr("href"), et.text());
                } catch (Exception e) {
                }
            }

            for (Map.Entry<String, String> le : links.entrySet()) {
                NewsRecord record = new NewsRecord();
                record.setUrl(le.getKey());
                record.setId(DigestKit.encodeMD5(record.getUrl()));
                if (existsId(record.getId()))
                    continue;
                record.setTitle(le.getValue());
                record.setCreatetime(System.currentTimeMillis());
                record.setSite(siteName);
                record.setType(entry.getValue());

                dataList.add(record);
            }
        }

        return dataList;
    }
}
