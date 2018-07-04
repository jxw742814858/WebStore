package cn.iamtudou.controller;

import cn.iamtudou.constant.MessageConst;
import cn.iamtudou.entity.ResponseEntity;
import cn.iamtudou.entity.SimpleEntity;
import cn.iamtudou.entity.StaffEntity;
import cn.iamtudou.service.StaffFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/staff")
public class StaffController {

    @Autowired
    StaffFactory staffFactory;

    ResponseEntity respWarnModel = new ResponseEntity(-1, MessageConst.VALUE_WARNING);

    //    @RequestMapping(value = "/staffLogin", method = RequestMethod.POST)
    public ResponseEntity staffLogin(@RequestBody SimpleEntity entity) {
        if (null == entity) {
            return respWarnModel;
        }
        return staffFactory.staffLoginJudge(entity.getId(), entity.getPassword());
    }

    //    @RequestMapping(value = "/staffRegister", method = RequestMethod.POST)
    public ResponseEntity staffRegister(@RequestBody SimpleEntity entity) {
        if (null == entity) {
            return respWarnModel;
        }
        entity.setPassword(DigestUtils.md5Hex(entity.getPassword()).toUpperCase());
        return staffFactory.staffRegister(entity);
    }

    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    public ResponseEntity updateStaffInfo(@RequestBody StaffEntity entity) {
        if (null == entity) {
            return respWarnModel;
        }
        return staffFactory.updateStaffInfo(entity);
    }

    @RequestMapping(value = "/editPasswd", method = RequestMethod.POST)
    public ResponseEntity editStaffPasswd(@RequestBody String password, Integer id) {
        if (password == null || id == null || id < 1) {
            return respWarnModel;
        }

        password = DigestUtils.md5Hex(password).toUpperCase();
        return staffFactory.editStaffPassword(password, id);
    }
}
