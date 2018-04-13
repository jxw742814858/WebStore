package cn.iamtudou.controller;

import cn.iamtudou.service.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IndexController {
    @Autowired
    private Service service;

    @RequestMapping(value = "/queryNews", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject queryNews(@RequestBody String date) {
        return service.queryNews(date);
    }

    @RequestMapping(value = "/searchMagnet", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray searchMagnet(@RequestBody String keyword) {
        return service.extractMagnet(keyword);
    }
}
