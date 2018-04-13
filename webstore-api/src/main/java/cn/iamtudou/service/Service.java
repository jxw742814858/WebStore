package cn.iamtudou.service;

import cn.iamtudou.service.impl.MagnetImpl;
import cn.iamtudou.service.impl.NewsImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;

@org.springframework.stereotype.Service("service")
public abstract class Service {

    @Autowired
    NewsImpl newsImpl;
    @Autowired
    MagnetImpl magnetImpl;

    public JSONObject queryNews(String date){
        return newsImpl.queryNews(date);
    }

    public JSONArray extractMagnet(String keyword) {
        return magnetImpl.extractMagnet(keyword);
    }
}
