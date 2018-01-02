package cn.iamtudou.main;

import cn.iamtudou.service.Service;
import cn.iamtudou.service.impl.*;

public class Main {

    public static void main(String[] args) {
        Service service = null;
        String[] sites = {
//                "凤凰网",
//                "新华网",
//                "人民网",
//                "合肥论坛",
//                "百度热搜",
                "搜狗热搜",
                ""
        };
        for (String site : sites) {
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

            service.parse();
        }
    }
}
