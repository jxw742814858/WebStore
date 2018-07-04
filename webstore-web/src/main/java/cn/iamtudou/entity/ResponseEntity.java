package cn.iamtudou.entity;

/**
 * 返回操作状态实体
 */
public class ResponseEntity {

    private Integer respCode; //返回状态码，0.正常 -1.异常
    private String respDescribe; //返回描述

    public Integer getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public String getRespDescribe() {
        return respDescribe;
    }

    public void setRespDescribe(String respDescribe) {
        this.respDescribe = respDescribe;
    }

    public ResponseEntity() {
    }

    public ResponseEntity(Integer respCode, String respDescribe) {
        this.respCode = respCode;
        this.respDescribe = respDescribe;
    }
}
