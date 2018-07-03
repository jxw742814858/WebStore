package cn.iamtudou.controller;

import cn.iamtudou.service.StaffFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaffController {

    @Autowired
    private StaffFactory staffImpl;

    @RequestMapping(value = "")
    public String staffLogin() {
        return "";
    }
}
