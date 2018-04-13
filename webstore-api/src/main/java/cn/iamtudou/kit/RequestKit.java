package cn.iamtudou.kit;

import name.iaceob.kit.httphelper.entity.HttpEntity;
import name.iaceob.kit.httphelper.kit.HttpKit;

import java.nio.charset.Charset;

public class RequestKit {

    public static String req(String url) {
        HttpEntity entity = null;
        int i = 0;
        while (i <= 2) {
            try {
                entity = HttpKit.get(url, Charset.defaultCharset());
                if (entity.getResponseCode() >= 200 && entity.getResponseCode() < 300)
                    return entity.getHtml();
            } catch (Exception e) {
                if (i == 2)
                    throw new RuntimeException("url请求3次失败，msg:" + e.getMessage());
            }
        }

        return null;
    }
}
