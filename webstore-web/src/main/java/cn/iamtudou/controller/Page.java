package cn.iamtudou.controller;

import cn.iamtudou.entity.ResponseEntity;
import cn.iamtudou.entity.SimpleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Page {

    @Autowired
    StaffController staffController;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndex() {
        return "index";
    }

    @RequestMapping(value = "/registerJudge", method = RequestMethod.POST)
    public ResponseEntity registerJudge(@RequestBody SimpleEntity entity) {
        if (entity == null) {
            return null;
        }

        if (entity.getAccountType() == 3) {
            return staffController.staffRegister(entity);
        }

        return null;
    }

    @RequestMapping(value = "/loginJudge", method = RequestMethod.POST)
    public ResponseEntity loginJudge(@RequestBody SimpleEntity entity) {
        if (entity == null) {
            return null;
        }

        if (entity.getAccountType() == 3) {
            return staffController.staffLogin(entity);
        }

        return null;
    }
}
