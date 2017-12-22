package cn.iamtudou.service.impl;

import cn.iamtudou.entity.NewsRecord;
import cn.iamtudou.kit.*;
import cn.iamtudou.service.Service;
import name.iaceob.kit.httphelper.kit.HttpKit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.*;

public class ParseImpl extends Service {
    Properties prop = PropKit.getProp("config.properties");
    String imgFolder = null;

    public void parse() {
        String url = "http://www.163.com";
        Calendar calendar = Calendar.getInstance();
        String todayFolder = String.format("%s-%s-%s", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
        imgFolder = String.format("%s/%s", prop.getProperty("img.path"), todayFolder);
        FileKit.folderOp(imgFolder);
        taskParse(url);
    }

    private void taskParse(String url) {
        String html = null;
        try {
            html = HttpKit.get(url).getHtml();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Map<String, String> urlMap = new HashMap<>();
        Elements elms = Jsoup.parse(html).select("a");
        for (Element ele : elms) {
            if (ParaKit.isNewsUrl(ele.attr("href"))) {
                try {
                    urlMap.put(ele.attr("href"), null);
                } catch (Exception ec) {
                }
            }
        }

        for (Map.Entry<String, String> entry : urlMap.entrySet())
            pageParse(entry.getKey());
    }

    private NewsRecord pageParse(String url) {
        String html = null;
        try {
            html = HttpKit.get(url, Charset.forName("GBK")).getHtml();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Document doc = Jsoup.parse(html);
        Elements elms = doc.select(".post_text");
        elms.select("img.icon").remove();
        Elements imgElms = elms.select("img");

        List<String> imgs = new ArrayList<>();
        if (!elms.isEmpty()) {
            for (Element ele : imgElms)
                imgs.add(ele.attr("src"));

            for (int i = 0; i < imgElms.size(); i++)
                elms.select("img").get(0).replaceWith(new Element("imgTag"));
        }
        String ehtml = elms.html();
        ehtml = ehtml.replace("<imgTag>", "{imgTag}").replace("</imgTag>", "");
        Document textDoc = Jsoup.parse(ehtml);
        String text = textDoc.text();

        String[] imgUrlArr = imgs.toArray(new String[imgs.size()]);
        String[] imgPathArr = new String[imgUrlArr.length];
        //下载资讯中的图片
        for (int i = 0; i < imgUrlArr.length; i++) {
            String ifn = String.format("%s/%s%s", imgFolder, System.currentTimeMillis(), "_tmp.jpg");
            try {
                if (!DownloadKit.downloadNet(imgUrlArr[i], ifn))
                    System.out.println("image download failed!");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            //图片处理
            imgPathArr[i] = ImageKit.imgOperate(ifn);
        }

        NewsRecord nr = new NewsRecord();
        nr.setId(DigestKit.encodeMD5(url));
        nr.setUrl(url);
        nr.setTitle(doc.select(".post_content_main h1").text());
        nr.setCotent(textDoc.text());
        nr.setTimestamp(ParaKit.getTimestamp(doc.select("div.post_time_source").text()));
        nr.setImg_url(imgUrlArr);
        nr.setImg_path(imgPathArr);
        nr.setCreatetime(System.currentTimeMillis());
        nr.setSite("网易");
        nr.setPlate(ParaKit.getPlate(doc.select(".post_crumb").text()));
        return nr;
    }
}
