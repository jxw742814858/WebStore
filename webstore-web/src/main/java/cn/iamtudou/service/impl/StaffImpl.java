package cn.iamtudou.service.impl;

import cn.iamtudou.constant.MessageConst;
import cn.iamtudou.entity.StaffEntity;
import cn.iamtudou.mapper.StaffMapper;
import cn.iamtudou.service.StaffFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("staffImpl")
public class StaffImpl implements StaffFactory {

    @Resource
    private StaffMapper staffMapper;

    @Override
    public String staffLoginJudege(String telephone, String password) {
        StaffEntity entity = staffMapper.staffLogin(telephone);
        if (entity == null) {
            return MessageConst.ACCOUNT_NOT_EXISTS;
        }

        if (DigestUtils.md5Hex(password).toUpperCase().equals(entity.getPassword())) {
            return MessageConst.SUCCESS;
        } else {
            return MessageConst.PASSWORD_NOT_PASSED;
        }
    }
}
