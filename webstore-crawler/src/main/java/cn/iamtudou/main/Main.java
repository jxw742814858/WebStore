package cn.iamtudou.main;

import cn.iamtudou.service.Service;
import cn.iamtudou.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ScheduledExecutorService scheduled = new ScheduledThreadPoolExecutor(10);
        String[] sites = {
                "凤凰网",
                "新华网",
                "人民网",
                "合肥论坛",
                "百度热搜",
                "搜狗热搜"
        };
        for (String site : sites) {
            scheduled.scheduleWithFixedDelay(() -> {
                Service service = null;
                switch (site) {
                    case "凤凰网":
                        service = new IfengParse();
                        break;
                    case "新华网":
                        service = new XinhuanetParse();
                        break;
                    case "人民网":
                        service = new PeopleParse();
                        break;
                    case "合肥论坛":
                        service = new HefeinewsParse();
                        break;
                    case "百度热搜":
                        service = new BaiduhotParse();
                        break;
                    case "搜狗热搜":
                        service = new SougouhotParse();
                        break;

                    default:
                        break;
                }

                try {
                    service.parse();
                } catch (Exception e) {
                    log.error("任务解析失败, msg: ", e);
                }
            }, 0, 1, TimeUnit.HOURS);
        }
    }
}
