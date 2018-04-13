package cn.iamtudou.service.impl;

import cn.iamtudou.entity.MagnetRecord;
import cn.iamtudou.kit.EncodeKit;
import cn.iamtudou.kit.PropKit;
import cn.iamtudou.kit.RequestKit;
import cn.iamtudou.service.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.stereotype.Service("magnetImpl")
@Primary
public class MagnetImpl extends Service {
    Logger log = LoggerFactory.getLogger(MagnetImpl.class);
    Properties prop = PropKit.getProp("source.properties");
    final String[] dateRegs = prop.getProperty("date.reg").split(";");
    final String[] sizeRegs = prop.getProperty("size.reg").split(";");

    String keyword = null;
    Map<String, JSONObject> ruleMap = new HashMap<>();
    JSONArray commArr = new JSONArray();

    /**
     * 读取解析网站和规则列表
     */
    public JSONArray extractMagnet(String keyword) {
        this.keyword = keyword;
        String[] sites = prop.getProperty("source.site").split(";");
        String[] rules = prop.getProperty("site.rule").split(";");

        List<JSONObject> siteJoL = new ArrayList<>();
        //去重和带 # 号忽略采集
        JSONObject siteJo = null;
        for (String site : sites) {
            if (!site.contains("#")) {
                siteJo = JSONObject.parseObject(site);
                siteJoL.add(siteJo);
            }
        }

        JSONObject ruleJo = null;
        for (String rule : rules) {
            ruleJo = JSONObject.parseObject(rule);
            ruleMap.put(ruleJo.getJSONObject("listPage").getString("sourceSite"), ruleJo);
        }

        ExecutorService es = Executors.newCachedThreadPool();

        for (JSONObject ij : siteJoL) {
            final JSONObject threadJo = ij;
            es.execute(() -> {
                String url = null;

                url = String.format(threadJo.getString("url"), keyword);
                //首页
                Set<MagnetRecord> records = siteReq(url, ruleMap.get(threadJo.getString("site")));
                //翻页
                records.addAll(turnPageReq(ruleMap.get(threadJo.getString("site"))));
                String recJson = JSON.toJSONString(records);
                synchronized (commArr) {
                    commArr.addAll(JSONArray.parseArray(recJson));
                }
            });
        }
        es.shutdown();
        while (true) {
            if (es.isTerminated())
                break;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }

        JSONArray resArr = (JSONArray) commArr.clone();
        commArr.clear();
        return resArr;
    }

    /**
     * 列表页搜索结果解析
     *
     * @param url
     * @param ruleJo
     * @return
     */
    private Set<MagnetRecord> siteReq(String url, JSONObject ruleJo) {
        String html = null;
        url = EncodeKit.encodeCn(url);
        try {
            html = RequestKit.req(url);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }

        Set<MagnetRecord> magList = new HashSet<>();
        MagnetRecord record = null;
        Document doc = Jsoup.parse(html);
        Elements titEs = doc.select(ruleJo.getJSONObject("listPage").getString("titleReg"));
        Elements oiEs = doc.select(ruleJo.getJSONObject("listPage").getString("otherInfoReg"));
        Elements magUrlEs = doc.select(ruleJo.getJSONObject("detailPage").getString("magUrlReg"));
        boolean detailClt = Boolean.valueOf(ruleJo.getJSONObject("listPage").getString("detailCtl"));

        String detailUrl = null, magUrl = null;
        for (int i = 0; i < titEs.size(); i++) {
            record = new MagnetRecord();
            record.setTitle(titEs.get(i).text());
            record.setFileSize(parseOtherInfo(oiEs.get(i).text(), "fileSize"));
            record.setReleaseDate(parseOtherInfo(oiEs.get(i).text(), "releaseDate"));

            if (detailClt) {
                detailUrl = urlFormat(url, titEs.get(i).attr("href"));
                if (StringUtil.isBlank(detailUrl))
                    continue;
                detailUrl = EncodeKit.encodeCn(detailUrl);
                magUrl = detailReq(detailUrl, ruleJo.getJSONObject("detailPage").getString("magUrlReg"));
                if (StringUtil.isBlank(magUrl))
                    continue;
                else
                    record.setMagnetUrl(magUrl);
            } else
                record.setMagnetUrl(magUrlEs.get(i).attr("href"));

            record.setSourceSite(ruleJo.getJSONObject("listPage").getString("sourceSite"));
            magList.add(record);
        }

        return magList;
    }

    /**
     * 解析翻页搜索结果列表
     *
     * @param ruleJo
     * @return
     */
    private Set<MagnetRecord> turnPageReq(JSONObject ruleJo) {
        String url = null;
        Set<MagnetRecord> resList = new HashSet<>();
        for (int pageNum = 2; pageNum < 4; pageNum++) {
            url = String.format(ruleJo.getJSONObject("listPage").getString("turnPageUrl"), keyword, pageNum);
            url = EncodeKit.encodeCn(url);
            resList.addAll(siteReq(url, ruleJo));
        }

        return resList;
    }

    /**
     * 解析详情页磁力
     *
     * @param url 详情页url
     * @return
     */
    private String detailReq(String url, String magReg) {
        String html = null;
        try {
            html = RequestKit.req(url);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
        Elements magEs = Jsoup.parse(html).select(magReg);
        if (CollectionUtils.isNotEmpty(magEs))
            return magEs.first().attr("href");
        return null;
    }

    /**
     * 磁力相关信息解析
     *
     * @param str
     * @param column
     * @return
     */
    private String parseOtherInfo(String str, String column) {
        String[] regs = null;
        switch (column) {
            case "fileSize":
                regs = sizeRegs.clone();
                break;
            case "releaseDate":
                regs = dateRegs.clone();
                break;

            default:
                break;
        }

        Matcher matcher = null;
        for (String reg : regs) {
            matcher = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(str);
            if (matcher.find()) {
                regs = null;
                return matcher.group(column).replace(" ", "");
            }
        }

        regs = null;
        return null;
    }

    /**
     * url处理
     *
     * @param indexUrl
     * @param detailUrl
     * @return
     */
    private String urlFormat(String indexUrl, String detailUrl) {
        if (detailUrl.contains("http"))
            return detailUrl;
        else {
            String protocol = indexUrl.contains("https") ? "https://" : "http://";
            try {
                URL url = new URL(indexUrl);
                return protocol.concat(url.getHost().concat(detailUrl));
            } catch (MalformedURLException e) {
                log.error("detail url format error, msg:", e);
            }
        }

        return null;
    }
}
