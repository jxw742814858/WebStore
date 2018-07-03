package cn.iamtudou.entity;

import java.io.Serializable;

/**
 * 登陆简单信息实体
 */
public class SimpleEntity implements Serializable {

    private String id;
    private String password;
    private Integer account_type; // 1.stu 2.teac 3.staff

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

    public Integer getAccount_type() {
        return account_type;
    }

    public void setAccount_type(Integer account_type) {
        this.account_type = account_type;
    }
}
