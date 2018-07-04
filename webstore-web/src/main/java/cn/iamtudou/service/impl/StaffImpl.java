package cn.iamtudou.service.impl;

import cn.iamtudou.constant.MessageConst;
import cn.iamtudou.entity.ResponseEntity;
import cn.iamtudou.entity.SimpleEntity;
import cn.iamtudou.entity.StaffEntity;
import cn.iamtudou.mapper.StaffMapper;
import cn.iamtudou.service.StaffFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("staffImpl")
public class StaffImpl implements StaffFactory {
    private Logger log = LoggerFactory.getLogger(StaffImpl.class);

    @Resource
    private StaffMapper staffMapper;

    @Override
    public ResponseEntity staffLoginJudge(String telephone, String password) {
        List<SimpleEntity> entitys = null;
        ResponseEntity respModel = new ResponseEntity();
        try {
            entitys = staffMapper.staffLogin(telephone);
        } catch (Exception e) {
            log.error("", e);
            respModel.setRespCode(-1);
            respModel.setRespDescribe(MessageConst.NETWORK_ANOMARY);
            return respModel;
        }
        if (entitys == null) {
            respModel.setRespCode(-1);
            respModel.setRespDescribe(MessageConst.ACCOUNT_NOT_EXISTS);
            return respModel;
        }

        if (DigestUtils.md5Hex(password).toUpperCase().equals(entitys.get(0).getPassword())) {
            respModel.setRespCode(0);
            respModel.setRespDescribe(MessageConst.SUCCESS);
        } else {
            respModel.setRespCode(-1);
            respModel.setRespDescribe(MessageConst.PASSWORD_NOT_PASSED);
        }

        log.info("detection of the [Staff Login] action, tel: {}, status: {}", telephone,
                respModel.getRespCode().equals(0) ? "success" : "failed");
        return respModel;
    }

    @Override
    public ResponseEntity staffRegister(SimpleEntity info) {
        ResponseEntity respModel = new ResponseEntity();
        try {
            staffMapper.staffRegister(info);
            respModel.setRespCode(0);
            respModel.setRespDescribe(MessageConst.REGISTER_SUCCESS);

        } catch (Exception e) {
            log.error("", e);
            respModel.setRespCode(-1);
            respModel.setRespDescribe(MessageConst.NETWORK_ANOMARY);
        }
        log.info("detection of the [Staff Register] action, tel: {}, status: {}", info.getId(),
                respModel.getRespCode().equals(0) ? "success" : "failed");
        return respModel;
    }

    @Override
    public ResponseEntity updateStaffInfo(StaffEntity entity) {
        ResponseEntity respModel = new ResponseEntity();
        try {
            staffMapper.updateStaffInfo(entity);
            respModel.setRespCode(0);
            respModel.setRespDescribe(MessageConst.UPDATE_INFO_SUCCESS);

        } catch (Exception e) {
            log.error("", e);
            respModel.setRespCode(-1);
            respModel.setRespDescribe(MessageConst.NETWORK_ANOMARY);
        }
        log.info("detection of the [Staff Update Info] action, tel: {}, status: {}", entity.getTelephone(),
                respModel.getRespCode().equals(0) ? "success" : "failed");
        return respModel;
    }

    @Override
    public ResponseEntity editStaffPassword(String password, int id) {
        ResponseEntity respModel = new ResponseEntity();
        try {
            staffMapper.editStaffPassword(password, id);
            respModel.setRespCode(0);
            respModel.setRespDescribe(MessageConst.UPDATE_PASSWD_SUCCESS);

        } catch (Exception e) {
            log.error("", e);
            respModel.setRespCode(-1);
            respModel.setRespDescribe(MessageConst.NETWORK_ANOMARY);
        }
        log.info("detection of the [Staff Edit Password] action, id: {}, status: {}", id,
                respModel.getRespCode().equals(0) ? "success" : "failed");
        return respModel;
    }
}
