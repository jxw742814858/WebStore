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

import java.util.*;

/**
 * 人民网资讯解析
 */
public class PeopleParse extends Service {
    private Logger log = LoggerFactory.getLogger(PeopleParse.class);

    public void parse() {
        String siteName = "人民网";
        String url = "http://www.people.com.cn/";
        List<NewsRecord> newsList = pageParse(url, siteName);
        if (CollectionUtils.isNotEmpty(newsList))
            DataKit.submit(newsList);
        log.debug("{}'s data parse complete! size:{}", siteName, newsList.size());
    }

    private List<NewsRecord> pageParse(String url, String siteName) {
        List<NewsRecord> dataList = new ArrayList<>();
        String html = null;
        try {
            html = ReqKit.reqRetry(url, false, "GBK", "HTML");
        } catch (Exception e) {
            log.error("", e);
            return null;
        }

        Document doc = Jsoup.parse(html);
        Elements elms = doc.select("#rmw_a a[href^=http://politics.people.com.cn]");
        Map<String, String> urls = new LinkedHashMap<>();
        for (Element et : elms) {
            try {
                urls.put(et.attr("href"), et.text());
            } catch (Exception e) {
            }
        }

        for (Map.Entry<String, String> entry : urls.entrySet()) {
            NewsRecord record = new NewsRecord();
            record.setUrl(entry.getKey());
            record.setTitle(entry.getValue());
            record.setId(DigestKit.encodeMD5(record.getUrl()));
            if (existsId(record.getId()))
                continue;
            record.setCreatetime(System.currentTimeMillis());
            record.setSite(siteName);

            dataList.add(record);
        }

        return dataList;
    }
}
