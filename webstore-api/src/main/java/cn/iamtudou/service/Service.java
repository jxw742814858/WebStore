package cn.iamtudou.service;

import com.alibaba.fastjson.JSONArray;

import javax.annotation.Resource;

@Resource(name = "service")
public interface Service {

    public JSONArray queryNews();
}
