package cn.iamtudou.entity;

import java.io.Serializable;
import java.util.Date;

public class TeacherEntity implements Serializable {

    private Integer id;
    private String realName;
    private Integer gender;
    private Date bornDate;
    private String telephone;
    private String loginPassword;
    private String nativeAddress;
    private String livingAddress;
    private String QQNumber;
    private String WechatNumber;
    private String imgPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getNativeAddress() {
        return nativeAddress;
    }

    public void setNativeAddress(String nativeAddress) {
        this.nativeAddress = nativeAddress;
    }

    public String getLivingAddress() {
        return livingAddress;
    }

    public void setLivingAddress(String livingAddress) {
        this.livingAddress = livingAddress;
    }

    public String getQQNumber() {
        return QQNumber;
    }

    public void setQQNumber(String QQNumber) {
        this.QQNumber = QQNumber;
    }

    public String getWechatNumber() {
        return WechatNumber;
    }

    public void setWechatNumber(String wechatNumber) {
        WechatNumber = wechatNumber;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public TeacherEntity(String realName, Integer gender, Date bornDate, String telephone, String loginPassword,
                         String nativeAddress, String livingAddress, String QQNumber, String wechatNumber,
                         String imgPath) {

        this.realName = realName;
        this.gender = gender;
        this.bornDate = bornDate;
        this.telephone = telephone;
        this.loginPassword = loginPassword;
        this.nativeAddress = nativeAddress;
        this.livingAddress = livingAddress;
        this.QQNumber = QQNumber;
        WechatNumber = wechatNumber;
        this.imgPath = imgPath;
    }
}
