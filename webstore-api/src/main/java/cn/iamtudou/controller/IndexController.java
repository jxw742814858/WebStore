package cn.iamtudou.controller;

import cn.iamtudou.service.Service;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Autowired
    private Service service;

    @RequestMapping(value = "/queryNews", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray queryNews() {
        return service.queryNews();
    }
}
