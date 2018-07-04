package cn.iamtudou.entity;

import java.io.Serializable;

/**
 * 登陆/注册 简单信息实体
 */
public class SimpleEntity implements Serializable {

    private String id;
    private String password;
    private Integer accountType; // 1.stu 2.teac 3.staff

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }
}
