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
 * 合肥论坛资讯解析
 */
public class HefeinewsParse extends Service {
    private Logger log = LoggerFactory.getLogger(HefeinewsParse.class);

    public void parse() {
        String siteName = "合肥论坛";
        String[] urls = {
                "http://news.hefei.cc/L/12020100.shtml",
                "http://news.hefei.cc/L/12020100_2.shtml"
        };

        for (String url : urls) {
            List<NewsRecord> newsList = pageParse(url, siteName);
            if (CollectionUtils.isNotEmpty(newsList))
                DataKit.submit(newsList);
        }
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
        Elements elms = doc.select("#list_box1 ul li a");
        Map<String, String> urls = new LinkedHashMap<>();
        for (Element et : elms) {
            try {
                urls.put(et.attr("href"), et.text());
            } catch (Exception e) {
            }
        }

        for (Map.Entry<String, String> entry : urls.entrySet()) {
            NewsRecord record = new NewsRecord();
            record.setSite(siteName);
            record.setUrl(entry.getKey());
            record.setId(DigestKit.encodeMD5(record.getUrl()));
            if (existsId(record.getId()))
                continue;
            record.setTitle(entry.getValue());
            record.setTimestamp(System.currentTimeMillis());

            dataList.add(record);
        }

        return dataList;
    }
}
