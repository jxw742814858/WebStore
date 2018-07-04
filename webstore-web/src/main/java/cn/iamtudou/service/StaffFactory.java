package cn.iamtudou.service;

import cn.iamtudou.entity.ResponseEntity;
import cn.iamtudou.entity.SimpleEntity;
import cn.iamtudou.entity.StaffEntity;

public interface StaffFactory {

    /**
     * 员工登陆校验
     * @param telephone
     * @param password
     * @return
     */
    ResponseEntity staffLoginJudge(String telephone, String password);

    /**
     * 员工注册
     * @param entity
     * @return
     */
    ResponseEntity staffRegister(SimpleEntity entity);

    /**
     * 员工信息修改
     * @param entity
     * @return
     */
    ResponseEntity updateStaffInfo(StaffEntity entity);

    /**
     * 员工登陆密码修改
     * @param password
     * @param id
     * @return
     */
    ResponseEntity editStaffPassword(String password, int id);
}
