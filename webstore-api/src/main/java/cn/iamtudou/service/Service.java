package cn.iamtudou.service;

import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;

@Resource(name = "service")
public interface Service {

    public JSONObject queryNews(String date);
}
