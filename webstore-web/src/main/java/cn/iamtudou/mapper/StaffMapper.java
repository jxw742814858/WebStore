package cn.iamtudou.mapper;

import cn.iamtudou.entity.SimpleEntity;
import cn.iamtudou.entity.StaffEntity;

import java.util.List;

public interface StaffMapper {

    List<SimpleEntity> staffLogin(String telephone);

    void staffRegister(SimpleEntity info);

    void updateStaffInfo(StaffEntity entity);

    void editStaffPassword(String password, int id);
}
