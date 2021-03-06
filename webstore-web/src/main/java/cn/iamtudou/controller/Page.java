package cn.iamtudou.controller;

import cn.iamtudou.entity.ResponseEntity;
import cn.iamtudou.entity.SimpleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Page {

    @Autowired
    StaffController staffController;

    @RequestMapping (value = "/index")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/register")
    public String register() {
        return "register";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
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
